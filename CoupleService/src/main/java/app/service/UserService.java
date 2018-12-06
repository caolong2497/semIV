package app.service;

import app.model.UserInfor;


public interface UserService {

	public Iterable<UserInfor> getAll();
	public UserInfor getUserById(int id);
	public void saveUser(UserInfor user);
	public void deleteUser(int id);
}
