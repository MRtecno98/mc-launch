package me.tecno.mclaunch.indexing;

import me.tecno.mclaunch.launch.LaunchEnvironment;

public interface IndexElement<T> {
	T getElement(LaunchEnvironment env);
}
