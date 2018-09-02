package com.datazord.comparators;

import java.util.Comparator;

import com.datazord.dto.DestinationDto;

public class DestinationDtoComparator implements Comparator<DestinationDto> {

	private final String spliter = "\\.";

	@Override
	public int compare(DestinationDto destinationDto1, DestinationDto destinationDto2) {
		String destinationDto1Name = destinationDto1.getName();
		String destinationDto2Name = destinationDto2.getName();

		String[] destinationDto1Array = destinationDto1Name.split(spliter);
		String[] destinationDto2Array = destinationDto2Name.split(spliter);

		int destinationDto1NameLength = destinationDto1Array.length;
		int destinationDto2NameLength = destinationDto2Array.length;

		int i = 0;
		int maxIterator = destinationDto1NameLength < destinationDto2NameLength ? destinationDto1NameLength : destinationDto2NameLength;

		for(; i < maxIterator; i++) {
			if(destinationDto1Array[i].equals(destinationDto2Array[i]))
				continue;

			break;
		}

		if(i == maxIterator)
			return destinationDto1NameLength - destinationDto2NameLength;

		return destinationDto1Array[i].compareTo(destinationDto2Array[i]);
	}
}