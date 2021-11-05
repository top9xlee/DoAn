package com.blog.DemoDoan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.DemoDoan.model.Sub;


@Repository
public interface SubRepository extends JpaRepository<Sub, Long> {

    Optional<Sub> findByName(String subName);
}