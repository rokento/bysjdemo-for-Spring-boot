package cn.bysj.service;

import java.util.List;
import java.util.Map;

public interface KindService {
	/**
	 * 根据kind获得字典
	 * **/
	public List<Map<String,Object>> getDicByKind(String kind);

}