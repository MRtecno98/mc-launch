package me.tecno.mclaunch.indexing.rules;

import me.tecno.mclaunch.launch.LaunchEnvironment;

public interface RuleCondition<T> {
	boolean isApplied(T value, LaunchEnvironment env);
}
