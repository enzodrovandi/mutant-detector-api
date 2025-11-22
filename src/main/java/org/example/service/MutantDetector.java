package org.example.service;

import java.util.Arrays;

public class MutantDetector {

    private static final int LONGITUD_SECUENCIA = 4;
    private static final int SECUENCIA_MINIMA_SER_MUTANTE = 2;

    private boolean buscarHorizontal(char[][] matriz, int i, int j){
        final char base = matriz[i][j];
        return  matriz[i][j+1] == base &&
                matriz[i][j+2] == base &&
                matriz[i][j+3] == base;
    }

    private boolean buscarVertical(char[][] matriz, int i, int j){
        final char base = matriz[i][j];
        return  matriz[i+1][j] == base &&
                matriz[i+2][j] == base &&
                matriz[i+3][j] == base;
    }

    private boolean buscarDiagonalDesc(char[][] matriz, int i, int j){
        final char base = matriz[i][j];
        return  matriz[i+1][j+1] == base &&
                matriz[i+2][j+2] == base &&
                matriz[i+3][j+3] == base;
    }

    private boolean buscarDiagonalAsc(char[][] matriz, int i, int j){
        final char base = matriz[i][j];
        return  matriz[i-1][j+1] == base &&
                matriz[i-2][j+2] == base &&
                matriz[i-3][j+3] == base;
    }

    public boolean isMutant(String[] dna) {
        final int n = dna.length;

        // 2. Inicializar un contador de secuencias encontradas.
        int contadorSecuencias = 0;
        char[][] matriz = new char[n][n];
        for (int i = 0; i < n; i++) {
            matriz[i] = dna[i].toCharArray();
        }

        // 3. Recorrer la matriz y buscar secuencias en las 4 direcciones (horizontal, vertical, diagonal descendente, diagonal ascendente).
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                if (col <= n - LONGITUD_SECUENCIA){
                    if (buscarHorizontal(matriz, row, col)){
                        contadorSecuencias++;
                        if (contadorSecuencias > 1) return true;
                    }
                }

                if (row <= n - LONGITUD_SECUENCIA){
                    if (buscarVertical(matriz, row, col)){
                        contadorSecuencias++;
                        if (contadorSecuencias > 1) return true;
                    }
                }

                if (row <= n - LONGITUD_SECUENCIA && col <= n - LONGITUD_SECUENCIA){
                    if (buscarDiagonalDesc(matriz, row, col)){
                        contadorSecuencias++;
                        if (contadorSecuencias > 1) return true;
                    }
                }

                if (row >= LONGITUD_SECUENCIA - 1 && col <= n - LONGITUD_SECUENCIA){
                    if (buscarDiagonalAsc(matriz, row, col)){
                        contadorSecuencias++;
                        if (contadorSecuencias > 1) return true;
                    }
                }
            }
        }

        // 4. Por cada secuencia encontrada, incrementar el contador.
        // 5. Si en algún momento el contador supera 1, se puede terminar la búsqueda y retornar `true` (Early Termination).
        // 6. Si se recorre toda la matriz y el contador no supera 1, retornar `false`.

        return false;
    }
}
