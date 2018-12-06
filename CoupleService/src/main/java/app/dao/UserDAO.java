package app.dao;

import org.springframework.data.repository.CrudRepository;

import app.model.UserInfor;

public interface UserDAO extends CrudRepository<UserInfor, Integer> {

}
