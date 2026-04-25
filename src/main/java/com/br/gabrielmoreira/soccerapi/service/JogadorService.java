package com.br.gabrielmoreira.soccerapi.service;

import com.br.gabrielmoreira.soccerapi.entity.Jogador;
import com.br.gabrielmoreira.soccerapi.repository.JogadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;

    public JogadorService(
            JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    public List<Jogador> findByNome(String nome) {
        return jogadorRepository.findByNome(nome);
    }

    public List<Jogador> findAll() {
        return jogadorRepository.findAll();
    }

    public List<Jogador> findByTime(String time) {
        return jogadorRepository.findByTime(time);
    }


}
