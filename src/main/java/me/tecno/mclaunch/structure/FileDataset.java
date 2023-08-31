package me.tecno.mclaunch.structure;

import java.net.URI;

import com.google.gson.annotations.JsonAdapter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.tecno.mclaunch.deserialization.URIDeserializer;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PROTECTED)
public class FileDataset {
	private int size;
	
	@JsonAdapter(URIDeserializer.class)
	private URI url;
	
	private String sha1;

	@Override
	public String toString() {
		String[] url = getUrl().toString().split("/");
		return getClass().getSimpleName() + "(size=" + size + ", url=" + url[url.length - 1]
				+ ", sha1=" + sha1.substring(0, 8) + ")";
	}
}
