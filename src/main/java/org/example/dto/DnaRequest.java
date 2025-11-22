package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.validation.ValidDnaSequence;

@Data
@Schema(description = "Request para verificar si un ADN es mutante")
public class DnaRequest {

    @Schema(description = "Secuencia de ADN como un array de Strings. Debe ser una matriz NxN.",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]") // <-- Y ESTO
    @ValidDnaSequence
    private String[] dna;
}
