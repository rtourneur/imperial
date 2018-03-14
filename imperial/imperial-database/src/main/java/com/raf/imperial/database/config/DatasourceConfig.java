package com.raf.imperial.database.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import liquibase.integration.spring.SpringLiquibase;

/**
 * Datasource configurator for Spring.
 * 
 * @author RAF
 */
@Configuration
public class DatasourceConfig {

  /** Spring environment. */
  @Autowired
  private Environment env;

  /**
   * Configure the data source.
   * 
   * @return the datasource
   */
  @Bean
  public DataSource dataSource() {
    final BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(this.env.getProperty("datasource.driver"));
    dataSource.setUrl(this.env.getProperty("datasource.url"));
    dataSource.setUsername(this.env.getProperty("datasource.username"));
    dataSource.setPassword(this.env.getProperty("datasource.password"));

    return dataSource;
  }

  /**
   * Populates the database.
   * 
   * @return the Spring liquibase loader
   */
  @Bean
  public SpringLiquibase liquibase() {
    final SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog("classpath:liquibase/db-changelog-master.xml");
    liquibase.setDataSource(dataSource());
    return liquibase;
  }
}
