package com.github.saulmmbp.foroAlura.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.saulmmbp.foroAlura.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
