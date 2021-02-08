package me.tecno.mclaunch.indexing.rules;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class ElementRuleDeserializer implements JsonDeserializer<IElementRule<Collection<String>>> {

	@Override
	public IElementRule<Collection<String>> deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject obj = json.getAsJsonObject();
		RuleAction act = context.deserialize(obj.get("action"), 
				new TypeToken<RuleAction>() {}.getType());
		
		Collection<RuleCondition<Collection<String>>> conds;
		conds = obj.keySet().stream()
			.filter((k) -> !k.equals("action"))
			.map((k) -> {
				Class<?> conditionClass = ConditionsRegistry.getInstance()
						.getConditions().get(k);
				if(conditionClass == null) return null;
				
				return context.<RuleCondition<Collection<String>>>deserialize(
						obj.get(k), conditionClass);
				})
			.filter(r -> r != null)
			.collect(Collectors.toList());
		
		return new ElementRule<Collection<String>>(act, conds);
	}

}
