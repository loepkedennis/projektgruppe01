 package org.projektmanagement.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
public class AppConfig {
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean getEnitityMangerFactory(){
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(getDataSource());
		emf.setPackagesToScan("org.projektmanagement.model");
		
		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		emf.setJpaVendorAdapter(adapter);
		emf.setJpaProperties(getProperties());
		
		return emf;
	}
	
	@Bean
	public DataSource getDataSource(){
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:./target/testdb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		
		return dataSource;
	}
	
	private Properties getProperties(){
		Properties props = new Properties();
		props.setProperty("hibernate.hbm2ddl.auto", "create");
		//props.setProperty("hibernate.hbm2ddl.auto", "validate");
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		//props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.setProperty("hibernate.show_sql", "true");
		
		return props;
	}
	
	@Bean
	public PlatformTransactionManager getTransactionManager(EntityManagerFactory emf){
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(emf);
		
		return manager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor getPostProcessor(){
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
