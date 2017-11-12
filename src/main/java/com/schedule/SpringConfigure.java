package com.schedule;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = SpringConfigure.class)
@PropertySource({ "classpath:database.properties" })
public class SpringConfigure {

	@Resource
	private Environment env;

	@Resource(name = "srcDataSource")
	private DataSource srcDataSource;
 

	@Bean(name = "srcTransactionManager")
	public PlatformTransactionManager srcTransactionManager() {
		return new DataSourceTransactionManager(srcDataSource);
	}

	 

	@Bean(name = "srcJdbcTemplate", autowire = Autowire.BY_NAME)
	public JdbcTemplate srcJdbcTemplate() {
		return new JdbcTemplate(srcDataSource);
	}

	 

	@Bean(name = "srcDataSource", destroyMethod = "close", autowire = Autowire.BY_NAME)
	public DataSource srcDataSource() throws Exception {
		final BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(env.getProperty("src.jdbc.driverClass",
				"com.mysql.jdbc.Driver"));
		dataSource.setUser(env.getProperty("src.jdbc.username", "root"));
		dataSource.setPassword(env.getProperty("src.jdbc.password", "root"));
		dataSource.setJdbcUrl(env.getProperty("src.jdbc.url"));
		dataSource.setIdleConnectionTestPeriodInMinutes(60);
		dataSource.setIdleMaxAgeInMinutes(240);
		dataSource.setMaxConnectionsPerPartition(5);
		dataSource.setMinConnectionsPerPartition(2);
		dataSource.setPartitionCount(2);
		dataSource.setAcquireIncrement(10);
		dataSource.setStatementsCacheSize(150);
		return dataSource;
	}

	 

}
