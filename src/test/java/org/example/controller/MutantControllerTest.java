package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Test
    @DisplayName("POST /mutant debe retornar 200 OK para un ADN mutante")
    void checkMutant_WhenDnaIsMutant_ShouldReturnOk() throws Exception {
        // --- Arrange ---
        String[] mutantDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRequest request = new DnaRequest();
        request.setDna(mutantDna);

        when(mutantService.isMutant(any(String[].class))).thenReturn(true);

        // --- Act & Assert ---
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 403 Forbidden para un ADN humano")
    void checkMutant_WhenDnaIsHuman_ShouldReturnForbidden() throws Exception {
        // --- Arrange ---
        String[] humanDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        DnaRequest request = new DnaRequest();
        request.setDna(humanDna);

        when(mutantService.isMutant(any(String[].class))).thenReturn(false);

        // --- Act & Assert ---
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 Bad Request para un body vacío")
    void checkMutant_WhenBodyIsEmpty_ShouldReturnBadRequest() throws Exception {
        // --- Act & Assert ---
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("GET /stats debe retornar 200 OK con las estadísticas correctas")
    void getStats_ShouldReturnStatsAndOk() throws Exception {
        // --- Arrange ---
        StatsResponse statsResponse = new StatsResponse(40L, 100L, 0.4);
        when(statsService.getStats()).thenReturn(statsResponse);

        // --- Act & Assert ---
        mockMvc.perform(get("/mutant/stats") // Ruta completa es /mutant/stats
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    @Test
    @DisplayName("GET /stats debe retornar 200 OK incluso cuando no hay datos")
    void getStats_WhenNoData_ShouldReturnZeroedStatsAndOk() throws Exception {
        // --- Arrange ---
        StatsResponse statsResponse = new StatsResponse(0L, 0L, 0.0);
        when(statsService.getStats()).thenReturn(statsResponse);

        // --- Act & Assert ---
        mockMvc.perform(get("/mutant/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));
    }
}
