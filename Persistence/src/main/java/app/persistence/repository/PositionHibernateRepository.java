package app.persistence.repository;

import app.model.Game;
import app.model.Player;
import app.model.Position;
import app.persistence.IConfigurationRepository;
import app.model.Configuration;
import app.persistence.IGameRepository;
import app.persistence.IPositionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class PositionHibernateRepository implements IPositionRepository {

    private static final Logger logger = LogManager.getLogger();

    public PositionHibernateRepository() {
        logger.info("Initializing PositionHibernateRepository");
    }

    @Override
    public Optional<Position> findOne(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Position WHERE id=:idM", Position.class)
                    .setParameter("idM", id)
                    .getSingleResult());
        }
    }

    @Override
    public Iterable<Position> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Position ", Position.class).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Positions", e);
            return null;
        }
    }

    @Override
    public Optional<Position> save(Position entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return Optional.of(entity);
    }

    @Override
    public Optional<Position> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Position> update(Position entity) {
        final AtomicReference<Optional<Position>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Position updatedConfiguration = (Position) session.merge(entity);
            result.set(Optional.ofNullable(updatedConfiguration));
        });

        return result.get();
    }
}

