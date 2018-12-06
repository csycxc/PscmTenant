package com.banry.pscm.tenant.service.biz;

import java.util.List;
import java.util.Date;
import com.banry.pscm.tenant.service.TenantException;;

/**
 * @author crj
 * @Description 租户及租户相关的信息的访问接口
 * @date 2018-04-04
 * @version 1.0
 */
public interface TenantService {
	
	public int setRealCodeNull(String tenantCode) throws TenantException;
	
	public int setSourceCodeNull(String tenantCode) throws TenantException;
	
	/**
	 * @Description 根据code查找租户
	 * 
	 * @param code 租户编码
	 * @return 租户Tenant
	 * @throws TenantException
	 */
	public Tenant findTenantByCode(String code) throws TenantException;

	
	/**
	 * @Description 根据实体code查找租户。实体包括有法人的企业、没有法人的项目部。
	 * 
	 * @param realCode 实体编码
	 * @return 租户Tenant
	 * @throws TenantException
	 */
	public Tenant findTenantByRealCode(String realCode) throws TenantException;
	
	/**
	 * @Description 根据数据源code查找租户
	 * 
	 * @param sourceCode 数据源编码
	 * @return 租户Tenant
	 * @throws TenantException
	 */
	public Tenant findTenantBySourceCode(String sourceCode) throws TenantException;
	
	/**
	 * @Description 根据租户类型查找租户
	 * 
	 * @param tenantType 租户类型
	 * @return 租户Tenant
	 * @throws TenantException
	 */
	public Tenant findTenantByType(int tenantType) throws TenantException;
	
	/**
	 * @Description 根据状账户查找租户
	 * 
	 * @param account 租户账户
	 * @return 租户Tenant
	 * @throws TenantException
	 */
	public Tenant findTenantByAccount(String account) throws TenantException;
	
	
	/**
	 * @Description 根据租户名查找租户。这里要实现模糊查找，在实现的sql语句中，要用包括含关系，且名称里取出关键字包含
	 * 
	 * @param name 租户名
	 * @return 相似名称的所有租户
	 * @throws TenantException
	 */
	public List<Tenant> findTenantByName(String name) throws TenantException;
	
	/**
	 * @Description 根据状态查找租户
	 * 
	 * @param status 租户状态
	 * @return 租户Tenant
	 * @throws TenantException
	 */
	public List<Tenant> findTenantByStatus(int status) throws TenantException;
	
	/**
	 * @Description 根据创建日期查找，条件为日期范围，取出创建日期在此范围内的所有租户
	 * 
	 * @param name 租户名
	 * @return 创建时间在查找的时间范围内所有的租户
	 * @throws TenantException
	 */
	public List<Tenant> findTenantByCreateTime(Date startTime, Date endTime) throws TenantException;
	
	
	/**
	 * @Description  查询所有的租户
	 * @return 
	 * @throws TenantException
	 */
	public List<Tenant> findTenantList() throws TenantException;
	
	/**
	 * @Description  查询父租户的所有的子租户
	 * @param parentCode 父租户编码
	 * @return 
	 * @throws TenantException
	 */
	public List<Tenant> findSonTenantList(String parentCode) throws TenantException;

	/**
	 * @Description 保存租户信息，不论是否为空，全部保存
	 * 
	 * @param tenant
	 * @return
	 * @throws TenantException
	 */
	public void saveTenant(Tenant tenant) throws TenantException;
	
	/**
	 * @Description 保存租户信息，如果为空，不保存
	 * 
	 * @param tenant
	 * @return
	 * @throws TenantException
	 */
	public void saveTenantSelective(Tenant tenant) throws TenantException;

	/**
	 * @Description 根据指定code删除租户。由于租户关联着业务功能模块信息、实体（企业）信息、动态数据源信息，所以删除前必须进行检查，
	 * 如果有关联，则提示，并禁止删除。
	 * 
	 * @param code
	 * @return
	 * @throws TenantException
	 */
	public int deletetenant(String code) throws TenantException;
	
	/**
	 * @Description 更新租户。租户的编码不能为空，这是更新的条件。其它项，不论为空，一律更新，如果为空，则置空。
	 * 
	 * @param tenant租户
	 * @return 
	 * @throws TenantException
	 */
	public void updateTenantByCode(Tenant tenant) throws TenantException;
	
	/**
	 * @Description 更新租户。租户的编码不能为空，这是更新的条件。其它项，惹不为空，则更新。
	 * 
	 * @param tenant租户
	 * @return 
	 * @throws TenantException
	 */
	public void updateTenantByCodeSelective(Tenant tenant) throws TenantException;
	
	/**
	 * @Description 将租户与租户实体关联起来
	 * 
	 * @param tenantCode租户编码,realCode租户实体编码
	 * @return 
	 * @throws TenantException
	 */
	public void joinTenantAndReal(String tenantCode,String realCode) throws TenantException;
	
	/**
	 * @Description 将租户与租户实体关联删除
	 * @see updateTenantByCodeSelective
	 * @param realCode租户实体编码
	 * @return 
	 * @throws TenantException
	 */
	public void deleteJoinTenantAndReal(String realCode) throws TenantException;
	
	/**
	 * @Description 将租户与租户实体关联起来
	 * @see updateTenantByCodeSelective
	 * @param tenantCode租户编码,sourceCode动态数据源编码
	 * @return 
	 * @throws TenantException
	 */
	public void joinTenantAndDataSource(String tenantCode,String sourceCode) throws TenantException;
	
	/**
	 * @Description 将租户与租户实体关联删除
	 * @see updateTenantByCodeSelective
	 * @param sourceCode动态数据源编码
	 * @return 
	 * @throws TenantException
	 */
	public void deleteJoinTenantAndDataSource(String sourceCode) throws TenantException;
	
}
