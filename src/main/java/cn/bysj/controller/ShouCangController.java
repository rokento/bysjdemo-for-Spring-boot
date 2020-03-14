package cn.bysj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.bysj.service.ShouCangService;
import cn.bysj.utils.RetrurnModel;

@RestController
@RequestMapping("app/sc")
public class ShouCangController {

	@Autowired
	private ShouCangService scService;

	/****
	 * 添加收藏
	 * @param fileid
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/add/{fileid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel addSc(@PathVariable("fileid")String fileid,@RequestHeader("token")String token) {

		return scService.addSc(fileid, token);
	}
	/****
	 * 删除收藏
	 * @param scid
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/delete/{scid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel deleteSc(@PathVariable("scid")String scid,@RequestHeader("token")String token) {

		return scService.deleteSc(scid, token);
	}

	/****
	 * 获取个人收藏
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/mysc",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel getSc(@RequestHeader("token") String token) {

		return scService.getSc(token);
	}

	/****
	 * 校验是否收藏
	 * @param fileid
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/checksc/{fileid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel checkSc(@PathVariable("fileid")String fileid,@RequestHeader("token")String token) {

		return scService.checkSc(fileid, token);
	}
}