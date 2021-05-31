/*
package com.example.test.configuration.hikari;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Slf4j
@Configuration
@MapperScan(basePackages = "com.example.test.mapper.jie", sqlSessionFactoryRef = "PrimarySqlSessionFactory")
public class JieDataSourceConfig {

    @Primary
    @Bean(name = "PrimaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.jie")
    public DataSource getPrimaryDateSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "PrimarySqlSessionFactory")
    public SqlSessionFactory PrimarySqlSessionFactory(@Qualifier("PrimaryDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/jie/*.xml"));
        bean.setTypeAliasesPackage("com.example.test.mapper.jie");
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Primary
    @Bean("PrimarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("PrimarySqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
*/
