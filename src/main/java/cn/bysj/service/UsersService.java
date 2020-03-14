package cn.bysj.service;

import org.springframework.web.multipart.MultipartFile;

import cn.bysj.utils.RetrurnModel;

public interface UsersService {

	public RetrurnModel addUser(String params);

	public RetrurnModel login(String param);

	public RetrurnModel uploadTouxiang (String param);

	public RetrurnModel getHistory(String token);
}
