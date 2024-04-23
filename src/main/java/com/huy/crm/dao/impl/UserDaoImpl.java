package com.huy.crm.dao.impl;

import com.huy.crm.constant.SortUserColumn;
import com.huy.crm.dao.UserDAO;
import com.huy.crm.dto.UserParams;
import com.huy.crm.entity.UserEntity;
import com.huy.crm.entity.UserEntity_;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<UserEntity> getAllUsers(UserParams userParams) {
        String search = userParams.getSearch();
        String sort = userParams.getSort();

        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        if (search != null && !search.trim().isEmpty()) {
            Predicate usernamePredicate = builder.like(
                    builder.lower(root.get(UserEntity_.USERNAME)), "%" + search.toLowerCase() + "%"
            );
            Predicate emailPredicate = builder.like(
                    builder.lower(root.get(UserEntity_.EMAIL)), "%" + search.toLowerCase() + "%"
            );

            query.where(builder.or(usernamePredicate, emailPredicate));
        }

        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case SortUserColumn.USERNAME_ASC:
                    query.orderBy(builder.asc(root.get(UserEntity_.USERNAME)));
                    break;
                case SortUserColumn.USERNAME_DESC:
                    query.orderBy(builder.desc(root.get(UserEntity_.USERNAME)));
                    break;
                case SortUserColumn.EMAIL_ASC:
                    query.orderBy(builder.asc(root.get(UserEntity_.EMAIL)));
                    break;
                case SortUserColumn.EMAIL_DESC:
                    query.orderBy(builder.desc(root.get(UserEntity_.EMAIL)));
                    break;
                case SortUserColumn.ENABLED_ASC:
                    query.orderBy(builder.asc(root.get(UserEntity_.ENABLED)));
                    break;
                case SortUserColumn.ENABLED_DESC:
                    query.orderBy(builder.desc(root.get(UserEntity_.ENABLED)));
                    break;
                default:
                    query.orderBy(builder.asc(root.get(UserEntity_.ID)));
                    break;
            }
        }

        int page = userParams.getPage();
        int pageSize = userParams.getPageSize();
        int firstResult = (page - 1) * pageSize;

        return sessionFactory.getCurrentSession()
                .createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();

    }

    @Override
    public int getUsersCount(UserParams userParams) {
        String search = userParams.getSearch();

        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        query.select(builder.count(root));

        if (search != null && !search.trim().isEmpty()) {
            Predicate usernamePredicate = builder.like(
                    builder.lower(root.get(UserEntity_.USERNAME)), "%" + search.toLowerCase() + "%"
            );
            Predicate emailPredicate = builder.like(
                    builder.lower(root.get(UserEntity_.EMAIL)), "%" + search.toLowerCase() + "%"
            );

            query.where(builder.or(usernamePredicate, emailPredicate));
        }

        return Math.toIntExact(sessionFactory.getCurrentSession().createQuery(query).getSingleResult());
    }

    @Override
    public UserEntity getUserById(long id) {
        return sessionFactory.getCurrentSession().get(UserEntity.class, id);
    }

    @Override
    @Transactional
    public UserEntity findByUserName(String userName) {
        return sessionFactory.getCurrentSession()
                .createQuery("from UserEntity where username = :userName", UserEntity.class)
                .setParameter("userName", userName)
                .uniqueResult();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("from UserEntity where email = :email", UserEntity.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public String getHashedPassword(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("select password from UserEntity where id = :id", String.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(userEntity);
    }

    @Override
    public void deleteUser(long id) {
        UserEntity userEntity = getUserById(id);
        sessionFactory.getCurrentSession().delete(userEntity);
    }
}
