package me.tecno.mclaunch.indexing.rules;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import me.tecno.mclaunch.launch.LaunchEnvironment;

@Getter
@AllArgsConstructor
public class ElementRule<T> implements IElementRule<T> {
	@NonNull
	private RuleAction action;
	
	@NonNull
	private Collection<RuleCondition<T>> conditions;
	
	@SafeVarargs
	public ElementRule(RuleAction action, RuleCondition<T>... conditions) {
		this(action, Arrays.asList(conditions));
	}
	
	@Override
	public boolean isPassing(T value, LaunchEnvironment env) {
		return getAction().process(
					getConditions().stream()
						.collect(Collector.of(() -> new boolean[] {true}, 
								(a, r) -> a[0] &= r.isApplied(value, env), 
								(a1, a2) -> new boolean[] {a1[0] && a2[1]}, 
								(a) -> a[0]))
				);
	}
	
}
