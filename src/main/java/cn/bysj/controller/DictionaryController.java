package cn.bysj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bysj.service.KindService;
import cn.bysj.utils.RetrurnModel;

@Controller
@RequestMapping("app/dictionary")
//@CrossOrigin(value = "http://localhost:8081")
public class DictionaryController {
	@Autowired
	private KindService ksercice;

	@RequestMapping(value="/get/{kind}",method=RequestMethod.GET)
	@ResponseBody
	public RetrurnModel getDic(@PathVariable("kind")String kind) {
		RetrurnModel result = new RetrurnModel("200","success");
		try {
			result.setData(ksercice.getDicByKind(kind));
		} catch (Exception e) {
			new RetrurnModel("500","ÏµÍ³´íÎó");
		}

		return result;

	}

}