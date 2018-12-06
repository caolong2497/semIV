package app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.UserDAO;
import app.model.UserInfor;
import app.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDao;
	@Override
	public Iterable<UserInfor> getAll() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	@Override
	public UserInfor getUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.findOne(id);
	}

	@Override
	public void saveUser(UserInfor user) {
		userDao.save(user);
		
	}

	@Override
	public void deleteUser(int id) {
		userDao.delete(id);
		
	}



}
