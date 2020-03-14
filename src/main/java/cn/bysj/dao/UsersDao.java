package cn.bysj.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bysj.entity.Users;

@Repository
public interface UsersDao extends JpaRepository<Users, String>{

	@Query(nativeQuery=true,value="select * from users where username=:username")
	Users getUserInfo(@Param("username") String username);

	/*@Query("select count(t) from users t where username=:username")
	Integer countUsername(@Param("username") String username);*/

	@Modifying
	@Query(nativeQuery=true,value="update users set touxiang=:data where username=:username")
	int updateTouXiang(@Param("data")String data,@Param("username")String username);


	@Query(nativeQuery=true,value="select username from users where token=:token")
	String token2username(@Param("token") String token);

	@Query(nativeQuery=true,value="select name from users where username=:username")
	String username2name(@Param("username") String username);
}