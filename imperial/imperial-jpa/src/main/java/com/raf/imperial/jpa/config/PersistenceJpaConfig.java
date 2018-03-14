package com.raf.imperial.jpa.config;

import static org.hibernate.cfg.AvailableSettings.CACHE_REGION_FACTORY;
import static org.hibernate.cfg.AvailableSettings.DEFAULT_BATCH_FETCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.MAX_FETCH_DEPTH;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.USE_QUERY_CACHE;
import static org.hibernate.cfg.AvailableSettings.USE_SECOND_LEVEL_CACHE;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.raf.fwk.util.config.UtilConfig;

import lombok.NoArgsConstructor;

/**
 * Spring configuration class for persistence.
 * 
 * @author RAF
 */
@Configuration
@Import(value = { UtilConfig.class })
@ComponentScan("com.raf.imperial.jpa")
@PropertySource({ "classpath:persistence.properties" })
@EnableTransactionManagement
@NoArgsConstructor
public class PersistenceJpaConfig {

  /** Spring environment. */
  @Autowired
  private Environment env;

  /**
   * Initialize the entity manager factory.
   * 
   * @return entity manager factory
   * @throws NamingException
   *           if datasource not found in context
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
    final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource());
    emf.setPackagesToScan(new String[] { this.env.getProperty("package.scan") });

    final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    emf.setJpaVendorAdapter(vendorAdapter);
    emf.setJpaProperties(additionalProperties());
    emf.setPersistenceUnitName(this.env.getProperty("persistence.name"));
    emf.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
    return emf;
  }

  /**
   * Set up the datasource.
   * 
   * @return the datasource
   * @throws NamingException
   *           if datasource not found in context
   */
  @Bean
  public DataSource dataSource() throws NamingException {
    final Context ctx = new InitialContext();
    return (DataSource) ctx.lookup("java:/comp/env/jdbc/descent");
  }

  /**
   * Add the transaction manager.
   * 
   * @param emf
   *          the entity manager factory
   * @return the transaction manager
   */
  @Bean
  public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
    final JpaTransactionManager trxManager = new JpaTransactionManager();
    trxManager.setEntityManagerFactory(emf);

    return trxManager;
  }

  /**
   * Returns the Persistence Exception Translation Processor.
   * 
   * @return the Persistence Exception Translation Processor
   */
  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  /**
   * Returns the additional properties for hibernate.
   * 
   * @return the additional properties
   */
  private Properties additionalProperties() {
    final Properties properties = new Properties();
    setHibernateProperty(properties, DIALECT);
    setHibernateProperty(properties, SHOW_SQL);
    setHibernateProperty(properties, FORMAT_SQL);
    setHibernateProperty(properties, MAX_FETCH_DEPTH);
    setHibernateProperty(properties, DEFAULT_BATCH_FETCH_SIZE);
    setHibernateProperty(properties, STATEMENT_BATCH_SIZE);
    setHibernateProperty(properties, CACHE_REGION_FACTORY);
    setHibernateProperty(properties, USE_SECOND_LEVEL_CACHE);
    setHibernateProperty(properties, USE_QUERY_CACHE);
    return properties;
  }

  /**
   * Add the hibernate property from the environment.
   * 
   * @param properties
   *          the properties
   * @param propertyName
   *          the property name the
   */
  private void setHibernateProperty(final Properties properties, final String propertyName) {
    if (this.env.containsProperty(propertyName)) {
      properties.setProperty(propertyName, this.env.getProperty(propertyName));
    }
  }

}
