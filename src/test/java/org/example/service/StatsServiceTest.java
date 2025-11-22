package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("Debe calcular las estadísticas correctamente con datos de mutantes y humanos")
    void getStats_WithMutantAndHumanData_ShouldReturnCorrectStats() {
        // --- Arrange ---
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        // --- Act ---
        StatsResponse stats = statsService.getStats();

        // --- Assert ---
        assertEquals(40, stats.getCount_mutant_dna());
        assertEquals(100, stats.getCount_human_dna());
        assertEquals(0.4, stats.getRatio(), 0.001, "El ratio de 40/100 debería ser 0.4");
    }

    @Test
    @DisplayName("Debe manejar el caso de división por cero cuando no hay humanos")
    void getStats_WithNoHumans_ShouldReturnCorrectRatio() {
        // --- Arrange ---
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        // --- Act ---
        StatsResponse stats = statsService.getStats();

        // --- Assert ---
        assertEquals(10, stats.getCount_mutant_dna());
        assertEquals(0, stats.getCount_human_dna());
        assertEquals(10.0, stats.getRatio(), "El ratio debería ser el número de mutantes si no hay humanos");
    }

    @Test
    @DisplayName("Debe retornar estadísticas en cero cuando no hay datos en la base de datos")
    void getStats_WithNoData_ShouldReturnZeroedStats() {
        // --- Arrange ---
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        // --- Act ---
        StatsResponse stats = statsService.getStats();

        // --- Assert ---
        assertEquals(0, stats.getCount_mutant_dna());
        assertEquals(0, stats.getCount_human_dna());
        assertEquals(0.0, stats.getRatio(), "El ratio debería ser 0.0 si no hay datos");
    }

    @Test
    @DisplayName("Debe calcular correctamente un ratio con decimales")
    void getStats_WithDecimalRatio_ShouldCalculateCorrectly() {
        // --- Arrange ---
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(1L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(3L);

        // --- Act ---
        StatsResponse stats = statsService.getStats();

        // --- Assert ---
        assertEquals(1, stats.getCount_mutant_dna());
        assertEquals(3, stats.getCount_human_dna());
        assertEquals(0.333, stats.getRatio(), 0.001, "El ratio de 1/3 debería ser aprox 0.333");
    }

    @Test
    @DisplayName("Debe retornar un ratio de 1.0 cuando las cantidades son iguales")
    void getStats_WithEqualCounts_ShouldReturnRatioOfOne() {
        // --- Arrange ---
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(50L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(50L);

        // --- Act ---
        StatsResponse stats = statsService.getStats();

        // --- Assert ---
        assertEquals(50, stats.getCount_mutant_dna());
        assertEquals(50, stats.getCount_human_dna());
        assertEquals(1.0, stats.getRatio(), "El ratio debería ser 1.0 para cantidades iguales");
    }
}
