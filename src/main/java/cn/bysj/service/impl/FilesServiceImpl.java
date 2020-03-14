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
		RetrurnModel result = new RetrurnModel("200","文件上传完毕");
		String username = userDao.token2username(token);
		String filename = UUID.randomUUID().toString().replaceAll("\\-", "");
		String filepath = "C:\\BYSJFiles\\"+UUID.randomUUID().toString().replaceAll("\\-", "")+"\\";
		String hz = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		//验证token是否有效
		if(username==null) {
			result.setCode("201");
			result.setMsg("token无效请重新登录");
			return result;
		}


		//如果不是doc文件则终止
		System.out.println("文件后缀:"+hz);
		if(!hz.equals(".doc")) {
			result.setCode("201");
			result.setMsg("请不要上传.doc以外的文件格式");
			return result;
		}

		//如果目录不存在则创建创建目录
		File path = new File(filepath);
		if ( !path.exists()){
			path.mkdir();  
            System.out.println("创建文件夹路径为："+ filepath);  
        } 
		File dest = new File(filepath+filename+hz);

		try {
			file.transferTo(dest);
			//文件输出完成返回文件名称和路径
			Map<String,String> resultData = new HashMap<String,String>();
			resultData.put("filename", filename);
			resultData.put("filepath", filepath);
			result.setData(resultData);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return new RetrurnModel("500","上传失败");
		} catch (IOException e) {
			e.printStackTrace();
			return new RetrurnModel("500","上传失败");
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","上传失败");
		}
		return result;
	}

	@Override
	public RetrurnModel fileInfo(String params,String token) {
		RetrurnModel result = new RetrurnModel("200","上传成功!");
		String username = userDao.token2username(token);
		//验证token是否有效
		if(username==null) {
			result.setCode("201");
			result.setMsg("token无效请重新登录");
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
			return new RetrurnModel("500","系统错误!");
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
			//封装返回数据
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
			return new RetrurnModel("500","系统错误");
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
			//有一个为空则中止操作
			if(filename==null || path == null) {
				result.setCode("201");
				result.setMsg("找不到文件哦");
				return result;
			}
			String htmlid = fileDao.getFileHtml(systemid) ==null ? null:fileDao.getFileHtml(systemid);
			String html="";
			if(htmlid==null) {
				html = Word2Html.Doc2Html(path, filename);
			}else {
				html = htmlDao.getFileHtml(htmlid).getHtml();
			}


			//验证token是否有效,有效则添加历史否则跳过
			if(username!=null) {
				History history = historyDao.getHis1(username);//获得最近的一条浏览历史
				if(history==null||!history.getFileid().equals(systemid)) {
					//如果没有历史记录或最近一条历史和现在浏览的不是同一个文件则添加
					String hisid = UUID.randomUUID().toString().replaceAll("\\-", "");
					History newhis = new History();
					newhis.setSystemid(hisid);
					newhis.setFileid(systemid);
					newhis.setUserid(username);
					newhis.setCjsj(new Date());
					historyDao.save(newhis);
				}else {
					if(history!=null) {//如果不是没有记录则更新查看时间
						history.setCjsj(new Date());
						historyDao.save(history);
					}
				}

			}
			result.setData(html);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","系统错误");
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
			//有一个为空则中止操作
			if(filename==null || path == null) {
				result.setCode("201");
				result.setMsg("找不到文件哦");
				return result;
			}
			result.setData(file);
		}catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","系统错误");
		}
		return result;
	}

	@Override
	public RetrurnModel edit(String systemid, String token) {
		RetrurnModel result = new RetrurnModel("200","success");
		String username = userDao.token2username(token);
		//验证token是否有效
		if(username==null) {
			result.setCode("201");
			result.setMsg("token无效请重新登录");
			return result;
		}
		//token有效获取文件userid
		Files file = fileDao.getFileById(systemid);
		if(!file.getUserid().equals(username)) {//不是文件上传者
			//校验上传者有无授权
			if(qxDao.checkEditQx(systemid, username)<=0) {
				result.setCode("201");
				result.setMsg("没有此文件的编辑权限哦。");
				return result;
			}
		}
		//权限校验通过
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
			return new RetrurnModel("500","系统错误");
		}



		return result;
	}

	@Override
	@Transactional
	public RetrurnModel save(String param, String token) {
		RetrurnModel result = new RetrurnModel("200","保存成功");
		try {
			String username = userDao.token2username(token);
			Map<String,Object> params = JSON.toJavaObject((JSON)JSON.parse(param), Map.class);
			String systemid = params.get("systemid").toString();
			//验证token是否有效
			if(username==null) {
				result.setCode("201");
				result.setMsg("token无效请重新登录");
				return result;
			}
			Files file = fileDao.getFileById(systemid);
			if(!file.getUserid().equals(username)) {//不是文件上传者
				//校验上传者有无授权
				if(qxDao.checkEditQx(systemid, username)<=0) {
					result.setCode("201");
					result.setMsg("没有此文件的编辑权限哦。");
					return result;
				}
			}
			//权限校验通过
			FileHis his = new FileHis(); //创建历史文件对象
			BeanUtils.copyProperties(file, his);
			String hisid = UUID.randomUUID().toString().replaceAll("\\-", "");
			his.setSystemid(hisid);
			his.setFileid(file.getSystemid());
			his.setUserid(username); 
			hisDao.save(his);
			//历史记录保存完成更新最新文件信息
			String html = params.get("context").toString();
			String newfilename = UUID.randomUUID().toString().replaceAll("\\-", "");
			Word2Html.html2Wrod(html, newfilename, file.getFilepath());
			//文件输出成成功保存新文件信息
			file.setFilename(newfilename);
			file.setCjsj(new Date());
			String htmlid = UUID.randomUUID().toString().replaceAll("\\-", "");
			file.setHtml(htmlid);
			fileDao.save(file);
			//保存html信息
			FileHtml htmlObj = new FileHtml();
			htmlObj.setSystemid(htmlid); 
			htmlObj.setHtml(html);
			htmlDao.save(htmlObj);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","系统错误");
		}

		return result;
	}

	@Override
	public RetrurnModel getMyFile(String token) {
		RetrurnModel result = new RetrurnModel("200","success");
		String userid = userDao.token2username(token)==null?null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("登录信息已失效，请重新登录");
			return result;
		}
		String name = userDao.username2name(userid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Files> files = fileDao.getFileByUserid(userid);
		//封装返回数据
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
		RetrurnModel result = new RetrurnModel("200","修改成功");
		String userid = userDao.token2username(token)==null?null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("登录信息已失效，请重新登录");
			return result;
		}
		try {
			//根据当前的open状态取反
			String open=null;
			if(fileDao.getOpen(id).equals("false")) {
				open="true";
			}else {
				open="false";
			}
			int i =fileDao.updateOpenByID(open, id, userid);
			if(i<=0) {
				result.setCode("201");
				result.setMsg("修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","系统错误");
		}
		return result;
	}

	@Override
	@Transactional
	public RetrurnModel deleteFile(String token,String id) {
		RetrurnModel result = new RetrurnModel("200","删除成功");
		String userid = userDao.token2username(token)==null?null:userDao.token2username(token);
		if(userid==null) {
			result.setCode("201");
			result.setMsg("登录信息已失效，请重新登录");
			return result;
		}
		try {
			//获取删除的数据
			Map<String,Object> fileinfo=fileDao.findFileByUseridSystemid(userid, id);
			String path = fileinfo.get("filepath")==null?null:fileinfo.get("filepath").toString();
			if(path==null) {
				result.setCode("201");
				result.setMsg("找不到这个文件哦");
				return result;
			}
			//开始删除文件
			fileDao.deleteById(id);
			//删除历史
			List<String> list = hisDao.getDeleteID(id);
			for(String hisid : list) {
				hisDao.deleteById(hisid);
			}

			File file = new File(path);
			deleteDirectory(file);

		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","系统错误");
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
                    //删除文件
                    f.delete();
            }
            //重新遍历一下文件夹内文件是否已删除干净，删除干净后则删除文件夹。
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