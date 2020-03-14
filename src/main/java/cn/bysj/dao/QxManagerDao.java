package cn.bysj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.bysj.entity.QxManager;

public interface QxManagerDao extends JpaRepository<QxManager, String> {

	@Query(nativeQuery=true,value="select*from t_qxmanager where fileid=:fileid")
	public List<QxManager> getQxByfileid(@Param("fileid")String fileid);

	@Query(nativeQuery=true,value="select fileid from t_qxmanager where systemid=:systemid")
	public String getfileidByID(@Param("systemid")String systemid);
	/****
	 * 校验用户是否有编辑权限
	 * @param fileid,userid
	 * @return int
	 */
	@Query(nativeQuery=true,value="select count(1) from t_qxmanager where fileid=:fileid and userid=:userid")
	public int checkEditQx(@Param("fileid")String fileid,@Param("userid")String userid);
}