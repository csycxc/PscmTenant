package com.banry.pscm.tenant.persist.mapper;

import org.apache.ibatis.jdbc.SQL;

public class DynamicSourceSQLBuilder {
	
	public String findDSBySqlWhere(String sqlWhere){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("dynamic_source");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}

}
