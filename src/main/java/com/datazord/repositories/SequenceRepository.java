package com.datazord.repositories;

import com.datazord.exceptions.SequenceException;

public interface SequenceRepository {
	public String getNextSequenceId(String key) throws SequenceException;
}
