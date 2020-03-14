package cn.bysj.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.bysj.service.FilesService;
import cn.bysj.utils.RetrurnModel;

@RestController
@RequestMapping("app/files")
public class FilesController {

	@Autowired
	private FilesService fileService;


	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public RetrurnModel upload(@RequestParam("file") MultipartFile file,@RequestHeader("token") String token) {
		return fileService.upload(file,token);
	}

	@RequestMapping(value="/fileinfo",method=RequestMethod.POST)
	@ResponseBody
	public RetrurnModel fileInfo(@RequestBody String param,@RequestHeader("token") String token) {
		System.out.println(token);
		return fileService.fileInfo(param,token);
	}
	@RequestMapping(value="/getfiles",method=RequestMethod.POST)
	@ResponseBody
	public RetrurnModel getFileList(@RequestBody String param) {

		return fileService.findFiles(param);
	}
	/****
	 * 
	 * @param systemid
	 * @return
	 */
	@RequestMapping(value="/preview/{systemid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel preview(@PathVariable("systemid") String systemid,@RequestHeader("token") String token) {

		return fileService.previewFile(systemid,token);
	}

	/****
	 * 
	 * @param systemid
	 * @param request
	 * @param response
	 */

	@RequestMapping(value="/download/{systemid}",method=RequestMethod.GET)
	@ResponseBody
	public void download(@PathVariable("systemid")String systemid,HttpServletRequest request, HttpServletResponse response) {
		RetrurnModel result = fileService.download(systemid);
		if(!result.getCode().equals("201")&&!result.getCode().equals("500")) {
			Map<String,Object> info = (Map<String, Object>) result.getData();
			String filename = info.get("filename").toString();
			String path = info.get("filepath").toString();
			File file = new File(path,filename+".doc");
			//文件存在
			if(file.exists()) {
	             byte[] buffer = new byte[1024];
	             FileInputStream fis = null;
	             BufferedInputStream bis = null;
	             try {
	                    fis = new FileInputStream(file);
	                    bis = new BufferedInputStream(fis);
	                    OutputStream os = response.getOutputStream();
	                    response.setContentType("application/force-download");
	                    response.setCharacterEncoding("UTF-8");
		                response.addHeader("Content-Disposition", "attachment;fileName=" + filename+".doc");// 设置文件名
	                    IOUtils.copy(bis, os);
	                    bis.close();
	                    fis.close();
	                    os.close();
	                }catch (Exception e) {
	                	e.printStackTrace();

	            }
			}
		}
	}

	/****
	 * 编辑接口
	 * @param systemid
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/edit/{systemid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel edit(@PathVariable("systemid")String systemid,@RequestHeader("token")String token) {

		return fileService.edit(systemid, token);
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public RetrurnModel save(@RequestBody String param,@RequestHeader("token")String token) {
		return fileService.save(param, token);
	}

	/****
	 * 修改是否公开状态
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/editopen/{systemid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel editopen(@RequestHeader("token")String token,@PathVariable("systemid")String systemid) {

		return fileService.updateOpen(token, systemid);
	}

	/****
	 * 删除单个文件
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/delete/{systemid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel deleteFile(@RequestHeader("token")String token,@PathVariable("systemid")String systemid) {

		return fileService.deleteFile(token, systemid);
	}

	@RequestMapping(value="/find/{input}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel find(@PathVariable("input")String input) {
		return fileService.findFile(input);
	}
}