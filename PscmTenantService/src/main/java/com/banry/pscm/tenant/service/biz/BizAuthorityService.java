package com.banry.pscm.tenant.service.biz;

import java.util.List;

import com.banry.pscm.tenant.service.TenantException;;


/**
 * @author crj
 * @Description 业务功能模块管理及租户授权信息访问接口。此接口包括两部分，业务及业务的授权
 * @date 2018-04-05
 * @version 1.0
 */
public interface BizAuthorityService {
	/**
	 * @Description 根据code查找业务模块
	 * 
	 * @param code 业务模块编码
	 * @return 业务模块
	 * @throws TenantException
	 */
	public PscmBusiness findPscmBusinessByCode(String code) throws TenantException;
	
	
	/**
	 * @Description 根据业务模块名查找业务模块。
	 * 
	 * @param name 业务模块名
	 * @return 指定名的业务模块
	 * @throws TenantException
	 */
	public PscmBusiness findPscmBusinessByName(String name) throws TenantException;
	
	/**
	 * @Description  查询所有业务模块
	 * @return 所有业务模块
	 * @throws TenantException
	 */
	public List<PscmBusiness> findPscmBusinessList() throws TenantException;

	/**
	 * @Description 保存业务模块
	 * 
	 * @param PscmBusiness
	 * @return
	 * @throws TenantException
	 */
	public void savePscmBusiness(PscmBusiness PscmBusiness) throws TenantException;
	
	/**
	 * @Description 保存业务模块。如果某属性为null，则不保存。
	 * 
	 * @param PscmBusiness
	 * @return
	 * @throws TenantException
	 */
	public void savePscmBusinessSelective(PscmBusiness PscmBusiness) throws TenantException;

	/**
	 * @Description 根据指定业务模块code删除业务模块源。由于业务模块关联着租户，所以删除前必须进行检查，
	 * 如果有关联，则提示，并禁止删除。
	 * 
	 * @param bizCode业务模块编码
	 * @return
	 * @throws TenantException
	 */
	public int deletePscmBusiness(String bizCode) throws TenantException;
	
	/**
	 * @Description 根据指定业务模块code，更新相应的该业务模块。如果数据为null，则同步更新，这样就将原来的数据置空。
	 * 
	 * @param PscmBusiness业务模块
	 * @return
	 * @throws TenantException
	 */
	public void updatePscmBusiness(PscmBusiness PscmBusiness)throws TenantException;
	
	/**
	 * @Description 根据指定业务模块code，更新相应的该业务模块。如果数据为null，则不更新。
	 * 
	 * @param PscmBusiness业务模块
	 * @return
	 * @throws TenantException
	 */
	public void updatePscmBusinessSelective(PscmBusiness PscmBusiness)throws TenantException;
	
	/**
	 * @Description  查询指定租户授权的模块
	 * @param 租户编码
	 * @return 指定租户的权限
	 * @throws TenantException
	 */
	public List<BizAuthority> findBizAuthorityListByTenantCode(String tenantCode) throws TenantException;

	/**
	 * @Description  查询指定业务授予的租户
	 * @param 业务编码
	 * @return 指定业务的权限
	 * @throws TenantException
	 */
	public List<BizAuthority> findBizAuthorityListByBizCode(String bizCode) throws TenantException;

	/**
	 * @Description 对指定的租户的业务模块进行授权
	 * 
	 * @param BizAuthority指定租户的所有业务模块。这里的租户码必须相等。
	 * @return
	 * @throws TenantException
	 */
	public void joinBizAuthority(List<BizAuthority> bizAuthorityList) throws TenantException;
	
	/**
	 * @Description 对指定的租户的业务模块的授权进行解除
	 * 
	 * @param BizAuthority指定租户的所有业务模块。这里的租户码必须相等。
	 * @return
	 * @throws TenantException
	 */
	public int deleteJoinBizAuthority(List<BizAuthority> bizAuthorityList) throws TenantException;
}
