package org.happyhour.scoreboard.algo;

import de.gesundkrank.jskills.GameInfo;
import de.gesundkrank.jskills.IPlayer;
import de.gesundkrank.jskills.Rating;
import de.gesundkrank.jskills.Team;
import de.gesundkrank.jskills.trueskill.TwoTeamTrueSkillCalculator;
import org.happyhour.scoreboard.model.Usermatch;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class TrueSkillAlgorithm {
    class User implements IPlayer {
        Integer id;
        Rating rating;

        public User(Integer id, Rating rating) {
            this.id = id;
            this.rating = rating;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;
            User user = (User) o;
            return Objects.equals(id, user.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }


    public Map<Integer, Double> calcRating(Collection<List<Usermatch>> matches) {
        TwoTeamTrueSkillCalculator calculator = new TwoTeamTrueSkillCalculator();
        Map<Integer, User> players = new HashMap<>();
        for (List<Usermatch> match : matches) {
            GameInfo gameInfo = GameInfo.getDefaultGameInfo();
            Team winTeam = new Team();
            Team loseTeam = new Team();
            for (Usermatch info : match) {
                User player = players.computeIfAbsent(info.getUserId(), id -> new User(id, gameInfo.getDefaultRating()));
                if (info.getScore() < 0) {
                    loseTeam.addPlayer(player, player.rating);
                } else if(info.getScore() > 0) {
                    winTeam.addPlayer(player, player.rating);
                }
            }
            Map<IPlayer, Rating> newRating = calculator.calculateNewRatings(gameInfo, Arrays.asList(winTeam, loseTeam), new int[]{1, 2});
            for (Map.Entry<IPlayer, Rating> entry : newRating.entrySet()) {
                ((User) entry.getKey()).rating = entry.getValue();
            }
        }

        Map<Integer, Double> map = new HashMap<>();
        for (User user : players.values()) {
            map.put(user.id, user.rating.getConservativeRating());
        }
        return map;
    }
}
