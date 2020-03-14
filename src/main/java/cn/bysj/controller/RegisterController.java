package cn.bysj.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.bysj.dao.UsersDao;
import cn.bysj.entity.Users;
import cn.bysj.service.UsersService;
import cn.bysj.utils.RetrurnModel;
import cn.bysj.utils.Word2Html;

@RestController
@RequestMapping("app/register")
//@CrossOrigin(value = "http://localhost:8081")
public class RegisterController {

	@Autowired
	private UsersService userService;



	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public RetrurnModel register(@RequestBody String params) {

		return userService.addUser(params);

	}


}