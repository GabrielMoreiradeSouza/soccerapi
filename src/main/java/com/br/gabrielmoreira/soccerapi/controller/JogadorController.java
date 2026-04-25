package com.br.gabrielmoreira.soccerapi.controller;

//import io.swagger.v3.oas.annotations.tags.Tag;
import com.br.gabrielmoreira.soccerapi.entity.Jogador;
import com.br.gabrielmoreira.soccerapi.service.JogadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogadores")
//@Tag(name = "Jogadores", description = "Endpoints para gerenciamento de jogadores")

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

    @PostMapping
    @Operation(summary = "Registrar um novo jogador", description = "Endpoint para Registrar um novo jogador no sistema")
    @ApiResponse(responseCode = "200", description = "Jogador Registrado com sucesso")
    public ResponseEntity<Jogador> create(@RequestBody  Jogador jogador) {
        return ResponseEntity.ok(JogadorService.create(jogador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogador> update(@PathVariable Long id,@RequestBody Jogador jogador) {
        return ResponseEntity.ok(JogadorService.update(id, jogador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        JogadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
