package hello;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.common.collect.Lists;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"hello"}, entityManagerFactoryRef="entityManagerFactory", transactionManagerRef = "transactionManager")
public class Application implements CommandLineRunner {

    @Autowired
    CustomerRepository repository;
    
    @Autowired
	ParticipanteDAO participanteDAO;
	
	@Autowired
	PerfilDAO perfilDAO;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
    
    private static final String ENTITY_PACKAGE = "hello";
	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	private static final String SCHEMA_URL = "jdbc:mysql://localhost:3306/pi2project-db";
	private static final String DB_USER = "root";
	
	//Senha pode variar de acordo com o ambiente
//	private static final String DB_PASSWORD = "root";
	private static final String DB_PASSWORD = "1234";

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

    @Override
    public void run(String... strings) throws Exception {
        // save a couple of customers
//        repository.save(new Customer("Jack", "Bauer"));
//        repository.save(new Customer("Chloe", "O'Brian"));
//        repository.save(new Customer("Kim", "Bauer"));
//        repository.save(new Customer("David", "Palmer"));
//        repository.save(new Customer("Michelle", "Dessler"));

        // fetch all customers
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (Customer customer : repository.findAll()) {
//            System.out.println(customer);
//        }
//        System.out.println();

        // fetch an individual customer by ID
//        Customer customer = repository.findOne(1L);
//        System.out.println("Customer found with findOne(1L):");
//        System.out.println("--------------------------------");
//        System.out.println(customer);
//        System.out.println();

        // fetch customers by last name
//        System.out.println("Customer found with findByLastName('Bauer'):");
//        System.out.println("--------------------------------------------");
//        for (Customer bauer : repository.findByLastName("Bauer")) {
//            System.out.println(bauer);
//        }
    	List<Perfil> perfis = Lists.newArrayList(perfilDAO.findAll());
    	if (perfis.isEmpty()) {
			System.out.println("Gerando os perfis");
			perfilDAO.save(new Perfil(PerfilDescricao.ROLE_ADMINISTRADOR));
			perfilDAO.save(new Perfil(PerfilDescricao.ROLE_LEILOEIRO));
			perfilDAO.save(new Perfil(PerfilDescricao.ROLE_COMITENTE));
			perfilDAO.save(new Perfil(PerfilDescricao.ROLE_ARREMATANTE));
			perfis = Lists.newArrayList(perfilDAO.findAll());
		}
		String senha = "admin";
		
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String cripto = encoder.encodePassword(senha, null);
		System.out.println(senha);
		
		participanteDAO.save(new Participante("Administrador Teste", cripto, "54753133184", ParticipanteStatus.ATIVO, perfis));
    }

}
