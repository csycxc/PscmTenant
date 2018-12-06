package com.banry.pscm.tenant.service.biz;

import java.util.List;

import com.banry.pscm.tenant.service.TenantException;;


/**
 * @author crj
 * @Description 租户实体的信息的访问接口。租户实体包括租户所在的企业和项目部。
 * @date 2018-04-05
 * @version 1.0
 */
public interface TenantRealService {
	/**
	 * @Description 根据code查找租户实体，包括实体的二进制数据表示的图片
	 * 
	 * @param code 租户编码
	 * @return 租户实体
	 * @throws TenantException
	 */
	public TenantRealWithBLOBs findTenantRealByCode(String code) throws TenantException;
	
	
	/**
	 * @Description 根据租户实体名查找租户实体。这里要实现模糊查找，在实现的sql语句中，要用包括含关系，且名称里取出关键字包含
	 * 
	 * @param name 租户实体名
	 * @return 相似名称的所有租户实体
	 * @throws TenantException
	 */
	public List<TenantRealWithBLOBs> findTenantRealByName(String name) throws TenantException;
	
	/**
	 * @Description  查询所有租户实体
	 * @return 所有租户实体，包括实体的图片
	 * @throws TenantException
	 */
	public List<TenantRealWithBLOBs> findTenantRealList() throws TenantException;
	
	/**
	 * @Description  查询所有未关联租户的租户实体
	 * @return 所有租户实体，包括实体的图片
	 * @throws TenantException
	 */
	public List<TenantRealWithBLOBs> findTenantRealListNotRelationTenant() throws TenantException;

	/**
	 * @Description 保存租户实体信息，包括图片。无论属性是否为null，都保存。如果为null，则置空。
	 * 
	 * @param tenantReal
	 * @return
	 * @throws TenantException
	 */
	public void saveTenantReal(TenantRealWithBLOBs tenantReal) throws TenantException;
	
	/**
	 * @Description 保存租户实体信息，包括图片。如果某属性为null，则不保存。
	 * 
	 * @param tenantReal
	 * @return
	 * @throws TenantException
	 */
	public void saveTenantRealSelective(TenantRealWithBLOBs tenantReal) throws TenantException;
	
	/**
	 * @Description 保存租户实体信息。无论属性是否为null，都保存。如果为null，则置空。
	 * 
	 * @param tenantReal
	 * @return
	 * @throws TenantException
	 */
	public void saveTenantReal(TenantReal tenantReal) throws TenantException;
	
	/**
	 * @Description 保存租户实体信息。如果某属性为null，则不保存。
	 * 
	 * @param tenantReal
	 * @return
	 * @throws TenantException
	 */
	public void saveTenantRealSelective(TenantReal tenantReal) throws TenantException;

	/**
	 * @Description 根据指定租户实体code删除租户。由于租户实体关联着租户，所以删除前必须进行检查，
	 * 如果有关联，则提示，并禁止删除。
	 * 
	 * @param realCode实体编码
	 * @return
	 * @throws TenantException
	 */
	public int deleteTenantReal(String realCode) throws TenantException;
	
	
	/**
	 * @Description 根据指定租户实体code，更新相应的该租户的二进制数据。
	 * 
	 * @param realCode实体编码；blobName二进制数据名，这里仅仅是bizlicense，otherscanner有效的；blob二进制数据。
	 * @return
	 * @throws TenantException
	 */
	public void updateTenantRealBlob(String realCode,String blobName,byte[] blob)throws TenantException;
	
	/**
	 * @Description 根据指定租户实体code，更新相应的该租户数据。如果数据为null，则同步更新，这样就将原来的数据置空。
	 * 
	 * @param tenantReal租户实体
	 * @return
	 * @throws TenantException
	 */
	public void updateTenantReal(TenantReal tenantReal)throws TenantException;
	
	/**
	 * @Description 根据指定租户实体code，更新相应的该租户数据。如果数据为null，则不更新。
	 * 
	 * @param tenantReal租户实体，包含二进制数据
	 * @return
	 * @throws TenantException
	 */
	public void updateTenantRealSelective(TenantRealWithBLOBs tenantReal)throws TenantException;
}
