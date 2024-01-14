package ru.clevertec.house.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.exception.RepositoryException;
import ru.clevertec.house.model.Person;
import ru.clevertec.house.repository.PersonRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    public static final String SELECT_BY_HOUSE_ID = """
                SELECT * FROM people p
                WHERE p.residency_id = :houseId
            """;

    public static final String SELECT_ALL_QUERY = """
                SELECT * FROM people LIMIT :limit OFFSET :offset;
            """;

    public static final String UPDATE_QUERY = """
                UPDATE people SET name = :name, surname = :surname, sex = :sex,
                passport_series = :passportSeries, passport_number = :passportNumber
                WHERE id = :id
            """;

    private final SessionFactory sessionFactory;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean save(Person person) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.persist(person);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RepositoryException("Failed to save person", e);
            }
        }
    }

    @Override
    public Person findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Person.class, id);
        } catch (Exception ex) {
            throw new RepositoryException(String.format("Failed to find person where id = %d!", id), ex);
        }
    }

    @Override
    public List<Person> findByHouseId(Long houseId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Person> query = session.createNativeQuery(SELECT_BY_HOUSE_ID, Person.class)
                    .setParameter("houseId", houseId);
            return query.list();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("Failed to find people where house id = %d!", houseId), ex);
        }
    }

    @Override
    public List<Person> findAll(int limit, int offset) {
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
    public boolean updateById(Long id, Person person) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.createNativeQuery(UPDATE_QUERY, Person.class)
                        .setParameter("name", person.getName())
                        .setParameter("surname", person.getSurname())
                        .setParameter("sex", person.getSex())
                        .setParameter("passportSeries", person.getPassportSeries())
                        .setParameter("passportNumber", person.getPassportNumber())
                        .setParameter("id", id)
                        .executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
                String message = "Failed to update person with id = %d.";
                throw new RepositoryException(String.format(message, id), e);
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Person person = session.getReference(Person.class, id);
                session.remove(person);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
                String message = "Failed to delete person with id = %d. This person is not exist!";
                throw new RepositoryException(String.format(message), e);
            }
        }
    }

    private Person construct(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getLong("id"));
        person.setUuid(resultSet.getObject("uuid", UUID.class));
        person.setName(resultSet.getString("name"));
        person.setSurname(resultSet.getString("surname"));
        person.setSex(resultSet.getString("sex"));
        person.setPassportSeries(resultSet.getString("passport_series"));
        person.setPassportNumber(resultSet.getString("passport_number"));
        person.setCreateDate(resultSet.getObject("create_date", LocalDateTime.class));
        person.setUpdateDate(resultSet.getObject("update_date", LocalDateTime.class));
        return person;
    }
}
