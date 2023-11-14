package com.haizer.projetospringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haizer.projetospringboot.modelo.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {
	Papel findByPapel(String papel);
}
