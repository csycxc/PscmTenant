package com.banry.pscm.tenant.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.tenant.persist.dao.DynamicSourceMapper;
import com.banry.pscm.tenant.persist.dao.TenantMapper;
import com.banry.pscm.tenant.persist.dao.TenantRealMapper;
import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.biz.DynamicSource;
import com.banry.pscm.tenant.service.biz.Tenant;
import com.banry.pscm.tenant.service.biz.TenantRealWithBLOBs;
import com.banry.pscm.tenant.service.biz.TenantService;
@Service
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	TenantMapper tenMapper;
	@Autowired
	DynamicSourceMapper dsMapper;
	@Autowired
	TenantRealMapper trMapper;
	
	private static Logger log = LoggerFactory.getLogger(TenantServiceImpl.class);

	@Override
	public Tenant findTenantByCode(String code) throws TenantException{
			try {
				return tenMapper.selectByPrimaryKey(code);
			} catch(Exception e){
				log.error("Exception异常：", e);
				throw new TenantException(e.getMessage(),e);
			}
	}

	@Override
	public Tenant findTenantByRealCode(String realCode) throws TenantException{
		try {
			String sqlWhere="real_code = '"+realCode+"'";
			return tenMapper.findTenantBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public Tenant findTenantBySourceCode(String sourceCode) throws TenantException{
		try {
			String sqlWhere="source_code = '"+sourceCode+"'";
			return tenMapper.findTenantBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public Tenant findTenantByType(int tenantType) throws TenantException{
		try {
			String sqlWhere="tenant_type = "+tenantType;
			return tenMapper.findTenantBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<Tenant> findTenantByName(String name) throws TenantException{
		try {
			String sqlWhere="name = '"+name+"'";
			return tenMapper.findTenantListBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<Tenant> findTenantByStatus(int status) throws TenantException{
		try {
			String sqlWhere="status = "+status;
			return tenMapper.findTenantListBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<Tenant> findTenantByCreateTime(Date startTime, Date endTime) throws TenantException{
		try {
			String sqlWhere="start_time = '"+startTime+"' and EndTime = '"+endTime+"'";
			return tenMapper.findTenantListBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<Tenant> findTenantList() throws TenantException{
		try {
			return tenMapper.findTenantList();
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<Tenant> findSonTenantList(String parentCode) throws TenantException{
		try {
			String sqlWhere="parent_code = '"+parentCode+"'";
			return tenMapper.findTenantListBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void saveTenant(Tenant tenant) throws TenantException{
		try {
			tenMapper.insert(tenant);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void saveTenantSelective(Tenant tenant) throws TenantException{
		try {
			tenMapper.insertSelective(tenant);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public int deletetenant(String code) throws TenantException{
		try {
			return tenMapper.deleteByPrimaryKey(code);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void updateTenantByCode(Tenant tenant) throws TenantException{
		try {
			tenMapper.updateByPrimaryKey(tenant);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void updateTenantByCodeSelective(Tenant tenant) throws TenantException{
		try {
			tenMapper.updateByPrimaryKeySelective(tenant);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void joinTenantAndReal(String tenantCode, String realCode) throws TenantException{
		try {
			Tenant ten = tenMapper.selectByPrimaryKey(tenantCode);
			TenantRealWithBLOBs tenReal = trMapper.selectByPrimaryKey(realCode);
			ten.setTenantReal(tenReal);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void deleteJoinTenantAndReal(String realCode) throws TenantException{
		try {
			String sqlWhere="real_code = '"+realCode+"'";
			tenMapper.findTenantBySqlWhere(sqlWhere).setTenantReal(null);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void joinTenantAndDataSource(String tenantCode, String sourceCode) throws TenantException{
		try {
			Tenant ten = tenMapper.selectByPrimaryKey(tenantCode);
			DynamicSource ds = dsMapper.selectByPrimaryKey(sourceCode);
			ten.setSource(ds);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void deleteJoinTenantAndDataSource(String sourceCode) throws TenantException{
		try {
			String sqlWhere="source_code = '"+sourceCode+"'";
			tenMapper.findTenantBySqlWhere(sqlWhere).setSource(null);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public int setRealCodeNull(String tenantCode) throws TenantException {
		try {
			return tenMapper.setRealCodeNull(tenantCode);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}
	
	@Override
	public int setSourceCodeNull(String tenantCode) throws TenantException {
		try {
			return tenMapper.setSourceCodeNull(tenantCode);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public Tenant findTenantByAccount(String account) throws TenantException {
		try {
			return tenMapper.findTenantByAccount(account);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

}
