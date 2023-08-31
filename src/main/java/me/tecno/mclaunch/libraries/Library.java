package me.tecno.mclaunch.libraries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.tecno.mclaunch.indexing.rules.ElementRule;
import me.tecno.mclaunch.structure.PathFileDataset;

import java.net.URI;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Library {
	private final String name;
	private final LibraryDownloads downloads;

	private URI repository;

	public record LibraryDownloads(PathFileDataset artifact,
								   Map<String, PathFileDataset> classifiers) {}
}
