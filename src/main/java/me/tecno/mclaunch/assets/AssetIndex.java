package me.tecno.mclaunch.assets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class AssetIndex {
	private final String id;
	private final URI url;
	private final String sha1;
	private final long size, totalSize;

	private Map<String, AssetRecord> objects = Collections.emptyMap();

	public Map<String, AssetRecord> getObjects() throws IOException {
		if(objects == null) {
			objects = downloadObjectRegistry();
		}

		return objects;
	}

	public Map<String, AssetRecord> downloadObjectRegistry() throws IOException {
		Gson gson = new Gson();

		try(InputStreamReader reader = new InputStreamReader(
				Request.Get(getUrl())
					.execute()
					.returnContent()
					.asStream())) {
			JsonReader jread = gson.newJsonReader(reader);
			jread.beginObject();
			jread.nextName();
			return gson.fromJson(jread, new TypeToken<Map<String, AssetRecord>>(){}.getType());
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(id=" + id + ", sha1=" + sha1.substring(0, 8) + ", size=" + size + ", totalSize="
				+ totalSize + ", downloaded=" + (objects != null ? String.valueOf(objects.size()) : "none") + ")";
	}

	public record AssetRecord(String hash, long size) {
		@Override
		public String toString() {
			return getClass().getSimpleName() + "(hash=" + hash + ", size=" + size + ")";
		}
	}
}
