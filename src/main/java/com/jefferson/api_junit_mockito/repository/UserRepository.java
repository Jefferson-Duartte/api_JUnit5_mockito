package com.jefferson.api_junit_mockito.repository;

import com.jefferson.api_junit_mockito.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
