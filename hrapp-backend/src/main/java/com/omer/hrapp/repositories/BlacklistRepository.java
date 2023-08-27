package com.omer.hrapp.repositories;

import com.omer.hrapp.entities.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    List<Blacklist> findAllBySpecialistId(Optional<Long> specialistId);
}
