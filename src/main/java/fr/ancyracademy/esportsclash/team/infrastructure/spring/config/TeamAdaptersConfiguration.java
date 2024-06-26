package fr.ancyracademy.esportsclash.team.infrastructure.spring.config;

import fr.ancyracademy.esportsclash.team.application.ports.TeamQuery;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.infrastructure.persistence.jpa.SQLTeamQuery;
import fr.ancyracademy.esportsclash.team.infrastructure.persistence.jpa.SQLTeamRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamAdaptersConfiguration {
    @Bean
    public TeamRepository teamRepository(EntityManager entityManager) {
        return new SQLTeamRepository(entityManager);
    }

    @Bean
    public TeamQuery teamQuery(EntityManager entityManager) {
        return new SQLTeamQuery(entityManager);
    }
}
