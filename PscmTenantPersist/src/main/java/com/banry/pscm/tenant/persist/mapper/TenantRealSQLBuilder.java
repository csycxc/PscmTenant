package com.banry.pscm.tenant.persist.mapper;

import org.apache.ibatis.jdbc.SQL;

public class TenantRealSQLBuilder {
	
	public String findTenantRealBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tenant_real");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}

}
