package cn.bysj.service;

import java.util.List;
import java.util.Map;

public interface KindService {
	/**
	 * ����kind����ֵ�
	 * **/
	public List<Map<String,Object>> getDicByKind(String kind);

}