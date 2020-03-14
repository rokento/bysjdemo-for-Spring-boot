package cn.bysj.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import cn.bysj.service.FilesService;
import cn.bysj.service.UsersService;
import cn.bysj.utils.RetrurnModel;

@RequestMapping("app/user")
@RestController
//@CrossOrigin(value = "http://localhost:8081")
public class UserController {

	@Autowired
	private UsersService userService;

	@Autowired 
	private FilesService fileService;

	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public RetrurnModel login(@RequestBody String param) {

		return userService.login(param);
	}

	@RequestMapping(value="/uploadtouxiang",method=RequestMethod.POST)
	@ResponseBody
	public RetrurnModel uploadTouxiang(@RequestBody String param) {
		return userService.uploadTouxiang(param);
	}

	@RequestMapping(value="/myfile",method=RequestMethod.GET)
	@ResponseBody 
	public RetrurnModel myFiles(@RequestHeader("token")String token) {
		return fileService.getMyFile(token);
	}

	@RequestMapping(value="/history",method=RequestMethod.GET)
	@ResponseBody 
	public RetrurnModel history(@RequestHeader("token")String token) {
		return userService.getHistory(token);
	}
}