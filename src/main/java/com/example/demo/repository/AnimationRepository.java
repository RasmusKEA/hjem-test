package com.example.demo.repository;

import com.example.demo.model.Animation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimationRepository extends JpaRepository<Animation, Long> {
}
