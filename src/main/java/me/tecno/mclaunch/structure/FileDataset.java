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
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FileDataset {
	private int size;
	
	@JsonAdapter(URIDeserializer.class)
	private URI url;
	
	private String sha1;
}
