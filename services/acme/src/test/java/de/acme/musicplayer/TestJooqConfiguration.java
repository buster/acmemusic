package de.acme.musicplayer;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TestJooqConfiguration {

    @Bean
    public DSLContext dslContext(DataSource dataSource) {
        return new DefaultDSLContext(dataSource, SQLDialect.POSTGRES);
    }
}