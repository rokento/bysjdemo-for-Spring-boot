package cn.bysj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.bysj.entity.Files;

public interface FilesDao extends JpaRepository<Files, String> {


	@Query(nativeQuery=true,value="select * from t_files where open='true' and "
			+ "(tags=:tags or :tags is null) and (sort=:sort or :sort is null) order by cjsj desc")
	public List<Files> FindFileByTagAndSort(@Param("sort") String sort,@Param("tags") String tags);

	/**
	 * 根据ID获取文件名称和路径
	 * **/
	@Query(nativeQuery=true,value="select filename,filepath from t_files where systemid=:id")
	public Map<String,Object> getFileInfo(@Param("id") String id);

	@Query(nativeQuery=true,value="select filename,filepath from t_files where systemid=:id and userid=:userid")
	public Map<String,Object> findFileByUseridSystemid(@Param("userid") String userid,@Param("id") String id);

	@Query(nativeQuery=true,value="select * from t_files where systemid=:id")
	public Files getFileById(@Param("id") String id);

	@Query(nativeQuery=true,value="select html from t_files where systemid=:id")
	public String getFileHtml(@Param("id") String id);

	@Query(nativeQuery=true,value="select*from t_files where userid=:uid order by cjsj desc")
	public List<Files> getFileByUserid(@Param("uid") String uid);


	@Query(nativeQuery=true,value="select open from t_files where systemid=:id")
	public String getOpen(@Param("id") String id);
	/****
	 * 修改Open
	 * @param open
	 * @param id
	 * @param userid
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true,value="update t_files set open=:open where systemid=:id and userid=:userid")
	public int updateOpenByID(@Param("open")String open,@Param("id")String id,@Param("userid")String userid);

	/****
	 * 删除单个文件
	 * @param id
	 * @param userid
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from t_files where systemid=:id and userid=:userid")
	public int deleteFile(@Param("id")String id,@Param("userid")String userid);

	@Query(nativeQuery=true,value="select cn_name from t_files where systemid=:id")
	public String systemid2cn_name(@Param("id")String id);

	@Query(nativeQuery=true,value="select count(1) from t_files where systemid=:id")
	public int countByID(@Param("id")String id);

	@Query(nativeQuery=true,value="select * from t_files where cn_name like CONCAT('%',:input,'%')"
			+ " or sort like CONCAT('%',:input,'%') or tags like CONCAT('%',:input,'%')")
	public List<Files> findFile(@Param("input") String input);

	/**
	 * 校验用户是否是文件拥有者
	 * **/
	@Query(nativeQuery=true,value="select count(1) from t_files where systemid=:systemid and userid=:userid")
	public int checkFileMaster(@Param("systemid")String id,@Param("userid")String userid);
}