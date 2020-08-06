package me.tecno.mclaunch.versioning;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.fluent.Request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionManifest {
	public static final String VERSION_MANIFEST = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
	
	private VersionDataset latestRelease;
	private VersionDataset latestSnapshot;
	private List<VersionDataset> versions;
	
	public Collection<Version> getAllVersions() {
		return getVersions().stream()
				.map(t -> {
					try {
						return t.getVersion();
					} catch (IOException e) { throw new RuntimeException(e); }
				}).collect(Collectors.toList());
	}
	
	public static JsonDeserializer<VersionManifest> getDeserializer() {
		return  new JsonDeserializer<VersionManifest>() {
			@Override
			public VersionManifest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				
				JsonObject j = json.getAsJsonObject();
				
				List<VersionDataset> datasets = context.deserialize(j.getAsJsonArray("versions"), 
						new TypeToken<List<VersionDataset>>(){}.getType());
				
				String latestRelease = j.getAsJsonObject("latest").get("release").getAsString();
				String latestSnapshot = j.getAsJsonObject("latest").get("snapshot").getAsString();
				
				VersionDataset vLatestRelease = datasets.stream()
						.filter((d) -> d.getId().equals(latestRelease))
						.findFirst()
						.orElseThrow(() -> new IllegalStateException("Latest release not found"));
				
				VersionDataset vLatestSnapshot = datasets.stream()
						.filter((d) -> d.getId().equals(latestSnapshot))
						.findFirst()
						.orElseThrow(() -> new IllegalStateException("Latest snapshot not found"));
				
				return new VersionManifest(vLatestRelease, vLatestSnapshot, datasets);
			}
		};
	}
	
	public static VersionManifest getVersionManifest() throws IOException {
		String json = Request.Get(VERSION_MANIFEST)
			.execute()
			.returnContent()
			.asString();
		
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(VersionManifest.class, getDeserializer())
				.create();
		return gson.fromJson(json, VersionManifest.class);
	}
}
