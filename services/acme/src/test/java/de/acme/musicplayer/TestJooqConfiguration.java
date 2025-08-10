package de.acme.musicplayer;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class TestJooqConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(AbstractIntegrationTest.postgres.getJdbcUrl());
        dataSource.setUsername(AbstractIntegrationTest.postgres.getUsername());
        dataSource.setPassword(AbstractIntegrationTest.postgres.getPassword());
        return dataSource;
    }

    @Bean
    public DSLContext dslContext(DataSource dataSource) {
        return new DefaultDSLContext(dataSource, SQLDialect.POSTGRES);
    }
}