package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutant")
@RequiredArgsConstructor
@Validated
@Tag(name = "Mutant API", description = "Endpoints para la detección de mutantes y estadísticas")
public class MutantController {
    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping
    @Operation(summary = "Verifica si un ADN pertenece a un mutante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El ADN es de un mutante"),
            @ApiResponse(responseCode = "403", description = "El ADN es de un humano"),
            @ApiResponse(responseCode = "400", description = "El ADN proporcionado es inválido")
    })
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.isMutant(dnaRequest.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return  ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtiene las estadísticas de verificaciones de ADN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    })
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}
