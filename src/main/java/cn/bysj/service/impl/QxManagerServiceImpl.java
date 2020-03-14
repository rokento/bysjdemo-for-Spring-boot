package cn.bysj.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bysj.dao.FilesDao;
import cn.bysj.dao.QxManagerDao;
import cn.bysj.dao.UsersDao;
import cn.bysj.entity.QxManager;
import cn.bysj.service.QxManagerService;
import cn.bysj.utils.RetrurnModel;

@Service
public class QxManagerServiceImpl implements QxManagerService {
	@Autowired
	private QxManagerDao qxDao;

	@Autowired
	private UsersDao userDao;

	@Autowired
	private FilesDao fileDao;

	@Override
	public RetrurnModel add(String token, String fileid, String userid) {
		RetrurnModel result = new RetrurnModel("200","success");
		String username = userDao.token2username(token);
		//验证token是否有效
		if(username==null) {
			result.setCode("201");
			result.setMsg("登录信息无效请重新登录");
			return result;
		}
		try {
			String qxid = UUID.randomUUID().toString().replaceAll("\\-", "");
			QxManager qx = new QxManager();
			qx.setSystemid(qxid);
			qx.setFileid(fileid);
			qx.setUserid(userid);
			qx.setSqsj(new Date());
			qxDao.save(qx);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","系统错误");
		}
		return result;
	}

	@Override
	public RetrurnModel deleteQx(String token, String qxid) {
		RetrurnModel result = new RetrurnModel("200","success");
		String username = userDao.token2username(token);
		//验证token是否有效
		if(username==null) {
			result.setCode("201");
			result.setMsg("登录信息无效请重新登录");
			return result;
		}
		String fileid = qxDao.getfileidByID(qxid)==null?"":qxDao.getfileidByID(qxid);
		//判断当前用户是否是此文件拥有者
		if(fileDao.checkFileMaster(fileid, username)<=0) {
			result.setCode("201");
			result.setMsg("你没有权限管理此文件");
			return result;
		}
		try {
			qxDao.deleteById(qxid);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","系统错误");
		}
		return result;
	}

	@Override
	public RetrurnModel getQxByfileid(String token, String fileid) {
		RetrurnModel result = new RetrurnModel("200","success");
		String username = userDao.token2username(token);
		//验证token是否有效
		if(username==null) {
			result.setCode("201");
			result.setMsg("登录信息无效请重新登录");
			return result;
		}
		//判断当前用户是否是此文件拥有者
		if(fileDao.checkFileMaster(fileid, username)<=0) {
			result.setCode("201");
			result.setMsg("你没有权限管理此文件");
			return result;
		}
		List<Map<String,Object>> resultData = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			List<QxManager> qxs = qxDao.getQxByfileid(fileid);
			for(QxManager qx:qxs) {
				Map<String,Object> data = new HashedMap<String,Object>();
				data.put("systemid", qx.getSystemid());
				data.put("userid", qx.getUserid());
				data.put("name", userDao.username2name(qx.getUserid()));
				data.put("sqsj", sdf.format(qx.getSqsj()));
				resultData.add(data);
			}
			result.setData(resultData);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","系统错误");
		}
		return result;
	}

}
