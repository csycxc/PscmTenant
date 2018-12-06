package com.banry.pscm.tenant.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.tenant.persist.dao.BizAuthorityMapper;
import com.banry.pscm.tenant.persist.dao.PscmBusinessMapper;
import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.biz.BizAuthority;
import com.banry.pscm.tenant.service.biz.BizAuthorityService;
import com.banry.pscm.tenant.service.biz.PscmBusiness;
@Service
public class BizAuthorityServiceImpl implements BizAuthorityService {
	
	@Autowired
	BizAuthorityMapper baMapper;
	@Autowired
	PscmBusinessMapper pbMapper;
	
	private static Logger log = LoggerFactory.getLogger(BizAuthorityServiceImpl.class);

	@Override
	public PscmBusiness findPscmBusinessByCode(String code) throws TenantException{
		try {
			return pbMapper.selectByPrimaryKey(code);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public PscmBusiness findPscmBusinessByName(String name) throws TenantException{
		try {
			String sqlWhere = "biz_chinese_name = '"+name+"'";
			return pbMapper.findPscmBusinessBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<PscmBusiness> findPscmBusinessList() throws TenantException{
		try {
			return pbMapper.findPscmBusinessList();
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void savePscmBusiness(PscmBusiness PscmBusiness) throws TenantException{
		try {
			pbMapper.insert(PscmBusiness);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void savePscmBusinessSelective(PscmBusiness PscmBusiness) throws TenantException{
		try {
			pbMapper.insertSelective(PscmBusiness);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public int deletePscmBusiness(String bizCode) throws TenantException{
		try {
			return pbMapper.deleteByPrimaryKey(bizCode);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void updatePscmBusiness(PscmBusiness PscmBusiness) throws TenantException{
		try {
			pbMapper.updateByPrimaryKey(PscmBusiness);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void updatePscmBusinessSelective(PscmBusiness PscmBusiness) throws TenantException{
		try {
			pbMapper.updateByPrimaryKeySelective(PscmBusiness);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<BizAuthority> findBizAuthorityListByTenantCode(String tenantCode) throws TenantException{
		try {
			String sqlWhere="tenant_code='"+tenantCode+"'";
			return baMapper.findBizAuthListBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public List<BizAuthority> findBizAuthorityListByBizCode(String bizCode) throws TenantException{
		try {
			String sqlWhere="biz_code='"+bizCode+"'";
			return baMapper.findBizAuthListBySqlWhere(sqlWhere);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

	@Override
	public void joinBizAuthority(List<BizAuthority> bizAuthorityList) throws TenantException{
		try {
			baMapper.insertBizAuthority(bizAuthorityList);
		} catch (Exception e) {
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}

	}

	@Override
	public int deleteJoinBizAuthority(List<BizAuthority> bizAuthorityList) throws TenantException{
		try {
			return baMapper.deleteBizAuthority(bizAuthorityList);
		} catch (Exception e) {
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}

	}

}
