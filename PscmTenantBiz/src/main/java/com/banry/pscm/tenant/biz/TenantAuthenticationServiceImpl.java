package com.banry.pscm.tenant.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.tenant.persist.dao.TenantMapper;
import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.authen.TenantAuthenticationService;
import com.banry.pscm.tenant.service.biz.Tenant;

@Service
public class TenantAuthenticationServiceImpl implements TenantAuthenticationService {
	
	@Autowired
	private TenantMapper tenMapper;
	
	private static Logger log = LoggerFactory.getLogger(TenantAuthenticationServiceImpl.class);

	@Override
	public Tenant AuthenticationTenant(String account) throws TenantException {
		try {
			return tenMapper.findTenantByAccount(account);
		} catch(Exception e){
			log.error("Exception异常：", e);
			throw new TenantException(e.getMessage(),e);
		}
	}

}
