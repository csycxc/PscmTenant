package com.banry.pscm.tenant.biz;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.tenant.persist.dao.DynamicSourceMapper;
import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.biz.DynamicSource;
import com.banry.pscm.tenant.service.biz.DynamicSourceService;
@Service
public class DynamicSourceServiceImpl implements DynamicSourceService {
	
	@Autowired
	DynamicSourceMapper dsMapper;
	
	private static Logger log = LoggerFactory.getLogger(DynamicSourceServiceImpl.class);
	
	@Override
	public DynamicSource findDynamicSourceByCode(String code) throws TenantException{
		try {
			return dsMapper.selectByPrimaryKey(code);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public DynamicSource findDynamicSourceByName(String name) throws TenantException{
		try {
			String sqlWhere = "source_name = '"+name+"'";
			return dsMapper.findDSBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<DynamicSource> findDynamicSourceList() throws TenantException{
		try {
			return dsMapper.findDSList();
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void saveDynamicSource(DynamicSource DynamicSource) throws TenantException{
		try {
			dsMapper.insert(DynamicSource);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void saveDynamicSourceSelective(DynamicSource DynamicSource) throws TenantException{
		try {
			dsMapper.insertSelective(DynamicSource);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteDynamicSource(String sourceCode) throws TenantException{
		try {
			return dsMapper.deleteByPrimaryKey(sourceCode);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void updateDynamicSource(DynamicSource DynamicSource) throws TenantException{
		try {
			dsMapper.updateByPrimaryKey(DynamicSource);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void updateDynamicSourceSelective(DynamicSource DynamicSource) throws TenantException{
		try {
			dsMapper.updateByPrimaryKeySelective(DynamicSource);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

}
