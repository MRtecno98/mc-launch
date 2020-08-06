package me.tecno.mclaunch.deserialization;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateDeserializer implements JsonDeserializer<Date> {
	private static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssXXX";

	@Override
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		try {
			return DateUtils.parseDate(json.getAsString(), ISO8601);
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
	}
}
