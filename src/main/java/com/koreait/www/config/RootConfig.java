package com.koreait.www.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@MapperScan(basePackages = {"com.koreait.www.repository"})
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@Configuration
public class RootConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Value("${db.driver}")
    private String driver;

    @Value("${db.url}")
    private String jdbcUrl;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    // Value 어노테이션을 사용할 수 있게함
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        // hikari 추가 설정
        hikariConfig.setMaximumPoolSize(5); // 최대 커넥션 수
        hikariConfig.setMinimumIdle(5); // 최소 유휴 커넥션 수
        hikariConfig.setConnectionTestQuery("SELECT now()"); // 커넥션 테스트 쿼리
        hikariConfig.setPoolName("MyHikariCP"); // 커넥션 풀 이름

        // MySQL 최적화 설정
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setMapperLocations(
                applicationContext.getResources("classpath:mappers/**/*.xml"));

        // mybatis-config.xml 추가 옵션 설정
        sqlSessionFactoryBean.setConfigLocation(
                applicationContext.getResource("classpath:mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}