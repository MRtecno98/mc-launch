package me.tecno.mclaunch.arguments;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import me.tecno.mclaunch.indexing.StringsIndex;
import me.tecno.mclaunch.launch.LaunchEnvironment;

@Getter
public class ArgumentIndex {
	private @SerializedName("game") StringsIndex gameArguments;
	private @SerializedName("jvm")  StringsIndex jvmArguments;

	protected String evaluate(StringsIndex index, LaunchEnvironment env) {
		return index.stream()
				.map(e -> e.getElement(env))
				.filter(Objects::nonNull)
				.map((el) -> String.join(" ", el))
				.reduce("", (a, b) -> a + " " + b);
	}
	
	public String evaluateGameArguments(LaunchEnvironment env) {
		return evaluate(getGameArguments(), env);
	}
	
	public String evaluateJVMArguments(LaunchEnvironment env) {
		return evaluate(getJvmArguments(), env);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(jvmArguments=" + getJvmArguments().size() + ", gameArguments=" + getGameArguments().size() + ")";
	}
}
