package cn.bysj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bysj.dao.DictionaryDao;
import cn.bysj.service.KindService;

@Service
public class KindServiceImpl implements KindService {

	@Autowired
	private DictionaryDao dicDao;

	@Override
	public List<Map<String, Object>> getDicByKind(String kind) {

		return dicDao.getDicByKind(kind);
	}

}