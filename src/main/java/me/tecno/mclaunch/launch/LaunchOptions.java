package me.tecno.mclaunch.launch;

import lombok.Value;

@Value
public class LaunchOptions {
	private String mainClass;
	private int minimumLauncherVersion;
}
