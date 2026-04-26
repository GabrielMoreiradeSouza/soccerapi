package com.br.gabrielmoreira.soccerapi.controller;

import com.br.gabrielmoreira.soccerapi.entity.Jogador;
import com.br.gabrielmoreira.soccerapi.service.JogadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogadores")
@Tag(name = "Jogadores", description = "Endpoints para gerenciamento de jogadores")

public class JogadorController {
    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> findAll(@RequestParam(required = false) String nome){
        if (nome != null && !nome.isBlank()) {
            return ResponseEntity.ok(jogadorService.findByNome(nome));
        }
        return ResponseEntity.ok(jogadorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogador> findById(@PathVariable Long id) {
        return ResponseEntity.ok(jogadorService.findById(id));
    }

    @GetMapping("/time/{time}")
    public ResponseEntity<List<Jogador>> findByTime(@PathVariable String time) {
        return ResponseEntity.ok(jogadorService.findByTime(time));
    }

    @PostMapping
    @Operation(summary = "Registrar um novo jogador", description = "Endpoint para Registrar um novo jogador no sistema")
    @ApiResponse(responseCode = "200", description = "Jogador Registrado com sucesso")
    public ResponseEntity<Jogador> create(@RequestBody  Jogador jogador) {
        return ResponseEntity.ok(jogadorService.create(jogador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogador> update(@PathVariable Long id,@RequestBody Jogador jogador) {
        return ResponseEntity.ok(jogadorService.update(id, jogador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jogadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
