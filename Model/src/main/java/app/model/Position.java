package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "Positions")
public class Position extends Entity<Long>{
    private Game game;
    private String pos1;
    private String pos2;
    private String pos3;
    private String pos4;
    private String pos5;
    private String pos6;
    private String pos7;
    private String pos8;
    private String pos9;

    public Position() {}
    public Position(Game game,String pos1, String pos2, String pos3, String pos4, String pos5, String pos6, String pos7, String pos8, String pos9) {
        this.game = game;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pos3 = pos3;
        this.pos4 = pos4;
        this.pos5 = pos5;
        this.pos6 = pos6;
        this.pos7 = pos7;
        this.pos8 = pos8;
        this.pos9 = pos9;
    }

    @ManyToOne
    @JoinColumn(name = "game_id")
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Column(name = "pos1")
    public String getPos1() {
        return pos1;
    }

    public void setPos1(String pos1) {
        this.pos1 = pos1;
    }

    @Column(name = "pos2")
    public String getPos2() {
        return pos2;
    }

    public void setPos2(String pos2) {
        this.pos2 = pos2;
    }

    @Column(name = "pos3")
    public String getPos3() {
        return pos3;
    }

    public void setPos3(String pos3) {
        this.pos3 = pos3;
    }

    @Column(name = "pos4")
    public String getPos4() {
        return pos4;
    }

    public void setPos4(String pos4) {
        this.pos4 = pos4;
    }

    @Column(name = "pos5")
    public String getPos5() {
        return pos5;
    }

    public void setPos5(String pos5) {
        this.pos5 = pos5;
    }

    @Column(name = "pos6")
    public String getPos6() {
        return pos6;
    }

    public void setPos6(String pos6) {
        this.pos6 = pos6;
    }

    @Column(name = "pos7")
    public String getPos7() {
        return pos7;
    }

    public void setPos7(String pos7) {
        this.pos7 = pos7;
    }

    @Column(name = "pos8")
    public String getPos8() {
        return pos8;
    }

    public void setPos8(String pos8) {
        this.pos8 = pos8;
    }

    @Column(name = "pos9")
    public String getPos9() {
        return pos9;
    }

    public void setPos9(String pos9) {
        this.pos9 = pos9;
    }

    @Override
    public String toString() {
        return "Position{id=" + id +
                ", pos1=" + pos1 +
                ", pos2=" + pos2 +
                ", pos3=" + pos3 +
                ", pos4=" + pos4 +
                ", pos5=" + pos5 +
                ", pos6=" + pos6 +
                ", pos7=" + pos7 +
                ", pos8=" + pos8 +
                ", pos9=" + pos9 +
                ", gameId=" + (game != null ? game.getId() : "null") +
                "}";
    }

}
