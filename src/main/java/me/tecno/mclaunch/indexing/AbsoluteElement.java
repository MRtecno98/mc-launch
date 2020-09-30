package me.tecno.mclaunch.indexing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tecno.mclaunch.launch.LaunchEnvironment;

@AllArgsConstructor
public class AbsoluteElement<T> implements IndexElement<T> {
	@Getter private T element;

	@Override
	public T getElement(LaunchEnvironment env) {
		return getElement();
	}
}
