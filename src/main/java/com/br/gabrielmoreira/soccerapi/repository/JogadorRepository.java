package com.br.gabrielmoreira.soccerapi.repository;

import com.br.gabrielmoreira.soccerapi.entity.Jogador;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {


    List<Jogador> findByTime(String time);

    List<Jogador> findByNome(@Param("nome") String nome);
}
