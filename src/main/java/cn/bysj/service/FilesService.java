package cn.bysj.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.bysj.utils.RetrurnModel;

public interface FilesService {

	/**
	 * �ϴ��ļ�
	 * **/
	public RetrurnModel upload(MultipartFile file,String token);

	/**
	 * �ļ��ϴ���ص��ӿڣ���д�ļ���Ϣ
	 * **/
	public RetrurnModel fileInfo(String params,String token);

	/**
	 * ��ȡ�ļ��б�
	 * **/
	public RetrurnModel findFiles(String params);

	/**
	 * wordתhtml�ַ�������ʵ��Ԥ��
	 * **/
	public RetrurnModel previewFile(String systemid,String token);

	/****
	 * �����ļ������ļ����ƺ�·��
	 */
	public RetrurnModel download(String systemid);

	/****
	 * �༭�ļ�
	 */
	public RetrurnModel edit(String systemid,String token);

	/****
	 * �����ļ�
	 */
	public RetrurnModel save(String param,String token);

	/****
	 * ��ȡ�����ļ��б�
	 * @param token
	 * @return
	 */
	public RetrurnModel getMyFile(String token);

	/****
	 * �޸��ļ�����״̬
	 * @param token
	 * @return
	 */
	public RetrurnModel updateOpen(String token,String id);

	public RetrurnModel deleteFile(String token, String id);

	public RetrurnModel findFile(String input);
}