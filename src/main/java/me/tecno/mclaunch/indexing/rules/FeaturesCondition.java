package me.tecno.mclaunch.indexing.rules;

import java.util.HashMap;

import me.tecno.mclaunch.launch.LaunchEnvironment;

public class FeaturesCondition<T> extends HashMap<String, String> implements RuleCondition<T> {
	private static final long serialVersionUID = -739440196688132247L;

	@Override
	public boolean isApplied(T value, LaunchEnvironment env) {
		return keySet().stream()
				.allMatch((k) -> get(k).equals(env.getProperties().get(k)));
	}

	@Override
	public String toString() {
		return "Features" + super.toString();
	}
}
