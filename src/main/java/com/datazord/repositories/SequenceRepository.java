package com.datazord.repositories;

import com.datazord.exceptions.SequenceException;

public interface SequenceRepository {
	public long getNextSequenceId(String key) throws SequenceException;
}
