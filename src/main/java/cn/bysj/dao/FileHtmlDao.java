package cn.bysj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.bysj.entity.FileHtml;

public interface FileHtmlDao extends JpaRepository<FileHtml, String> {

	@Query(nativeQuery=true,value="select*from t_filehtml where systemid=:id")
	public FileHtml getFileHtml(@Param("id")String id);

}