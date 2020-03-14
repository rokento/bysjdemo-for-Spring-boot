package cn.bysj.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bysj.dao.FilesDao;
import cn.bysj.dao.ShouCangDao;
import cn.bysj.dao.UsersDao;
import cn.bysj.entity.ShouCang;
import cn.bysj.service.ShouCangService;
import cn.bysj.utils.RetrurnModel;

@Service
public class ShouCangServiceImpl implements ShouCangService {

	@Autowired
	private FilesDao fileDao;

	@Autowired
	private UsersDao userDao;

	@Autowired
	private ShouCangDao scDao;

	@Override
	public RetrurnModel addSc(String fileid, String token) {
		RetrurnModel result = new RetrurnModel("200","����ӵ��ҵ��ղ���");
		String userid = userDao.token2username(token)==null? null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("��¼��Ϣ��Ч�����µ�¼");
			return result;
		}
		try {
			ShouCang sc = new ShouCang();
			String scid = UUID.randomUUID().toString().replaceAll("\\-", "");
			sc.setSystemid(scid);
			sc.setFileid(fileid);
			sc.setUserid(userid);
			sc.setCjsj(new Date());

			scDao.save(sc);
		} catch (Exception e) {
			return new RetrurnModel("500","ϵͳ����");
		}
		return result;
	}

	@Override
	@Transactional
	public RetrurnModel deleteSc(String fileid, String token) {
		RetrurnModel result = new RetrurnModel("200","ɾ���ɹ�");
		String userid = userDao.token2username(token)==null? null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("��¼��Ϣ��Ч�����µ�¼");
			return result;
		}
		try {
			String scid = scDao.getSystemidByUseridFileid(userid, fileid)==null? null:scDao.getSystemidByUseridFileid(userid, fileid);
			if(scid==null) {
				result.setCode("201");
				result.setMsg("û�������ղ�Ŷ");
				return result;
			}
			scDao.deleteById(scid);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}
		return result;
	}

	@Override
	public RetrurnModel getSc(String token) {
		RetrurnModel result = new RetrurnModel("200","ɾ���ɹ�");
		String userid = userDao.token2username(token)==null? null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("��¼��Ϣ��Ч�����µ�¼");
			return result;
		}
		try {
			List<ShouCang> mysc = scDao.getMyShouCang(userid);
			List<Map<String,Object>> resultData = new ArrayList<Map<String,Object>>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(ShouCang sc:mysc) {
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("systemid", sc.getSystemid());
				data.put("filename", fileDao.systemid2cn_name(sc.getFileid()));
				data.put("fileid", sc.getFileid());
				data.put("name", userDao.username2name(sc.getUserid()));
				data.put("cjsj", sdf.format(sc.getCjsj()));
				resultData.add(data);
			}
			result.setData(resultData);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����");
		}

		return result;
	}

	@Override
	public RetrurnModel checkSc(String fileid, String token) {
		RetrurnModel result = new RetrurnModel("200","success");
		String userid = userDao.token2username(token)==null? null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("token��Ч");
			return result;
		}
		try {
			int i = scDao.checkSc(userid, fileid);
			if(i<=0) {
				//û���ղظ��ļ�
				result.setData(false);
			}else {
				result.setData(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ϵͳ����,�޷�ȡ��У����");
		}
		return result;
	}
}
