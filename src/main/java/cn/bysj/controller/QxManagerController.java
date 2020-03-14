package cn.bysj.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.bysj.service.FilesService;
import cn.bysj.service.QxManagerService;
import cn.bysj.utils.RetrurnModel;

@RestController
@RequestMapping("app/qxManager")
public class QxManagerController {

	@Autowired
	private QxManagerService qxService;


	/****
	 * 新增权限
	 * @param param
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public RetrurnModel add(@RequestBody String param,@RequestHeader("token")String token) {
		String fileid="";
		String userid="";
		try {
			Map<String,Object> params = JSON.toJavaObject((JSON)JSON.parse(param), Map.class);
			fileid=params.get("fileid").toString();
			userid=params.get("userid").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","参数格式错误");
		}
		return qxService.add(token, fileid, userid);
	}

	/****
	 * 取消授权
	 * @param qxid
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/delete/{qxid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel delete(@PathVariable("qxid") String qxid,@RequestHeader("token")String token) {
		return qxService.deleteQx(token, qxid);
	}

	/****
	 * 获取权限列表
	 * @param fileid
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/get/{fileid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel getqx(@PathVariable("fileid") String fileid,@RequestHeader("token")String token) {
		return qxService.getQxByfileid(token, fileid);
	}
}