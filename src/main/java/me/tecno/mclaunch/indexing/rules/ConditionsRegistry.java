package me.tecno.mclaunch.indexing.rules;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class ConditionsRegistry {
	@SuppressWarnings("rawtypes")
	private final Map<String, Class<? extends RuleCondition>> conditions = new HashMap<>();
	
	static {
		getInstance().getConditions().put("features", FeaturesCondition.class);
		getInstance().getConditions().put("os", SystemCondition.class);
	}
	
	private static ConditionsRegistry instance;
	public static ConditionsRegistry getInstance() {
		if(instance == null) instance = new ConditionsRegistry();
		return instance;
	}
}
