package cn.bysj.service;

import cn.bysj.utils.RetrurnModel;

public interface QxManagerService {

	/****
	 * �����û��ļ��༭Ȩ��
	 * @param token
	 * @param fileid
	 * @param userid
	 * @return
	 */
	public RetrurnModel add(String token,String fileid,String userid);
	/****
	 * ȡ��һ����Ȩ
	 * @param token
	 * @param qxid
	 * @return
	 */
	public RetrurnModel deleteQx(String token,String qxid);

	/****
	 * �����ļ�ID��ȡȨ���б�
	 * @param token
	 * @param fileid
	 * @return
	 */
	public RetrurnModel getQxByfileid(String token,String fileid);
}