package me.tecno.mclaunch.indexing.rules;

import me.tecno.mclaunch.launch.LaunchEnvironment;

public interface IElementRule<T> {
	boolean isPassing(T value, LaunchEnvironment env);
}
