package app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.CoupleDAO;
import app.model.Couple;
import app.service.CoupleService;
@Service("coupleService")
public class CoupleServiceImpl implements CoupleService{
	@Autowired
	private CoupleDAO coupleDAO;
	@Override
	public Couple getCoupleById(int id) {
		// TODO Auto-generated method stub
		return coupleDAO.findOne(id);
	}

	@Override
	public void saveCouple(Couple couple) {
		 coupleDAO.save(couple);
	}

	@Override
	public void deleteCouple(int id) {
		coupleDAO.delete(id);
	}
	
	
}
