package cn.bysj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.bysj.entity.FileHis;

public interface FileHisDao extends JpaRepository<FileHis, String> {

	@Query(nativeQuery=true,value="select*from t_files_his where fileid=:fileid")
	List<FileHis> getHisByfileID(@Param("fileid")String fileid);

	@Query(nativeQuery=true,value="select*from t_files_his where systemid=:systemid")
	FileHis getHisByHisid(@Param("systemid")String systemid);

	@Query(nativeQuery=true,value="select systemid from t_files_his where fileid=:fileid")
	List<String> getDeleteID(@Param("fileid")String fileid);
}