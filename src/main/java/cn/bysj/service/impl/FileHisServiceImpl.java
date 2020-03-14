package cn.bysj.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bysj.dao.FileHisDao;
import cn.bysj.dao.FileHtmlDao;
import cn.bysj.dao.UsersDao;
import cn.bysj.entity.FileHis;
import cn.bysj.service.FileHisService;
import cn.bysj.utils.RetrurnModel;
import cn.bysj.utils.Word2Html;

@Service
public class FileHisServiceImpl implements FileHisService {

	@Autowired
	private FileHisDao hisDao;

	@Autowired
	private UsersDao userDao;

	@Autowired
	private FileHtmlDao htmlDao;

	@Override
	public List<Map<String, Object>> getHis(String fileid) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<FileHis> his = hisDao.getHisByfileID(fileid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		if(his!=null&&his.size()>0) {
			for(FileHis file : his) {
				Map<String, Object> data = new HashMap<String, Object>();
				String cjsj = sdf.format(file.getCjsj());
				data.put("systemid", file.getSystemid());
				data.put("cjsj", cjsj);
				data.put("editUser", userDao.username2name(file.getUserid()));
				result.add(data);
			}
		}
		return result;
	}


	@Override
	public RetrurnModel lookHis(String hisid) {
		RetrurnModel result = new RetrurnModel("200","success");
		try {
			FileHis file = hisDao.getHisByHisid(hisid);
			String filename = file.getFilename() ==null ? null:file.getFilename();
			String path = file.getFilepath() ==null ? null:file.getFilepath();
			//有一个为空则中止操作
			if(filename==null || path == null) {
				result.setCode("201");
				result.setMsg("找不到文件哦");
				return result;
			}
			String htmlid = file.getHtml() ==null ? null:file.getHtml();
			String html="";
			if(htmlid==null) {
				html = Word2Html.Doc2Html(path, filename);
			}else {
				html = htmlDao.getFileHtml(htmlid).getHtml();
			}
			result.setData(html);
		}catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","系统错误!");
		}
		return result;
	}

}