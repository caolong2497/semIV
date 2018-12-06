package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.model.UserInfor;
import app.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;

	// lay tat ca
	@RequestMapping("/getall")
	@ResponseBody
	public List<UserInfor> getAll() {
		return (List<UserInfor>) userService.getAll();
	}

	// lay theo id
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public UserInfor getByID(@PathVariable("id") int id) {
		System.out.println("Fetching id: " + id);
		UserInfor user = userService.getUserById(id);
		if (user == null) {
			System.out.println("Id:" + id + " not found!");
		}
		return user;
	}
	
	// delete
	@DeleteMapping("/delete/{id}")
	public boolean deleteByID(@PathVariable("id") int id) {	
		UserInfor user = userService.getUserById(id);
		if (user == null) {
			System.out.println("Id:" + id + " not found!");
			return false;
		}
		userService.deleteUser(id);
		return true;
	}
	
	// them mới
	@PostMapping("/add")
	public void addUser(@RequestBody UserInfor u) {
		userService.saveUser(u);
		return ;
	}
	
	// cập nhật
	@PutMapping("/update")
	public UserInfor updateUser(@RequestBody UserInfor u) {
		UserInfor user=userService.getUserById(u.getUserid());
		if(user == null){
			System.out.println("User không tồn tại");
			return null;
		}
		userService.saveUser(u);
		return u;
	}
}
