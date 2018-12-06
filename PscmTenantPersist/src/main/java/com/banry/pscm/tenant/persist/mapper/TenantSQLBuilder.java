package com.banry.pscm.tenant.persist.mapper;

import org.apache.ibatis.jdbc.SQL;

public class TenantSQLBuilder {
	
	public String findTenantBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tenant");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}

}
