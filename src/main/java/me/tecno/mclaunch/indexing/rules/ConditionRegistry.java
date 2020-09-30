package me.tecno.mclaunch.indexing.rules;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class ConditionRegistry {
	@SuppressWarnings("rawtypes")
	private @Getter Map<String, Class<? extends RuleCondition>> conditions = new HashMap<>();
	
	static {
		getInstance().getConditions().put("features", FeaturesCondition.class);
	}
	
	private static ConditionRegistry instance;
	public static ConditionRegistry getInstance() {
		if(instance == null) instance = new ConditionRegistry();
		return instance;
	}
}
