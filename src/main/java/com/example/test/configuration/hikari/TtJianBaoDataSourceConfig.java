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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Slf4j
@Configuration
@MapperScan(basePackages = "com.example.test.mapper.ttjb", sqlSessionFactoryRef = "ttjbSqlSessionFactory")
public class TtJianBaoDataSourceConfig {

    @Bean(name = "ttjbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ttjb")
    public DataSource getSecondaryDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "ttjbSqlSessionFactory")
    public SqlSessionFactory ttjbSqlSessionFactory(@Qualifier("ttjbDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/ttjb/*.xml"));
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Bean("ttjbSqlSessionTemplate")
    public SqlSessionTemplate ttjbSqlSessionTemplate(@Qualifier("ttjbSqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
*/
