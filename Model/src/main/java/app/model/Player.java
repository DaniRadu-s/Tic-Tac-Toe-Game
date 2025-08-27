package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "Players")
public class Player extends Entity<Long>{
    private String alias;

    public Player() {}
    public Player(String alias) {
        this.alias = alias;
    }

    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Player{" +
                "alias='" + alias + '\'' +
                '}';
    }
}
