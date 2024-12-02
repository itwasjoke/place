package com.itwasjoke.place.repository;

import com.itwasjoke.place.entity.Couple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, String> {
    Optional<Couple> findCoupleByHash(String hash);
}
