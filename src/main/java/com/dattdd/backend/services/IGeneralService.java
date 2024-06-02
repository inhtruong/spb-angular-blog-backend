package com.dattdd.backend.services;

import java.util.List;

public interface IGeneralService<T, U> {
	List<T> findAll();

	T findById(final U id);
	
	U create(final T t);

	void update(final T t);

	void delete(final U id);
}
