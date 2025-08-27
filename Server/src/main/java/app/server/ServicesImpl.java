package app.server;

import app.model.Configuration;
import app.model.Player;
import app.model.Game;
import app.model.Position;
import app.persistence.IConfigurationRepository;
import app.persistence.IGameRepository;
import app.persistence.IPlayerRepository;
import app.persistence.IPositionRepository;
import app.services.AppException;
import app.services.IObserver;
import app.services.IServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

public class ServicesImpl implements IServices {

    private IPlayerRepository playerRepo;
    private IConfigurationRepository configRepo;
    private IGameRepository gameRepo;
    private IPositionRepository positionRepo;

    private static final Logger logger = LogManager.getLogger(ServicesImpl.class);

    private Map<String, IObserver> loggedClients;

    public ServicesImpl(IPlayerRepository playerRepo, IConfigurationRepository configRepo, IGameRepository gameRepo, IPositionRepository positionRepo) {
        this.playerRepo = playerRepo;
        this.configRepo = configRepo;
        this.gameRepo = gameRepo;
        this.positionRepo = positionRepo;
        logger.info("Initializing ServicesImpl");
        loggedClients = new ConcurrentHashMap<>();
    }

    public synchronized Player login(Player player, IObserver client) throws AppException {
        logger.info("Player initialized: " + player);
        Player foundPlayer = findPlayerByAlias(player.getAlias());
        if (foundPlayer != null) {
            if(loggedClients.get(player.getAlias()) != null)
                throw new AppException("Player already logged in.");
            loggedClients.put(player.getAlias(), client);
        } else
            throw new AppException("Authentication failed.");
        return foundPlayer;
    }

    @Override
    public synchronized Configuration addConfiguration(Configuration configuration) throws AppException {
        logger.info("Adding Configuration: " + configuration + " ...");
        return configRepo.save(configuration).orElseThrow(() -> new AppException("Error adding the configuration."));
    }

    @Override
    public void addPosition(Position configuration) throws AppException {
        logger.info("Adding Position: " + configuration + " ...");
        Position addedGame = positionRepo.save(configuration).orElseThrow(() -> new AppException("Error adding the game."));
    }

    @Override
    public synchronized void addGame(Game game) throws AppException {
        logger.info("Adding Game: " + game + " ...");
        Game addedGame = gameRepo.save(game).orElseThrow(() -> new AppException("Error adding the game."));
        CompletableFuture.runAsync(() -> {
            try {
                for (IObserver client : loggedClients.values()) {
                    try {
                        client.gameAdded(addedGame);
                    } catch (AppException e) {
                        System.err.println("Error notifying client: " + e);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public  synchronized List<Configuration> getAllConfigurations() throws AppException {
        logger.info("Getting all configurations...");
        Iterable<Configuration> configurations = configRepo.findAll();
        return StreamSupport.stream(configurations.spliterator(), false).toList();
    }

    @Override
    public List<Position> getAllPositions() throws AppException {
        logger.info("Getting all positions...");
        Iterable<Position> configurations = positionRepo.findAll();
        return StreamSupport.stream(configurations.spliterator(), false).toList();
    }


    @Override
    public synchronized List<Game> getAllGamesForPlayer(Player player) throws AppException {
        logger.info("Getting all games for player: " + player.getAlias() + " ...");
        Iterable<Game> games = gameRepo.findAllByPlayer(player);
        return StreamSupport.stream(games.spliterator(), false).toList();
    }

    @Override
    public synchronized List<Game> getAllGames() throws AppException {
        logger.info("Getting all games...");
        Iterable<Game> games = gameRepo.findAll();
        return StreamSupport.stream(games.spliterator(), false).toList();
    }

    public synchronized Player findPlayerByAlias(String alias) throws AppException {
        logger.info("Finding player by alias: " + alias + " ...");
        return playerRepo.findOneByAlias(alias).orElseThrow(() -> new AppException("Player not found."));
    }

    public synchronized void logout(Player player, IObserver client) throws AppException {
        logger.info("Logging out player: " + player.getAlias() + " ...");
        IObserver localClient = loggedClients.remove(player.getAlias());
        if (localClient == null)
            throw new AppException("Player " + player.getAlias() + " is not logged in.");
    }
}
