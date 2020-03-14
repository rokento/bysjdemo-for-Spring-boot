package cn.bysj.service;

import cn.bysj.utils.RetrurnModel;

public interface QxManagerService {

	/****
	 * 授予用户文件编辑权限
	 * @param token
	 * @param fileid
	 * @param userid
	 * @return
	 */
	public RetrurnModel add(String token,String fileid,String userid);
	/****
	 * 取消一条授权
	 * @param token
	 * @param qxid
	 * @return
	 */
	public RetrurnModel deleteQx(String token,String qxid);

	/****
	 * 根据文件ID获取权限列表
	 * @param token
	 * @param fileid
	 * @return
	 */
	public RetrurnModel getQxByfileid(String token,String fileid);
}