package com.br.gabrielmoreira.soccerapi.exception;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.Locale;

/**
 * Converte exceções de integridade (BD) em mensagens de regra de negócio, sem vazar detalhe técnico.
 */
public final class DataIntegrityExceptionMapper {

    private DataIntegrityExceptionMapper() {
    }

    public static String toUserMessage(DataIntegrityViolationException ex) {
        String raw = ex.getMessage();
        if (raw != null) {
            String l = raw.toLowerCase(Locale.ROOT);
            if (l.contains("unique") || l.contains("duplicate key") || l.contains("uk_")) {
                return "Já existe um jogador com os mesmos dados únicos (por exemplo, nome ou identificação) "
                        + "ou violação de valor único informado no cadastro.";
            }
            if (l.contains("foreign key")
                    || l.contains("violates foreign key")
                    || l.contains("fk_")
                    || l.contains("referential")) {
                return "Não é possível concluir a operação com o jogador: referência a time inexistente "
                        + "ou jogador impedido no banco de dados.";
            }
            if (l.contains("not null") || l.contains("null value in column")) {
                return "Não é possível salvar o jogador: dado obrigatório faltou para o banco de dados.";
            }
        }
        return "Não foi possível concluir a operação com o jogador: integridade de dados rejeitada. "
                + "Verifique valores únicos, obrigatórios e referências informadas.";
    }

}
