package com.banry.pscm.tenant.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


/**
 * 总的启动项目
 *
 */
@SpringBootApplication(exclude={JpaRepositoriesAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {"com.banry.pscm.tenant.service.biz", 
		                                            "com.banry.pscm.tenant.service.authen",
		                                            "com.banry.pscm.tenant.biz",
		                                            "com.banry.pscm.tenant.persist.dao.config"}) 
@MapperScan("com.banry.pscm.tenant.persist.dao")
public class PscmTenantWebApplication 
{
    public static void main(String[] args) {
		SpringApplication.run(PscmTenantWebApplication.class, args);
	}
}
