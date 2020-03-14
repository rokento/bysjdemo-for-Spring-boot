package cn.bysj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.bysj.entity.History;

public interface HistoryDao extends JpaRepository<History, String> {

	@Query(nativeQuery=true,value="select*from History where userid=:userid ORDER BY cjsj desc LIMIT 1")
	public History getHis1(@Param("userid") String userid);

	@Query(nativeQuery=true,value="select*from History where userid=:userid ORDER BY cjsj desc")
	public List<History> getHisByuserid(@Param("userid") String userid);
	@Query(nativeQuery=true,value="select systemid from History where userid=:userid and fileid=:fileid")
	public String getSystemid(@Param("userid")String userid,@Param("fileid")String fileid);
}