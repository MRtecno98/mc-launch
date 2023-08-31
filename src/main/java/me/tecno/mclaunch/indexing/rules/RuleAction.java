package me.tecno.mclaunch.indexing.rules;

import java.util.function.Function;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RuleAction {
	@SerializedName("allow") ALLOW(Function.identity()),
	@SerializedName("disallow")  DENY((b) -> !b);
	
	private final Function<Boolean, Boolean> evaluator;
	
	public boolean process(boolean b) {
		return evaluator.apply(b);
	}
}
