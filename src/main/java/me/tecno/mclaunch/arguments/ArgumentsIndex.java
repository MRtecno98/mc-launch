package me.tecno.mclaunch.arguments;

import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import me.tecno.mclaunch.indexing.StringsIndex;
import me.tecno.mclaunch.launch.LaunchEnvironment;

public class ArgumentsIndex {
	private @Getter @SerializedName("game") StringsIndex gameArguments;
	private @Getter @SerializedName("jvm")  StringsIndex jvmArguments;

	protected String evaluate(StringsIndex index, LaunchEnvironment env) {
		return index.stream()
				.map(e -> e.getElement(env))
				.filter(e -> e != null)
				.collect(
					Collectors.reducing("", (el) -> String.join(" ", el), 
									   (a, b) -> a + " " + b));
	}
	
	public String evaluateGameArguments(LaunchEnvironment env) {
		return evaluate(getGameArguments(), env);
	}
	
	public String evaluateJVMArguments(LaunchEnvironment env) {
		return evaluate(getJvmArguments(), env);
	}

	@Override
	public String toString() {
		return getClass().getName() + "(jvmArguments=" + getJvmArguments().size() + ", gameArguments=" + getGameArguments().size() + ")";
	}
}
