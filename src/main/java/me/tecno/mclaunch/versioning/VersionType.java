package me.tecno.mclaunch.versioning;

import com.google.gson.annotations.SerializedName;

public enum VersionType {
	@SerializedName("release")  RELEASE, 
	@SerializedName("snapshot") SNAPSHOT;
}
