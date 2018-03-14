package com.raf.imperial.jpa.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.raf.imperial.database.config.DatasourceConfig;
import com.raf.imperial.jpa.config.PersistenceJpaConfig;

/**
 * Abstract class for all DAO tests.
 *
 * @author RAF
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { PersistenceJpaConfig.class,
    DatasourceConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
public abstract class AbstractDaoTest {

  /**
   * Constructor.
   */
  public AbstractDaoTest() {
    super();
  }

}
