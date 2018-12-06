package app.dao;

import org.springframework.data.repository.CrudRepository;

import app.model.Couple;

public interface CoupleDAO extends CrudRepository<Couple, Integer> {
	
}
