package cn.bysj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.bysj.entity.Dictionary;

public interface DictionaryDao extends JpaRepository<Dictionary, String>{

	@Query(nativeQuery=true,value="select kind_code,detail "
			+ "from t_dictionary where kind=:kind")
	public List<Map<String,Object>> getDicByKind(@Param("kind") String kind);

}