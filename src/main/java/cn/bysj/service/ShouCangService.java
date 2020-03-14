package cn.bysj.service;

import cn.bysj.utils.RetrurnModel;

public interface ShouCangService {
	/****
	 * ����ղ�
	 * @param fileid
	 * @param token
	 * @return
	 */
	public RetrurnModel addSc(String fileid,String token);

	/****
	 * ɾ��һ���ղ�
	 * @param scid
	 * @param token
	 * @return
	 */
	public RetrurnModel deleteSc(String fileid,String token);

	/****
	 * ��ȡ�����ղ�
	 * @param token
	 * @return
	 */
	public RetrurnModel getSc(String token);



	/****
	 * У���Ƿ��ղ�ĳ���ļ�
	 * @param fileid
	 * @param token
	 * @return
	 */
	public RetrurnModel checkSc(String fileid,String token);
}
