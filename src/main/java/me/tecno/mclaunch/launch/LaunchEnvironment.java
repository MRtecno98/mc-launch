package me.tecno.mclaunch.launch;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class LaunchEnvironment {
	public Map<String, String> properties = new HashMap<>();
}
