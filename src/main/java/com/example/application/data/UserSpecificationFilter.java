package com.example.application.data;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

public class UserSpecificationFilter implements Specification<User> {
    private final String name;

    private final String username;

    public UserSpecificationFilter(String name, String username) {
        this.name = name;
        this.username = username;
    }

    @Override
    public @Nullable Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        Predicate userNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("username")),
                "%" + username.toLowerCase() + "%");
        Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                "%" + name.toLowerCase() + "%");
        ;
        return criteriaBuilder.or(userNamePredicate, namePredicate);

        // TODO Auto-generated method stub
    }
}
