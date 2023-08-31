package me.tecno.mclaunch.launch;

public record LaunchOptions(String mainClass, int minimumLauncherVersion, JavaOptions javaVersion) {
	public record JavaOptions(String component, int majorVersion) {}
}
