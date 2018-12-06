package com.banry.pscm.tenant.service.authen;

import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.biz.Tenant;;


/**
 * @author crj
 * @Description 租户登录时，先进行租户认证，调用此接口。此接口全部为restapi.通过pscmtenantweb中的restcontrol对外提供服务。
 * @data 2018-04-06
 * @version 1.0
 */
public interface TenantAuthenticationService {
	/**
	 * @Description 根据code 验证租户信息
	 * 
	 * @param tenantCode 租户编码
	 * @return 租户信息
	 * @throws TenantException
	 */
	public Tenant AuthenticationTenant(String account) throws TenantException;
	
}
