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

import app.model.Couple;
import app.service.CoupleService;

@RestController
@RequestMapping("couple")
public class CoupleController {
	@Autowired
	private CoupleService coupleService;



	// lay theo id
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Couple getByID(@PathVariable("id") int id) {
		System.out.println("Fetching id: " + id);
		Couple couple = coupleService.getCoupleById(id);
		if (couple == null) {
			System.out.println("Id:" + id + " not found!");
		}
		return couple;
	}
	
	// delete
	@DeleteMapping("/delete/{id}")
	public boolean deleteByID(@PathVariable("id") int id) {	
		Couple couple = coupleService.getCoupleById(id);
		if (couple == null) {
			System.out.println("Id:" + id + " not found!");
			return false;
		}
		coupleService.deleteCouple(id);
		return true;
	}
	
	// them mới
	@PostMapping("/add")
	public void createCouple(@RequestBody Couple c) {
		coupleService.saveCouple(c);
		return ;
	}
	
	// cập nhật
	@PutMapping("/update")
	public Couple updateUser(@RequestBody Couple couple) {
		Couple couple2=coupleService.getCoupleById(couple.getCoupleId());
		if(couple2 == null){
			System.out.println("Couple không tồn tại");
			return null;
		}
		coupleService.saveCouple(couple);
		return couple2;
	}
}
