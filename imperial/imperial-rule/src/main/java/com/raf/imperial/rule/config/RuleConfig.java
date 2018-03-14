package com.raf.imperial.rule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.raf.fwk.util.config.UtilConfig;
import com.raf.imperial.jpa.config.PersistenceJpaConfig;

import lombok.NoArgsConstructor;

/**
 * Spring configuration class for rules.
 * 
 * @author RAF
 */
@Configuration
@Import(value = { UtilConfig.class, PersistenceJpaConfig.class })
@ComponentScan("com.raf.imperial.rule")
@NoArgsConstructor
public class RuleConfig {
  // RAS
}
