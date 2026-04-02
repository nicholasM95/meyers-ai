package be.nicholasmeyers.meyersai.chat.adapter.repository;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = { "be.nicholasmeyers.meyersai.chat.adapter.repository"})
@EnableJpaRepositories(basePackages = { "be.nicholasmeyers.meyersai.chat.adapter.repository"})
@EntityScan(basePackages = { "be.nicholasmeyers.meyersai.chat.adapter.repository"})
public class JpaTestConfig {
}