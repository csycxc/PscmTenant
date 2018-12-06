package com.banry.pscm.tenant.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.tenant.persist.dao.TenantMapper;
import com.banry.pscm.tenant.persist.dao.TenantRealMapper;
import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.biz.Tenant;
import com.banry.pscm.tenant.service.biz.TenantReal;
import com.banry.pscm.tenant.service.biz.TenantRealService;
import com.banry.pscm.tenant.service.biz.TenantRealWithBLOBs;

@Service
public class TenantRealServiceImpl implements TenantRealService {
	
	@Autowired
	TenantRealMapper trMapper;
	@Autowired
	TenantMapper tenMapper;
	
	private static Logger log = LoggerFactory.getLogger(TenantRealServiceImpl.class);

	@Override
	public TenantRealWithBLOBs findTenantRealByCode(String code) throws TenantException{
		try {
			return trMapper.selectByPrimaryKey(code);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<TenantRealWithBLOBs> findTenantRealByName(String name) throws TenantException{
		try {
			String sqlWhere="name='"+name+"'";
			return trMapper.findTenRealBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<TenantRealWithBLOBs> findTenantRealList() throws TenantException{
		try {
			return trMapper.findTenantRealList();
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void saveTenantReal(TenantRealWithBLOBs tenantReal) throws TenantException{
		try {
			trMapper.insert(tenantReal);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void saveTenantRealSelective(TenantRealWithBLOBs tenantReal) throws TenantException{
		try {
			trMapper.insertSelective(tenantReal);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void saveTenantReal(TenantReal tenantReal) throws TenantException{
		try {
			trMapper.insertTenantReal(tenantReal);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void saveTenantRealSelective(TenantReal tenantReal) throws TenantException{
		try {
			trMapper.insertTenantReal(tenantReal);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteTenantReal(String realCode) throws TenantException{
		try {
			String sqlWhere="real_code = '"+realCode+"'";
			Tenant ten = tenMapper.findTenantBySqlWhere(sqlWhere);
			if(ten!=null){
				ten.setTenantReal(null);
				tenMapper.updateByPrimaryKey(ten);
			}
			return trMapper.deleteByPrimaryKey(realCode);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void updateTenantRealBlob(String realCode, String blobName, byte[] blob) throws TenantException{
		try {
			TenantRealWithBLOBs trb=new TenantRealWithBLOBs();
			trb.setRealCode(realCode);
			trb.setName(blobName);
			trb.setBizLicense(blob);
			trMapper.updateByPrimaryKeyWithBLOBs(trb);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void updateTenantReal(TenantReal tenantReal) throws TenantException{
		try {
			trMapper.updateByPrimaryKey(tenantReal);
			throw new TenantException();
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void updateTenantRealSelective(TenantRealWithBLOBs tenantReal) throws TenantException{
		try {
			trMapper.updateByPrimaryKeySelective(tenantReal);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<TenantRealWithBLOBs> findTenantRealListNotRelationTenant() throws TenantException {
		try {
			return trMapper.findTenantRealListNotRelationTenant();
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

}
