package fr.ancyracademy.esportsclash.team.domain;

import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TeamTests {
    @Nested
    class addMember {
        @Test
        void shouldJoinTest() {
            var team = new Team("123", "Team1");
            team.addMember("Player1", Role.TOP);
            Assert.assertTrue(team.hasMember("Player1", Role.TOP));
        }

        @Test
        void whenPlayerIsAlreadyInTheTeam_shouldThrow() {
            var team = new Team("123", "Team  1");
            team.addMember("Player1", Role.TOP);

            var exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
                team.addMember("Player1", Role.TOP);
            });

            Assert.assertEquals("Player is already in the team", exception.getMessage());
        }

        @Test
        void whenTheRoleIsAlreadyTaken_shouldThrow() {
            var team = new Team("123", "Team  1");
            team.addMember("Player1", Role.TOP);

            var exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
                team.addMember("Player2", Role.TOP);
            });

            Assert.assertEquals("Role is already taken", exception.getMessage());
        }
    }

    @Nested
    class removeMember {
        @Test
        void shouldRemoveTest() {
            var team = new Team("123", "Team 1");
            team.addMember("player1", Role.TOP);
            team.removeMember("player1");
            Assert.assertFalse(team.hasMember("player1", Role.TOP));
        }

        @Test
        void whenPlayerIsNotInTheTeam_shouldNotThrow() {
            var team = new Team("123", "Team 1");
            var exception = Assert.assertThrows(
                    IllegalArgumentException.class, () -> {
                        team.removeMember("player1");
                    });

            Assert.assertEquals("Player not in team", exception.getMessage());

        }
    }
}
