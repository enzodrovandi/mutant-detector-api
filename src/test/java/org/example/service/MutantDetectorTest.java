package org.example.service;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MutantDetectorTest {
    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    @Test
    @Order(1)
    @DisplayName("Debe retornar true para un ADN mutante con secuencias horizontales y diagonales")
    void testIsMutant_HorizontalAndDiagonalSequences_ReturnsTrue() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA", // Horizontal: CCCC
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @Order(2)
    @DisplayName("Debe retornar true para un ADN mutante con secuencias verticales")
    void testIsMutant_VerticalSequences_ReturnsTrue() {
        String[] dna = {
                "ATGCGA",
                "AAGTGC",
                "ATATGT",
                "AAGTGG",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @Order(3)
    @DisplayName("Debe retornar true para un ADN mutante con múltiples secuencias horizontales")
    void testIsMutant_MultipleHorizontalSequences_ReturnsTrue() {
        String[] dna = {
                "AAAAAA", // Horizontal: AAAA
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA", // Horizontal: CCCC
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @Order(4)
    @DisplayName("Debe retornar true para un ADN mutante con diagonales ascendentes y descendentes")
    void testIsMutant_BothDiagonalSequences_ReturnsTrue() {
        String[] dna = {
                "ATGCGA",
                "CAGGTC",
                "TTACGT",
                "AGCCGG", // Diagonal descendente: GGGG
                "GCGTCA", // Diagonal ascendente: CCCC
                "CCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @Order(5)
    @DisplayName("Debe retornar false para un ADN no mutante con solo una secuencia")
    void testNotMutantWithOnlyOneSequence() {
        String[] dna = {
                "ATGCGA",
                "CCGTGC",
                "TTGTGT",
                "AGAGTG",
                "GACCCC", // Unica Horizontal: CCCC
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @Order(6)
    @DisplayName("Debe retornar false para un ADN no mutante sin secuencias")
    void testNotMutantWithNoSequences(){
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTGTGT",
                "AGAAGG",
                "CCGCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @Order(7)
    @DisplayName("Debe retornar false para un ADN null")
    void testNullDna(){
        String[] dna = null;
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @Order(8)
    @DisplayName("Debe retornar false para un ADN vacío")
    void testEmptyDna(){
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @Order(9)
    @DisplayName("Debe retornar false para un ADN de matriz no cuadrada")
    void testNonSquareMatrix(){
        String[] dna = {
                "ATGCGA", //Matriz 4x6
                "CAGTGC",
                "TTGTGT",
                "AGAAGG",
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @Order(10)
    @DisplayName("Debe retornar false para un ADN con caracteres inválidos")
    void testInvalidCharacters() {
        String[] dna = {
                "ATGCGA",
                "CAGTXC", // 'X' es un carácter inválido
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @Order(11)
    @DisplayName("Debe retornar false para un ADN con una fila nula")
    void testNullRowInDna() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                null,
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @Order(12)
    @DisplayName("Debe retornar false para una matriz más pequeña que 4x4")
    void testMatrixTooSmall() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @Order(13)
    @DisplayName("Debe retornar true para una matriz 4x4 mutante")
    void testMutantInMinimumSizeMatrix() {
        String[] dna = {
                "AAAA",
                "CTGC",
                "GTTG",
                "CCCC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @Order(14)
    @DisplayName("Debe retornar true para una matriz completamente llena del mismo caracter")
    void testAllSameCharacterMatrix() {
        String[] dna = {
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @Order(15)
    @DisplayName("Debe contar secuencias superpuestas en una cadena larga")
    void testSequenceLongerThanFour() {
        String[] dna = {
                "AAAAA", // Contiene 2 secuencias horizontales: AAAA (índice 0) y AAAA (índice 1)
                "CAGTG",
                "TTGTG",
                "AGAGC",
                "CCTAC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @Order(16)
    @DisplayName("Debe detectar mutante en una matriz grande 10x10")
    void testLargeMatrix10x10() {
        String[] dna = {
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA",
                "CCCCTACCCC",  // 2 horizontales: CCCC (pos 0-3 y 6-9)
                "TCACTGTCAC",
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA"
        };
        assertTrue(detector.isMutant(dna));
    }
}
