package cn.bysj.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.bysj.utils.RetrurnModel;

public interface FilesService {

	/**
	 * 上传文件
	 * **/
	public RetrurnModel upload(MultipartFile file,String token);

	/**
	 * 文件上传后回调接口，回写文件信息
	 * **/
	public RetrurnModel fileInfo(String params,String token);

	/**
	 * 获取文件列表
	 * **/
	public RetrurnModel findFiles(String params);

	/**
	 * word转html字符串返回实现预览
	 * **/
	public RetrurnModel previewFile(String systemid,String token);

	/****
	 * 下载文件返回文件名称和路径
	 */
	public RetrurnModel download(String systemid);

	/****
	 * 编辑文件
	 */
	public RetrurnModel edit(String systemid,String token);

	/****
	 * 保存文件
	 */
	public RetrurnModel save(String param,String token);

	/****
	 * 获取个人文件列表
	 * @param token
	 * @return
	 */
	public RetrurnModel getMyFile(String token);

	/****
	 * 修改文件公开状态
	 * @param token
	 * @return
	 */
	public RetrurnModel updateOpen(String token,String id);

	public RetrurnModel deleteFile(String token, String id);

	public RetrurnModel findFile(String input);
}