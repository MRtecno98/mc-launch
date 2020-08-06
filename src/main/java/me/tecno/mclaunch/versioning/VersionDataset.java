package me.tecno.mclaunch.versioning;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.tecno.mclaunch.deserialization.DateDeserializer;
import me.tecno.mclaunch.deserialization.URIDeserializer;

@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionDataset {
	private String id;
	private VersionType type;
	
	@SerializedName("url")
	@JsonAdapter(URIDeserializer.class)
	private URI manifestURI;
	
	@SerializedName("time")
	@JsonAdapter(DateDeserializer.class)
	private Date lastUpdate;
	
	@SerializedName("releaseTime") 
	@JsonAdapter(DateDeserializer.class)
	private Date releaseDate;
	
	public Version getVersion() throws IOException {
		return Version.fromVersionDataset(this);
	}
}
