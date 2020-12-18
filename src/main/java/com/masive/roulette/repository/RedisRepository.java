package com.masive.roulette.repository;

import java.util.Map;

import com.masive.roulette.model.Roulette;

public interface RedisRepository {

	Map<String, Roulette> findAll();
	Roulette findById(int id);
	void save(Roulette roulette);
	void delete(int id);
}
