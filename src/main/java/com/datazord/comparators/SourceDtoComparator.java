package com.datazord.comparators;

import java.util.Comparator;

import com.datazord.dto.SourceDto;

public class SourceDtoComparator implements Comparator<SourceDto> {

	private final String spliter = "\\.";

	@Override
	public int compare(SourceDto sourceDto1, SourceDto sourceDto2) {
		String sourceDto1Name = sourceDto1.getName();
		String sourceDto2Name = sourceDto2.getName();

		String[] sourceDto1Array = sourceDto1Name.split(spliter);
		String[] sourceDto2Array = sourceDto2Name.split(spliter);

		int sourceDto1NameLength = sourceDto1Array.length;
		int sourceDto2NameLength = sourceDto2Array.length;

		int i = 0;
		int maxIterator = sourceDto1NameLength < sourceDto2NameLength ? sourceDto1NameLength : sourceDto2NameLength;

		for(; i < maxIterator; i++) {
			if(sourceDto1Array[i].equals(sourceDto2Array[i]))
				continue;

			break;
		}

		if(i == maxIterator)
			return sourceDto1NameLength - sourceDto2NameLength;

		return sourceDto1Array[i].compareTo(sourceDto2Array[i]);
	}
}