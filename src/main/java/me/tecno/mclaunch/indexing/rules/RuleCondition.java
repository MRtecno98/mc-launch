package me.tecno.mclaunch.indexing.rules;

import me.tecno.mclaunch.launch.LaunchEnvironment;

public interface RuleCondition<T> {
	public boolean isApplied(T value, LaunchEnvironment env);
}
