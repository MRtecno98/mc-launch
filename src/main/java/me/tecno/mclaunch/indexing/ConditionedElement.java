package me.tecno.mclaunch.indexing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tecno.mclaunch.launch.LaunchEnvironment;

@AllArgsConstructor
public abstract class ConditionedElement<T> implements IndexElement<T> {
	private @Getter(AccessLevel.PROTECTED) T value;
	
	@Override
	public T getElement(LaunchEnvironment env) {
		return compliant(env) ? value : null;
	}
	
	public abstract boolean compliant(LaunchEnvironment env);
}
