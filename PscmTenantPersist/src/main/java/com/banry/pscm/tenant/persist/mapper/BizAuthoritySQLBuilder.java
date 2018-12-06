package com.banry.pscm.tenant.persist.mapper;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.banry.pscm.tenant.service.biz.BizAuthority;

public class BizAuthoritySQLBuilder {

	public String findBizAuthorityBySqlWhere(String sqlWhere) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("biz_authority");
		if (sqlWhere != null && !"".equals(sqlWhere)) {
			sql.WHERE(sqlWhere);
		}
		return sql.toString();
	}

	public String insertBizAuthority(Map map) {
		List<BizAuthority> configs = (List<BizAuthority>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("insert into biz_authority");
		sb.append("(tenant_code,biz_code,biz_chinese_name)");
		sb.append("values");
		MessageFormat mf = new MessageFormat("#'{'list[{0}].tenantCode'}',#'{'list[{0}].bizCode'}',#'{'list[{0}].bizChineseName'}'");
		for (int i = 0; i < configs.size(); i++) {
			sb.append("(");
			sb.append(mf.format(new Object[] { i }));
			sb.append(")");

			if (i < configs.size() - 1) {
				sb.append(",");
			}
		}

		return sb.toString();
	}
	
	public String deleteBizAuthority(Map map) {
		List<BizAuthority> configs = (List<BizAuthority>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("delete from biz_authority");
		sb.append(" where");
		sb.append(" tenant_code in (");
		MessageFormat mf = new MessageFormat("#'{'list[{0}].tenantCode'}'");
		MessageFormat mf2 = new MessageFormat("#'{'list[{0}].bizCode'}'");
		for (int i = 0; i < configs.size(); i++) {
			if(i==configs.size()-1){
				sb.append(mf.format(new Object[] { i }));
			}else{
				sb.append(mf.format(new Object[] { i })+",");
			}
		}
		
		sb.append(") and biz_code in (");
		
		for (int i = 0; i < configs.size(); i++) {
			if(i==configs.size()-1){
				sb.append(mf2.format(new Object[] { i }));
			}else{
				sb.append(mf2.format(new Object[] { i })+",");
			}
		}
		sb.append(")");

		return sb.toString();
	}

}
