package me.tecno.mclaunch.indexing.rules;

import java.util.HashMap;

import me.tecno.mclaunch.global.SystemInfo;
import me.tecno.mclaunch.launch.LaunchEnvironment;

public class SystemCondition<T> extends HashMap<String, String> implements RuleCondition<T> {
	private static final long serialVersionUID = 107316663197544599L;

	@Override
	public boolean isApplied(T value, LaunchEnvironment env) {
		return keySet().stream()
			.allMatch((k) -> SystemInfo.getProperties().getProperty("os." + k).matches(get(k)));
	}

	@Override
	public String toString() {
		return "System" + super.toString();
	}
}
