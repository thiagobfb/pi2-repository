package br.upis.sel.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import br.upis.sel.util.ViewScope;

/**
 * 
 * @author THIAGO
 *
 */
@Configuration
@ComponentScan(basePackages = {"br.upis.sel.model.*", "br.upis.sel.controller.*", "br.upis.sel.view.mb"})
@EnableJpaRepositories(basePackages = {"br.upis.sel.model.dao"}, entityManagerFactoryRef="entityManagerFactory", transactionManagerRef = "transactionManager")
@Import(value = { SELMvcConfig.class, SELSecurityConfig.class})
public class SELConfig {
	
	private static final String ENTITY_PACKAGE = "br.upis.sel.model.entity";
	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	private static final String SCHEMA_URL = "jdbc:mysql://localhost:3306/pi2project-db";
	private static final String DB_USER = "root";
	
	//Senha pode variar de acordo com o ambiente
	private static final String DB_PASSWORD = "root";
//	private static final String DB_PASSWORD = "1234";

	//Spring JPA
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(this.getDataSource());
		em.setPackagesToScan(new String[] { ENTITY_PACKAGE });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(this.getAdditionalProperties());

		return em;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DRIVER_CLASS_NAME);
		dataSource.setUrl(SCHEMA_URL);
		dataSource.setUsername(DB_USER);
		dataSource.setPassword(DB_PASSWORD);
		return dataSource;
	}

	@DependsOn("entityManagerFactory")
	@Autowired
	@Bean(name = "transactionManager")
	public PlatformTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean(name = "exceptionTranslation")
	public PersistenceExceptionTranslationPostProcessor getExceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties getAdditionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect",	"org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		return properties;
	}
	
	
	//Customizando a View Scope para Spring
	@Bean
	public CustomScopeConfigurer getCustomScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		Map<String, Object> customScopes = new HashMap<String, Object>();
		customScopes.put("view", new ViewScope());
		configurer.setScopes(customScopes);
		
		return configurer;
	}
}
