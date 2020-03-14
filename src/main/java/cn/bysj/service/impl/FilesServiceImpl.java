package cn.bysj.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import cn.bysj.dao.FileHisDao;
import cn.bysj.dao.FileHtmlDao;
import cn.bysj.dao.FilesDao;
import cn.bysj.dao.HistoryDao;
import cn.bysj.dao.QxManagerDao;
import cn.bysj.dao.UsersDao;
import cn.bysj.entity.FileHis;
import cn.bysj.entity.FileHtml;
import cn.bysj.entity.Files;
import cn.bysj.entity.History;
import cn.bysj.service.FilesService;
import cn.bysj.utils.RetrurnModel;
import cn.bysj.utils.Word2Html;

@Service
public class FilesServiceImpl implements FilesService {

	@Autowired
	private FilesDao fileDao;

	@Autowired
	private UsersDao userDao;

	@Autowired
	private FileHisDao hisDao;

	@Autowired
	private FileHtmlDao htmlDao;

	@Autowired
	private HistoryDao historyDao;

	@Autowired
	private QxManagerDao qxDao;

	@Override
	public RetrurnModel upload(MultipartFile file,String token) {
		RetrurnModel result = new RetrurnModel("200","�ļ��ϴ����");
		String username = userDao.token2username(token);
		String filename = UUID.randomUUID().toString().replaceAll("\\-", "");
		String filepath = "C:\\BYSJFiles\\"+UUID.randomUUID().toString().replaceAll("\\-", "")+"\\";
		String hz = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		//��֤token�Ƿ���Ч
		if(username==null) {
			result.setCode("201");
			result.setMsg("token��Ч�����µ�¼");
			return result;
		}


		//�������doc�ļ�����ֹ
		System.out.println("�ļ���׺:"+hz);
		if(!hz.equals(".doc")) {
			result.setCode("201");
			result.setMsg("�벻Ҫ�ϴ�.doc������ļ���ʽ");
			return result;
		}

		//���Ŀ¼�������򴴽�����Ŀ¼
		File path = new File(filepath);
		if ( !path.exists()){
			path.mkdir();  
            System.out.println("�����ļ���·��Ϊ��"+ filepath);  
        } 
		File dest = new File(filepath+filename+hz);

		try {
			file.transferTo(dest);
			//�ļ������ɷ����ļ����ƺ�·��
			Map<String,String> resultData = new HashMap<String,String>();
			resultData.put("filename", filename);
			resultData.put("filepath", filepath);
			result.setData(resultData);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return new RetrurnModel("500","�ϴ�ʧ��");
		} catch (IOException e) {
			e.printStackTrace();
			return new RetrurnModel("500","�ϴ�ʧ��");
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","�ϴ�ʧ��");
		}
		return result;
	}

	@Override
	public RetrurnModel fileInfo(String params,String token) {
		RetrurnModel result = new RetrurnModel("200","�ϴ��ɹ�!");
		String username = userDao.token2username(token);
		//��֤token�Ƿ���Ч
		if(username==null) {
			result.setCode("201");
			result.setMsg("token��Ч�����µ�¼");
			return result;
		}

		try {
			Map<String,Object> data = JSON.toJavaObject((JSON)JSON.parse(params), Map.class);
			String systemid = UUID.randomUUID().toString().replaceAll("\\-", "");
			Files file = new Files();
			file.setSystemid(systemid);
			file.setCn_name(data.get("title").toString());
			file.setFilename(data.get("filename").toString());
			file.setFilepath(data.get("filepath").toString());
			file.setOpen(data.get("public").toString());
			file.setSort(data.get("sort").toString());
			file.setTags(data.get("tags").toString());
			file.setUserid(username);
			file.setCjsj(new Date());

			fileDao.save(file);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����!");
		}
		return result;
	}

	@Override
	public RetrurnModel findFiles(String params) {
		RetrurnModel result = new RetrurnModel("200","success");
		try {
			Map<String,Object> data = JSON.toJavaObject((JSON)JSON.parse(params), Map.class);

			String sort = data.get("sort") ==null ? null:data.get("sort").toString();
			String tags = data.get("tags") ==null ? null:data.get("tags").toString();
			List<Files> list = fileDao.FindFileByTagAndSort(sort, tags);
			//��װ��������
			List<Map<String,String>> resultData = new ArrayList<Map<String,String>>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(Files file : list) {
				Map<String,String> listdata = new HashMap<String,String>();
				String user = userDao.username2name(file.getUserid());
				String cjsj = sdf.format(file.getCjsj());
				listdata.put("systemid",file.getSystemid());
				listdata.put("user",user);
				listdata.put("filename",file.getCn_name());
				listdata.put("cjsj",cjsj);
				resultData.add(listdata);
			}
			result.setData(resultData);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}
		return result;
	}

	@Override
	public RetrurnModel previewFile(String systemid,String token) {
		RetrurnModel result = new RetrurnModel("200","success");
		String username = userDao.token2username(token);
		try {
			Map<String,Object> file = fileDao.getFileInfo(systemid);
			String filename = file.get("filename") ==null ? null:file.get("filename").toString();
			String path = file.get("filepath") ==null ? null:file.get("filepath").toString();
			//��һ��Ϊ������ֹ����
			if(filename==null || path == null) {
				result.setCode("201");
				result.setMsg("�Ҳ����ļ�Ŷ");
				return result;
			}
			String htmlid = fileDao.getFileHtml(systemid) ==null ? null:fileDao.getFileHtml(systemid);
			String html="";
			if(htmlid==null) {
				html = Word2Html.Doc2Html(path, filename);
			}else {
				html = htmlDao.getFileHtml(htmlid).getHtml();
			}


			//��֤token�Ƿ���Ч,��Ч�������ʷ��������
			if(username!=null) {
				History history = historyDao.getHis1(username);//��������һ�������ʷ
				if(history==null||!history.getFileid().equals(systemid)) {
					//���û����ʷ��¼�����һ����ʷ����������Ĳ���ͬһ���ļ������
					String hisid = UUID.randomUUID().toString().replaceAll("\\-", "");
					History newhis = new History();
					newhis.setSystemid(hisid);
					newhis.setFileid(systemid);
					newhis.setUserid(username);
					newhis.setCjsj(new Date());
					historyDao.save(newhis);
				}else {
					if(history!=null) {//�������û�м�¼����²鿴ʱ��
						history.setCjsj(new Date());
						historyDao.save(history);
					}
				}

			}
			result.setData(html);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}
		return result;
	}

	@Override
	public RetrurnModel download(String systemid) {
		RetrurnModel result = new RetrurnModel("200","success");
		try {
			Map<String,Object> file = fileDao.getFileInfo(systemid);
			String filename = file.get("filename") ==null ? null:file.get("filename").toString()+".doc";
			String path = file.get("filepath") ==null ? null:file.get("filepath").toString();
			//��һ��Ϊ������ֹ����
			if(filename==null || path == null) {
				result.setCode("201");
				result.setMsg("�Ҳ����ļ�Ŷ");
				return result;
			}
			result.setData(file);
		}catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}
		return result;
	}

	@Override
	public RetrurnModel edit(String systemid, String token) {
		RetrurnModel result = new RetrurnModel("200","success");
		String username = userDao.token2username(token);
		//��֤token�Ƿ���Ч
		if(username==null) {
			result.setCode("201");
			result.setMsg("token��Ч�����µ�¼");
			return result;
		}
		//token��Ч��ȡ�ļ�userid
		Files file = fileDao.getFileById(systemid);
		if(!file.getUserid().equals(username)) {//�����ļ��ϴ���
			//У���ϴ���������Ȩ
			if(qxDao.checkEditQx(systemid, username)<=0) {
				result.setCode("201");
				result.setMsg("û�д��ļ��ı༭Ȩ��Ŷ��");
				return result;
			}
		}
		//Ȩ��У��ͨ��
		String filename = file.getFilename() ==null ? null:file.getFilename();
		String path = file.getFilepath() ==null ? null:file.getFilepath();
		String html="";
		try {
			String htmlid = fileDao.getFileHtml(systemid) ==null ? null:fileDao.getFileHtml(systemid);

			if(htmlid==null) {
				html = Word2Html.Doc2Html(path, filename);
			}else {
				html = htmlDao.getFileHtml(htmlid).getHtml();
			}
			result.setData(html);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}



		return result;
	}

	@Override
	@Transactional
	public RetrurnModel save(String param, String token) {
		RetrurnModel result = new RetrurnModel("200","����ɹ�");
		try {
			String username = userDao.token2username(token);
			Map<String,Object> params = JSON.toJavaObject((JSON)JSON.parse(param), Map.class);
			String systemid = params.get("systemid").toString();
			//��֤token�Ƿ���Ч
			if(username==null) {
				result.setCode("201");
				result.setMsg("token��Ч�����µ�¼");
				return result;
			}
			Files file = fileDao.getFileById(systemid);
			if(!file.getUserid().equals(username)) {//�����ļ��ϴ���
				//У���ϴ���������Ȩ
				if(qxDao.checkEditQx(systemid, username)<=0) {
					result.setCode("201");
					result.setMsg("û�д��ļ��ı༭Ȩ��Ŷ��");
					return result;
				}
			}
			//Ȩ��У��ͨ��
			FileHis his = new FileHis(); //������ʷ�ļ�����
			BeanUtils.copyProperties(file, his);
			String hisid = UUID.randomUUID().toString().replaceAll("\\-", "");
			his.setSystemid(hisid);
			his.setFileid(file.getSystemid());
			his.setUserid(username); 
			hisDao.save(his);
			//��ʷ��¼������ɸ��������ļ���Ϣ
			String html = params.get("context").toString();
			String newfilename = UUID.randomUUID().toString().replaceAll("\\-", "");
			Word2Html.html2Wrod(html, newfilename, file.getFilepath());
			//�ļ�����ɳɹ��������ļ���Ϣ
			file.setFilename(newfilename);
			file.setCjsj(new Date());
			String htmlid = UUID.randomUUID().toString().replaceAll("\\-", "");
			file.setHtml(htmlid);
			fileDao.save(file);
			//����html��Ϣ
			FileHtml htmlObj = new FileHtml();
			htmlObj.setSystemid(htmlid); 
			htmlObj.setHtml(html);
			htmlDao.save(htmlObj);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}

		return result;
	}

	@Override
	public RetrurnModel getMyFile(String token) {
		RetrurnModel result = new RetrurnModel("200","success");
		String userid = userDao.token2username(token)==null?null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("��¼��Ϣ��ʧЧ�������µ�¼");
			return result;
		}
		String name = userDao.username2name(userid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Files> files = fileDao.getFileByUserid(userid);
		//��װ��������
		List<Map<String,Object>> resultData = new ArrayList<Map<String,Object>>();
		for(Files file : files) {
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("systemid", file.getSystemid());
			data.put("filename",file.getCn_name());
			data.put("name", name);
			data.put("cjsj", sdf.format(file.getCjsj()));
			if(file.getOpen()!=null&&file.getOpen().equals("true")) {
				data.put("open", true);
			}else {
				data.put("open", false);
			}
			resultData.add(data);
		}
		result.setData(resultData);
		return result;
	}

	@Override
	@Transactional
	public RetrurnModel updateOpen(String token,String id) {
		RetrurnModel result = new RetrurnModel("200","�޸ĳɹ�");
		String userid = userDao.token2username(token)==null?null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("��¼��Ϣ��ʧЧ�������µ�¼");
			return result;
		}
		try {
			//���ݵ�ǰ��open״̬ȡ��
			String open=null;
			if(fileDao.getOpen(id).equals("false")) {
				open="true";
			}else {
				open="false";
			}
			int i =fileDao.updateOpenByID(open, id, userid);
			if(i<=0) {
				result.setCode("201");
				result.setMsg("�޸�ʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}
		return result;
	}

	@Override
	@Transactional
	public RetrurnModel deleteFile(String token,String id) {
		RetrurnModel result = new RetrurnModel("200","ɾ���ɹ�");
		String userid = userDao.token2username(token)==null?null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("��¼��Ϣ��ʧЧ�������µ�¼");
			return result;
		}
		try {
			//��ȡɾ��������
			Map<String,Object> fileinfo=fileDao.findFileByUseridSystemid(userid, id);
			String path = fileinfo.get("filepath")==null?null:fileinfo.get("filepath").toString();
			if(path==null) {
				result.setCode("201");
				result.setMsg("�Ҳ�������ļ�Ŷ");
				return result;
			}
			//��ʼɾ���ļ�
			fileDao.deleteById(id);
			//ɾ����ʷ
			List<String> list = hisDao.getDeleteID(id);
			for(String hisid : list) {
				hisDao.deleteById(hisid);
			}

			File file = new File(path);
			deleteDirectory(file);

		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}
		return result;
	}

	/****
	 * 
	 * @param file
	 */
    public void deleteDirectory(File file){
        try {
        	File[] list = file.listFiles();
            for (File f:list){
                    //ɾ���ļ�
                    f.delete();
            }
            //���±���һ���ļ������ļ��Ƿ���ɾ���ɾ���ɾ���ɾ�����ɾ���ļ��С�
            if (file.listFiles().length <=0 ){
                file.delete();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public RetrurnModel findFile(String input) {
		RetrurnModel result = new RetrurnModel("200","success");
		List<Files> files = fileDao.findFile(input);
		List<Map<String,Object>> resultData = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Files f:files) {
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("systemid",f.getSystemid());
			data.put("filename", f.getCn_name());
			data.put("name",userDao.username2name(f.getUserid()));
			data.put("cjsj", sdf.format(f.getCjsj()));
			data.put("sort", f.getSort());
			data.put("tags", f.getTags());
			resultData.add(data);
		}
		result.setData(resultData);
		return result;
	}

}