package app.service;

import app.model.Couple;


public interface CoupleService {
	public Couple getCoupleById(int id);
	public void saveCouple(Couple couple);
	public void deleteCouple(int id);
}
