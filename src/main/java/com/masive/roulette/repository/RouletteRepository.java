package com.masive.roulette.repository;

import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.masive.roulette.model.Roulette;

@Repository
public class RouletteRepository implements RedisRepository{

	private static final String KEY = "Roulette";
	
	private RedisTemplate<Integer, Roulette> redisTemplate;
	private HashOperations hashOperations;
	
	public RouletteRepository(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.hashOperations = redisTemplate.opsForHash();
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public Map<String, Roulette> findAll() {
		return hashOperations.entries(KEY);
	}

	@Override
	public Roulette findById(int id) {
		return (Roulette)hashOperations.get(KEY, id);
	}

	@Override
	public void save(Roulette roulette) {
		hashOperations.put(KEY, roulette.getId(), roulette);
	}

	@Override
	public void delete(int id) {
		hashOperations.delete(KEY, id);
	}
	
	

}
