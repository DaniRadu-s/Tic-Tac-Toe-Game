package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@jakarta.persistence.Entity
@Table(name = "Games")
public class Game extends Entity<Long>{
    private Player player;
    private Integer score;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Game(){}
    public Game(Player player, Integer score, LocalDateTime startTime, LocalDateTime endTime) {
        this.player = player;
        this.score = score;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @ManyToOne
    @JoinColumn(name = "player_id")
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Column(name = "start_date")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_date")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
