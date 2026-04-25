package com.br.gabrielmoreira.soccerapi.service;

import com.br.gabrielmoreira.soccerapi.entity.Jogador;
import com.br.gabrielmoreira.soccerapi.repository.JogadorRepository;
import com.br.gabrielmoreira.soccerapi.exception.ResourceNotFoundException;
import com.br.gabrielmoreira.soccerapi.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Jogador create(Jogador input) {
        validateJogadorForCreate(input);

        Jogador jogador = new Jogador();
        jogador.setNome(input.getNome());
        jogador.setPosicao(input.getPosicao());
        jogador.setIdade(input.getIdade());
        jogador.setTime(input.getTime());

        return jogadorRepository.save(jogador);
    }

    @Transactional
    public Jogador update(Long id, Jogador jogadorUpdate) {
        Jogador jogador = jogadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado com o id informado"));

        if (jogadorUpdate.getNome() != null) {
            jogador.setNome(jogadorUpdate.getNome());
        }
        if (jogadorUpdate.getPosicao() != null) {
            jogador.setPosicao(jogadorUpdate.getPosicao());
        }
        if (jogadorUpdate.getIdade() != 0) {
            jogador.setIdade(jogadorUpdate.getIdade());
        }
        if (jogadorUpdate.getTime() != null) {
            jogador.setTime(jogadorUpdate.getTime());
        }

        return jogadorRepository.save(jogador);
    }

    @Transactional
    public void delete(Long id) {
        Jogador jogador = jogadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado com o id informado"));
        jogadorRepository.delete(jogador);
    }

    private void validateJogadorForCreate(Jogador input) {
        if (isBlank(input.getNome())) {
            throw new BusinessException("O campo nome é obrigatório");
        }
        if (isBlank(input.getPosicao())) {
            throw new BusinessException("O campo posição é obrigatório");
        }
        if (input.getIdade() == 0) {
            throw new BusinessException("O campo idade é obrigatório");
        }
        if (isBlank(input.getTime())) {
            throw new BusinessException("O campo time é obrigatório");
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
