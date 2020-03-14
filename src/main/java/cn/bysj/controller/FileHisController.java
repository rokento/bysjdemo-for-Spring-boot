package cn.bysj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.bysj.service.FileHisService;
import cn.bysj.utils.RetrurnModel;

@RestController
@RequestMapping("app/filehis")
public class FileHisController {

	@Autowired
	private FileHisService hisService;



	@RequestMapping(value="/gethis/{fileid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel gethis(@PathVariable("fileid")String fileid) {
		RetrurnModel result = new RetrurnModel("200","success");
		try {
			result.setData(hisService.getHis(fileid));
		} catch (Exception e) {
			e.printStackTrace();
			return new RetrurnModel("500","ÏµÍ³´íÎó");
		}
		return result;
	}

	@RequestMapping(value="/lookhis/{hisid}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel lookHis(@PathVariable("hisid")String hisid) {
		return hisService.lookHis(hisid);
	}
}