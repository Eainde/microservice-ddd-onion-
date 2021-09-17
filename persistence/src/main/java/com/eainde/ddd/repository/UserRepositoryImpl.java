package com.eainde.ddd.repository;

import com.eainde.ddd.Mapper;
import com.eainde.ddd.aggregate.UserAggregate;
import com.eainde.ddd.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final Mapper<UserAggregate, User> mapper;
  @PersistenceContext private final EntityManager entityManager;
  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

  UserRepositoryImpl(final EntityManager entityManager, final Mapper<UserAggregate, User> mapper) {
    this.entityManager = entityManager;
    this.mapper = mapper;
  }

  @Override
  public List<UserAggregate> findAll() {
    final var criteria = entityManager.getCriteriaBuilder().createQuery(User.class);
    criteria.select(criteria.from(User.class));
    return entityManager.createQuery(criteria).getResultList().stream()
        .map(mapper::mapToDomain)
        .collect(Collectors.toList());
  }

  @Override
  public UserAggregate getUserById(long id) {
    User user =
        entityManager
            .createQuery("SELECT u FROM USER u WHERE u.userId=:id", User.class)
            .setParameter("id", id)
            .getSingleResult();
    return mapper.mapToDomain(user);
  }

  @Override
  public boolean add(UserAggregate user) {
    try {
      entityManager.persist(mapper.mapToEntity(user));
      return true;
    } catch (Exception e) {
      LOGGER.error("Error ", e);
      return false;
    }
  }

  @Override
  public UserAggregate update(UserAggregate user) {
    return mapper.mapToDomain(entityManager.merge(mapper.mapToEntity(user)));
  }
}