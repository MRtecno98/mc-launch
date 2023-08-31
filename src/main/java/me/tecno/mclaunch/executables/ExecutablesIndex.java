package me.tecno.mclaunch.executables;

import com.google.gson.annotations.SerializedName;
import me.tecno.mclaunch.structure.FileDataset;

public record ExecutablesIndex(
		FileDataset client,
		@SerializedName("client_mappings")
		FileDataset clientMappings,
		FileDataset server,
		@SerializedName("server_mappings")
		FileDataset serverMappings) {
}
