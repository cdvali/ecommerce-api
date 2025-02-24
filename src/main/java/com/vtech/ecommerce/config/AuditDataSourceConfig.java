package com.vtech.ecommerce.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.vtech.ecommerce.audit.repository",
		entityManagerFactoryRef = "auditEntityManagerFactory",
	    transactionManagerRef = "auditTransactionManager"
)
public class AuditDataSourceConfig {

	@Bean(name = "auditDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.audit")
	public HikariDataSource dataSource() {
		return DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.build();
	}
	
	@Bean(name = "auditEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder builder,
			@Qualifier("auditDataSource") DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.packages("com.vtech.ecommerce.audit.model")
				.persistenceUnit("audit")
				.build();
	}
	
	@Bean(name = "auditTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("auditEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
