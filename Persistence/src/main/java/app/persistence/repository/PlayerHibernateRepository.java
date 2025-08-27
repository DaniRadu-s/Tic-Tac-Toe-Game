package app.persistence.repository;

import app.model.Player;
import app.persistence.IPlayerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerHibernateRepository implements IPlayerRepository {

    private final static Logger logger = LogManager.getLogger();
    public PlayerHibernateRepository() {
        logger.info("Initializing PlayerHibernateRepository");
    }

    @Override
    public Optional<Player> findOne(Long id) {
        final AtomicReference<Optional<Player>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Player configuration = session.get(Player.class, id);
            result.set(Optional.ofNullable(configuration));
        });

        return result.get();
    }

    @Override
    public Iterable<Player> findAll() {
        return null;
    }

    @Override
    public Optional<Player> save(Player player) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(player));
        return Optional.of(player);
    }

    @Override
    public Optional<Player> delete(Long id) {
        final AtomicReference<Optional<Player>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Player player = session.get(Player.class, id);
            if (player != null) {
                session.delete(player);
                result.set(Optional.of(player));
            } else {
                result.set(Optional.empty());
            }
        });

        return result.get();
    }

    @Override
    public Optional<Player> update(Player entity) {
        final AtomicReference<Optional<Player>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Player updatedConfiguration = (Player) session.merge(entity);
            result.set(Optional.ofNullable(updatedConfiguration));
        });

        return result.get();
    }

    @Override
    public Optional<Player> findOneByAlias(String alias) {
        logger.traceEntry();

        final AtomicReference<Optional<Player>> result = new AtomicReference<>(Optional.empty());

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            try {
                Player player = session
                        .createQuery("from Player where alias = :alias", Player.class)
                        .setParameter("alias", alias)
                        .setMaxResults(1)
                        .uniqueResult();

                result.set(Optional.ofNullable(player));
            } catch (Exception e) {
                logger.error("Error finding player by alias: ", e);
            }
        });

        return result.get();
    }

}
