package edu.hust.webswmm.framework.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"edu.hust.webswmm.mapper.mdb"}, sqlSessionFactoryRef = "sqlSessionFactory_mdb",
        sqlSessionTemplateRef = "sqlSessionTemplate_mdb")
public class MdbDataSourceConfig implements EnvironmentAware {
    private Environment env;

    @Override
    public void setEnvironment(Environment env)
    {
        this.env = env;
    }

    @Bean(name = "ds_mdb")
    @ConfigurationProperties("spring.datasource.dsmdb")
    public DataSource dataSource1()
    {
        return DruidDataSourceBuilder.create()
                .build();
    }

    /**
     * 配置事物管理器
     *
     * @return
     */
    @Bean(name = "dsmdbTransactionManager")
    public DataSourceTransactionManager ds1TransactionManager(
            @Qualifier("ds_mdb") DataSource dataSource
    )
    {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean(name = "sqlSessionFactory_mdb")
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("ds_mdb") DataSource dataSource) throws Exception
    {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/mapper/mdb/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
        //分页插件
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        Interceptor interceptor = new PageInterceptor();
        interceptor.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{interceptor});
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate_mdb")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory_mdb") SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
