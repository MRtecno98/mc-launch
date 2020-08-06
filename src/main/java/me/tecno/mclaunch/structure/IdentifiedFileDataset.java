package me.tecno.mclaunch.structure;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class IdentifiedFileDataset extends FileDataset {
	private String id;
	
	public IdentifiedFileDataset(FileDataset base, String id) {
		this.id = id;
		
		setSize(base.getSize());
		setSha1(base.getSha1());
		setUrl(base.getUrl());
	}
}
