package com.banry.pscm.tenant.persist.mapper;

import org.apache.ibatis.jdbc.SQL;

public class PscmBusinessSQLBuilder {

	public String findPscmBusinessBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("business");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}
	
}
