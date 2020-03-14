package cn.bysj.service;

import java.util.List;
import java.util.Map;

import cn.bysj.utils.RetrurnModel;

public interface FileHisService {

	public List<Map<String,Object>> getHis(String fileid);

	public RetrurnModel lookHis(String hisid);

}