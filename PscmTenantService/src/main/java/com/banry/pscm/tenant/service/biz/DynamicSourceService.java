package com.banry.pscm.tenant.service.biz;

import java.util.List;

import com.banry.pscm.tenant.service.TenantException;;


/**
 * @author crj
 * @Description 动态数据源信息访问接口。一般情况下，一个租户一个数据源，这些数据源需要动态生成。
 * @date 2018-04-05
 * @version 1.0
 */
public interface DynamicSourceService {
	/**
	 * @Description 根据code查找数据源
	 * 
	 * @param code 数据源编码
	 * @return 数据源
	 * @throws TenantException
	 */
	public DynamicSource findDynamicSourceByCode(String code) throws TenantException;
	
	
	/**
	 * @Description 根据数据源名查找数据源。
	 * 
	 * @param name 数据源名
	 * @return 指定名的数据源
	 * @throws TenantException
	 */
	public DynamicSource findDynamicSourceByName(String name) throws TenantException;
	
	/**
	 * @Description  查询所有数据源
	 * @return 所有数据源
	 * @throws TenantException
	 */
	public List<DynamicSource> findDynamicSourceList() throws TenantException;

	/**
	 * @Description 保存数据源
	 * 
	 * @param DynamicSource
	 * @return
	 * @throws TenantException
	 */
	public void saveDynamicSource(DynamicSource DynamicSource) throws TenantException;
	
	/**
	 * @Description 保存数据源。如果某属性为null，则不保存。
	 * 
	 * @param DynamicSource
	 * @return
	 * @throws TenantException
	 */
	public void saveDynamicSourceSelective(DynamicSource DynamicSource) throws TenantException;

	/**
	 * @Description 根据指定数据源code删除数据源。由于数据源关联着租户，所以删除前必须进行检查，
	 * 如果有关联，则提示，并禁止删除。
	 * 
	 * @param sourceCode数据源编码
	 * @return
	 * @throws TenantException
	 */
	public int deleteDynamicSource(String sourceCode) throws TenantException;
	
	/**
	 * @Description 根据指定数据源code，更新相应的该数据源数据。如果数据为null，则同步更新，这样就将原来的数据置空。
	 * 
	 * @param DynamicSource数据源
	 * @return
	 * @throws TenantException
	 */
	public void updateDynamicSource(DynamicSource DynamicSource)throws TenantException;
	
	/**
	 * @Description 根据指定数据源code，更新相应的该数据源户数据。如果数据为null，则不更新。
	 * 
	 * @param DynamicSource数据源
	 * @return
	 * @throws TenantException
	 */
	public void updateDynamicSourceSelective(DynamicSource DynamicSource)throws TenantException;
}
