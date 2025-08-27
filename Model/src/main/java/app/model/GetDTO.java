package app.model;

import java.util.List;

public class GetDTO {
    private int score;
    private List<Position> positions;
    private List<Integer> posBarcaX;
    private List<Integer> posBarcaY;

    public GetDTO(int score, List<Position> positions, List<Integer> posBarcaX, List<Integer> posBarcaY) {
        this.score = score;
        this.positions = positions;
        this.posBarcaX = posBarcaX;
        this.posBarcaY = posBarcaY;
    }

    public int getScore() {
        return score;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public List<Integer> getPosBarcaX() {
        return posBarcaX;
    }

    public List<Integer> getPosBarcaY() {
        return posBarcaY;
    }
}
