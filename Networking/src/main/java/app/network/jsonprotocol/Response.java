package app.network.jsonprotocol;

// TODO 1: IMPORT MODELS FOR RESPONSE CLASS

import app.model.Configuration;
import app.model.Game;
import app.model.Player;
import app.model.Position;

import java.util.List;

public class Response {

    private ResponseType type;
    private String errorMessage;
    private Player player;
    private Game game;
    private Configuration configuration;
    private List<Game> games;
    private List<Configuration> configurations;
    private List<Position> positions;
    public Response(){}

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> pos) {
        this.positions = pos;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", errorMessage='" + errorMessage + '\'' +
                ", player=" + player +
                ", game=" + game +
                ", configuration=" + configuration +
                ", games=" + games +
                ", configurations=" + configurations +
                '}';
    }
}
