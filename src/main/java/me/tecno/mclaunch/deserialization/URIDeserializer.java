package me.tecno.mclaunch.deserialization;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class URIDeserializer implements JsonDeserializer<URI> {

	@Override
	public URI deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		try {
			return new URI(json.getAsString());
		} catch (URISyntaxException e) {
			throw new JsonParseException(e);
		}
	}

}
