package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta con las estadísticas de los ADN verificados")
public class StatsResponse {
    @Schema(description = "Número total de ADN mutantes verificados.")
    private long count_mutant_dna;

    @Schema(description = "Número total de ADN humanos verificados.")
    private long count_human_dna;

    @Schema(description = "Ratio de mutantes sobre humanos (mutants/humans).")
    private double ratio;
}
