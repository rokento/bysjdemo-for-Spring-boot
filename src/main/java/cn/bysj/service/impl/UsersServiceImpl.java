package cn.bysj.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.Base64;

import cn.bysj.dao.FilesDao;
import cn.bysj.dao.HistoryDao;
import cn.bysj.dao.UserWtDao;
import cn.bysj.dao.UsersDao;
import cn.bysj.entity.History;
import cn.bysj.entity.UserWt;
import cn.bysj.entity.Users;
import cn.bysj.service.UsersService;
import cn.bysj.utils.RetrurnModel;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
   	private UsersDao usersDao;

	@Autowired
	private UserWtDao wtDao;

	@Autowired
	private FilesDao fileDao;

	@Autowired
	private HistoryDao hisDao;


	@Override
	public RetrurnModel addUser(String params) {
		RetrurnModel result = new RetrurnModel("200","ע��ɹ�");
		try {
			Map<String,Object> map = JSON.toJavaObject((JSON)JSON.parse(params), Map.class);
			//����user
			Users u = new Users();
			//����id��token��Ϣ
			String systemid = UUID.randomUUID().toString().replaceAll("\\-", "");
			String token = UUID.randomUUID().toString().replaceAll("\\-", "");
			//String����תDate
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date csrq=sdf.parse(map.get("csrq").toString());
			//����User��Ϣ
			u.setSystemid(systemid);
			u.setName(map.get("name").toString());
			u.setSex(map.get("sex").toString());
			u.setUsername(map.get("username").toString());
			u.setPwd(map.get("password1").toString());
			u.setCsrq(csrq);
			u.setToken(token);

			usersDao.save(u);

			//������JSONתΪjava����
			Map<String,Object> wt1 = JSON.toJavaObject((JSON)JSON.parse(map.get("wt1").toString()), Map.class);
			Map<String,Object> wt2 = JSON.toJavaObject((JSON)JSON.parse(map.get("wt2").toString()), Map.class);
			Map<String,Object> wt3 = JSON.toJavaObject((JSON)JSON.parse(map.get("wt3").toString()), Map.class);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			list.add(wt1);
			list.add(wt2);
			list.add(wt3);
			//�����û��ܱ�����
			for(Map<String,Object> wtMap : list) {
				UserWt wt = new UserWt();
				String wtid = UUID.randomUUID().toString().replaceAll("\\-", "");
				wt.setSystemid(wtid);
				wt.setUserid(systemid);
				wt.setWtmc(wtMap.get("wt").toString());
				wt.setWtda(wtMap.get("da").toString());

				wtDao.save(wt);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ע��ʧ��,ϵͳ����");
		}

		return result;
	}

	@Override
	public RetrurnModel login(String param) {
		RetrurnModel result = new RetrurnModel("200","��¼�ɹ�");
		try {
			Map<String,Object> map = JSON.toJavaObject((JSON)JSON.parse(param), Map.class);
			Users user = usersDao.getUserInfo(map.get("username").toString());
			if(user==null) {
				result.setCode("201");
				result.setMsg("�˺Ų�����!");
				return result;
			}
			boolean login = user.getPwd().equals(map.get("password").toString());
			if(login) {
				Map<String,String> resultMap=new HashMap<String, String>();
				resultMap.put("name", user.getName());
				resultMap.put("username",user.getUsername());
				resultMap.put("token",user.getToken());
				resultMap.put("touxiang", user.getTouxiang());
				SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd");
				String scrq=sdf.format(user.getCsrq()).toString();
				resultMap.put("csrq",scrq);
				result.setData(resultMap);
			}else {
				result.setCode("201");
				result.setMsg("�˺Ż��������!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}
		return result;
	}

	/**
	 * ����ͷ��
	 * **/
	@Override
	@Transactional
	public RetrurnModel uploadTouxiang(String param) {
		RetrurnModel result = new RetrurnModel("200","ͷ������ɹ�");
		try {
			Map<String,Object> map = JSON.toJavaObject((JSON)JSON.parse(param), Map.class);
			String username = map.get("username").toString();
			//�Ƿ��е�¼��
			if(map.get("username").toString().equals("")||map.get("username")==null) {
				result.setCode("500");
				result.setMsg("ͷ�����ʧ��,�����µ�¼");
			}
			int i = usersDao.updateTouXiang(map.get("data").toString(), username);
			System.out.println("ͷ���޸Ľ����"+i);
			if(i<=0) {
				result.setCode("201");
				result.setMsg("ͷ�����ʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}
		return result;
	}

	@Override
	public RetrurnModel getHistory(String token) {
		RetrurnModel result = new RetrurnModel("200","success");
		String username = usersDao.token2username(token);
		//��֤token�Ƿ���Ч
		if(username==null) {
			result.setCode("201");
			result.setMsg("token��Ч�����µ�¼");
			return result;
		}
		//��ȡ��ʷ
		List<History> his = hisDao.getHisByuserid(username);
		//��װ��������
		List<Map<String,Object>> resultData = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(History h : his) {
			Map<String,Object> data = new HashMap<String,Object>();
			String cn_name = fileDao.systemid2cn_name(h.getFileid())==null ? null:fileDao.systemid2cn_name(h.getFileid());
			data.put("systemid", h.getFileid());
			//���cn_name�ֶβ�������ɾ��������¼
			if(cn_name!=null) {
				data.put("filename",cn_name);
			}else {
				hisDao.deleteById(h.getSystemid());
			}
			data.put("name", usersDao.username2name(h.getUserid()));
			data.put("cjsj", sdf.format(h.getCjsj()));
			resultData.add(data);
		}
		result.setData(resultData);
		return result;
	}



}