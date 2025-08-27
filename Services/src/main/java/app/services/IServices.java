package app.services;

// TODO 1: ADD ALL MODELS

import app.model.Configuration;
import app.model.Game;
import app.model.Player;
import app.model.Position;

import java.util.List;

public interface IServices {
    Player login(Player player, IObserver client) throws AppException;
    Configuration addConfiguration(Configuration configuration) throws AppException;
    void addPosition(Position configuration) throws AppException;
    void addGame(Game game) throws AppException;
    List<Configuration> getAllConfigurations() throws AppException;
    List<Position> getAllPositions() throws AppException;
    List<Game> getAllGamesForPlayer(Player player) throws AppException;
    List<Game> getAllGames() throws AppException;
    Player findPlayerByAlias(String alias) throws AppException;
    void logout(Player player, IObserver client) throws AppException;
}
