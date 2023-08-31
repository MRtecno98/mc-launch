package me.tecno.mclaunch.indexing;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import me.tecno.mclaunch.indexing.rules.ElementRule;
import me.tecno.mclaunch.indexing.rules.IElementRule;
import me.tecno.mclaunch.indexing.rules.RuledElement;

public class IndexElementDeserializer implements JsonDeserializer<IndexElement<Collection<String>>> {
	@Override
	public IndexElement<Collection<String>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		if(json.isJsonPrimitive()) return new AbsoluteElement<>(
				Collections.singletonList(json.getAsString()));
		else if(json.isJsonObject()) {
			JsonObject obj = json.getAsJsonObject();
			
			Collection<String> value;
			JsonElement jval = obj.get("value");
			if(jval.isJsonPrimitive() && jval.getAsJsonPrimitive().isString())
				value = Collections.singletonList(jval.getAsString());
			else if(jval.isJsonArray()) value = StreamSupport.stream(
					jval.getAsJsonArray().spliterator(), false)
						.map(JsonElement::getAsString)
						.collect(Collectors.toList());
			else throw new JsonParseException("Invalid index element value");
			
			return new RuledElement<>(value,
					context.<Collection<IElementRule<Collection<String>>>>deserialize(obj.get("rules"),
							new TypeToken<Collection<ElementRule<String>>>() {
							}.getType()));
		} else throw new JsonParseException("Invalid index element descriptor");
	}

}
