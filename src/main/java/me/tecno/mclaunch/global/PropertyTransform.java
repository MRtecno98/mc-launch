package me.tecno.mclaunch.global;

public interface PropertyTransform {
	public String transform(String key, String value);
	
	public default void addToGlobalRegister() {
		TransformRegister.getGlobalRegister().registerTransform(this);
	}
}
