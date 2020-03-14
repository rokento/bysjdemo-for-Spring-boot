package cn.bysj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.bysj.entity.ShouCang;

public interface ShouCangDao extends JpaRepository<ShouCang, String>{

	@Query(nativeQuery=true,value="select * from shoucang where userid=:userid")
	public List<ShouCang> getMyShouCang(@Param("userid")String userid);

	@Query(nativeQuery=true,value="select count(1) from shoucang where userid=:userid"
			+ " and fileid=:fileid ")
	public int checkSc(@Param("userid")String userid,@Param("fileid")String fileid);

	@Query(nativeQuery=true,value="select systemid from shoucang where userid=:userid"
			+ " and fileid=:fileid limit 1 ")
	public String getSystemidByUseridFileid(@Param("userid")String userid,@Param("fileid")String fileid);
}