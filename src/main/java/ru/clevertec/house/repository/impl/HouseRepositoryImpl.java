package ru.clevertec.house.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.exception.RepositoryException;
import ru.clevertec.house.model.House;
import ru.clevertec.house.repository.HouseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HouseRepositoryImpl implements HouseRepository {

    public static final String SELECT_BY_OWNER_ID = """
                SELECT * FROM houses h
                JOIN houses_owners ho ON h.id = ho.house_id
                WHERE ho.owner_id = :ownerId
            """;

    public static final String SELECT_ALL_QUERY = """
                 SELECT * FROM houses LIMIT :limit OFFSET :offset;
            """;

    public static final String UPDATE_QUERY = """
                UPDATE houses SET 
                area = :area, 
                country = :country, 
                city = :city, 
                street = :street, 
                number = :number, 
                update_date = NOW()
                WHERE id = :id
            """;

    private final SessionFactory sessionFactory;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public boolean save(House house) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.persist(house);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Failed to save house: this house already exist", e);
            }
        }
    }

    @Override
    public House findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(House.class, id);
        } catch (Exception ex) {
            throw new RepositoryException(String.format("Failed to find house where id = %d!", id), ex);
        }
    }

    @Override
    public List<House> findByOwnerId(Long ownerId) {
        try (Session session = sessionFactory.openSession()) {
            Query<House> query = session.createNativeQuery(SELECT_BY_OWNER_ID, House.class)
                    .setParameter("ownerId", ownerId);
            return query.getResultList();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("Failed to find houses where owner id = %d!", ownerId), ex);
        }
    }

    @Override
    public List<House> findAll(int limit, int offset) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("limit", limit);
            params.addValue("offset", offset);

            return namedParameterJdbcTemplate.query(SELECT_ALL_QUERY, params,
                    (resultSet, rowNum) -> construct(resultSet));
        } catch (Exception ex) {
            throw new RepositoryException("The entities were not found[" + ex.getMessage() + "]");
        }
    }

    @Override
    public boolean updateById(Long id, House house) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.createNativeQuery(UPDATE_QUERY, House.class)
                        .setParameter("area", house.getArea())
                        .setParameter("country", house.getCountry())
                        .setParameter("city", house.getCity())
                        .setParameter("street", house.getStreet())
                        .setParameter("number", house.getNumber())
                        .setParameter("id", id)
                        .executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
                String message = "Failed to update house with id = %d. This house is not exist!";
                throw new RepositoryException(String.format(message, id), e);
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                House house = session.getReference(House.class, id);
                session.remove(house);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
                String message = "Failed to delete house  with id = %d. This house is not exist!";
                throw new RepositoryException(String.format(message), e);
            }
        }
    }

    private House construct(ResultSet resultSet) throws SQLException {
        House house = new House();
        house.setId(resultSet.getLong("id"));
        house.setUuid(resultSet.getObject("uuid", UUID.class));
        house.setArea(resultSet.getString("area"));
        house.setCountry(resultSet.getString("country"));
        house.setCity(resultSet.getString("city"));
        house.setStreet(resultSet.getString("street"));
        house.setNumber(resultSet.getString("number"));
        house.setCreateDate(resultSet.getObject("create_date", LocalDateTime.class));
        house.setUpdateDate(resultSet.getObject("update_date", LocalDateTime.class));
        return house;
    }

}
