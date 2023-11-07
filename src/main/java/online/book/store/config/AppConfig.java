package online.book.store.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "online.book.store")
@SpringBootApplication(scanBasePackages={"online.book.store","online.book.store.mapper"})
public class AppConfig {
    private Environment environment;

    @Autowired
    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource getDatasource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment
                .getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(getDatasource());
        Properties properties = new Properties();
        properties.put("show_sql", environment.getProperty("spring.jpa.show-sql"));
        properties.put("hibernate.hbm2ddl.auto", environment
                .getProperty("spring.jpa.hibernate.ddl-auto"));
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.setPackagesToScan("online.book.store");
        return localSessionFactoryBean;
    }

}