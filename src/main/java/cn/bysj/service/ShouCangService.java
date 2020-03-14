package cn.bysj.service;

import cn.bysj.utils.RetrurnModel;

public interface ShouCangService {
	/****
	 * 添加收藏
	 * @param fileid
	 * @param token
	 * @return
	 */
	public RetrurnModel addSc(String fileid,String token);

	/****
	 * 删除一条收藏
	 * @param scid
	 * @param token
	 * @return
	 */
	public RetrurnModel deleteSc(String fileid,String token);

	/****
	 * 获取个人收藏
	 * @param token
	 * @return
	 */
	public RetrurnModel getSc(String token);



	/****
	 * 校验是否收藏某个文件
	 * @param fileid
	 * @param token
	 * @return
	 */
	public RetrurnModel checkSc(String fileid,String token);
}
