package com.raf.imperial.rule;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.raf.imperial.database.config.DatasourceConfig;
import com.raf.imperial.rule.config.RuleConfig;

import lombok.NoArgsConstructor;

/**
 * Abstract class for all Rule tests.
 * 
 * @author RAF
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RuleConfig.class,
    DatasourceConfig.class }, loader = AnnotationConfigContextLoader.class)
@NoArgsConstructor
@Transactional
public abstract class AbstractRuleTest {
  // RAS
}
