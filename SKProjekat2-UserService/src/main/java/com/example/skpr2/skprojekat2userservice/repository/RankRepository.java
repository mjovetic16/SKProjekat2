package com.example.skpr2.skprojekat2userservice.repository;

import com.example.skpr2.skprojekat2userservice.domain.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Rank,Long> {
}
