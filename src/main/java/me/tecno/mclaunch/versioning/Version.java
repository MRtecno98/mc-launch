package me.tecno.mclaunch.versioning;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;

import me.tecno.mclaunch.launch.LoggingTarget;
import org.apache.http.client.fluent.Request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Delegate;
import me.tecno.mclaunch.arguments.ArgumentIndex;
import me.tecno.mclaunch.assets.AssetIndex;
import me.tecno.mclaunch.executables.ExecutablesIndex;
import me.tecno.mclaunch.indexing.IndexElement;
import me.tecno.mclaunch.indexing.IndexElementDeserializer;
import me.tecno.mclaunch.indexing.rules.ElementRule;
import me.tecno.mclaunch.indexing.rules.ElementRuleDeserializer;
import me.tecno.mclaunch.launch.LaunchOptions;
import me.tecno.mclaunch.launch.LaunchOptions.JavaOptions;
import me.tecno.mclaunch.launch.LoggingOptions;
import me.tecno.mclaunch.libraries.Library;

@Getter
@ToString
@EqualsAndHashCode
@Setter(AccessLevel.PRIVATE)
public class Version {
	@Delegate
	private VersionDataset metadata;
	
	private @SerializedName("assetIndex") AssetIndex assetIndex;
	private @SerializedName("libraries") Collection<Library> libraryIndex;
	private @SerializedName("downloads") ExecutablesIndex executablesIndex;
	private @SerializedName("arguments") ArgumentIndex argumentIndex;
	
	private LaunchOptions launchOptions;
	
	private @SerializedName("logging") Map<LoggingTarget, LoggingOptions> loggingOptions;

	private int complianceLevel;
	
	private Version() {}
	
	public static Version fromVersionDataset(VersionDataset dataset) throws IOException {
		Version v =  fromDescriptor(Request.Get(
							dataset.getManifestURI())
						.execute()
						.returnContent()
						.asStream());
		v.getMetadata().setManifestURI(dataset.getManifestURI());
		
		return v;
	}
	
	public static Version fromDescriptor(InputStream versionDescriptor) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Version.class, (JsonDeserializer<Version>) (json, typeOfT, context) -> {
					Gson gson1 = new GsonBuilder()
							.registerTypeAdapter(IndexElement.class, new IndexElementDeserializer())
							.registerTypeAdapter(ElementRule.class, new ElementRuleDeserializer())
							.create();

					Version base = gson1.fromJson(json, Version.class);
					VersionDataset metadata = context.deserialize(json, VersionDataset.class);

					LaunchOptions launchOptions = new LaunchOptions(
							json.getAsJsonObject()
								.get("mainClass")
								.getAsString(),
							json.getAsJsonObject()
								.get("minimumLauncherVersion")
								.getAsInt(),
							gson1.fromJson(json.getAsJsonObject().get("javaVersion"),
									JavaOptions.class));

					base.setMetadata(metadata);
					base.setLaunchOptions(launchOptions);

					return base;
				}).create();
		
		return gson.fromJson(new InputStreamReader(versionDescriptor), Version.class);
	}
}
