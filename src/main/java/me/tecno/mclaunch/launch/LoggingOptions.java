package me.tecno.mclaunch.launch;

import me.tecno.mclaunch.structure.IdentifiedFileDataset;

public record LoggingOptions(String argument, IdentifiedFileDataset file, LoggingType type) {
}
