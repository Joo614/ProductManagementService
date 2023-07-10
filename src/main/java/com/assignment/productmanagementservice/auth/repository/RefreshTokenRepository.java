package com.assignment.productmanagementservice.auth.repository;

import com.assignment.productmanagementservice.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

}
