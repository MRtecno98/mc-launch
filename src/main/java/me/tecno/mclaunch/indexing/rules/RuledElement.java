package me.tecno.mclaunch.indexing.rules;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collector;

import me.tecno.mclaunch.indexing.ConditionedElement;
import me.tecno.mclaunch.indexing.IndexElement;
import me.tecno.mclaunch.launch.LaunchEnvironment;

public class RuledElement<T> extends ConditionedElement<T> implements IndexElement<T> {
	private final Collection<IElementRule<T>> rules;
	
	@SafeVarargs
	public RuledElement(T value, IElementRule<T>... rules) {
		this(value, Arrays.asList(rules));
	}
	
	public RuledElement(T value, Collection<IElementRule<T>> rules) {
		super(value);
		this.rules = rules;
	}
	
	@Override
	public boolean compliant(LaunchEnvironment env) {
		return rules.stream()
				.collect(Collector.of(() -> new boolean[] {true}, 
						(a, r) -> a[0] &= r.isPassing(getValue(), env), 
						(a1, a2) -> new boolean[] {a1[0] && a2[0]}, 
						(a) -> a[0]));
	}

}
