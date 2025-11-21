package org.example.service;

public class MutantDetector {
    public boolean isMutant(String[] dna) {
        // 1. Validar la matriz de ADN (tamaño, caracteres válidos).
        if (dna == null || dna.length < 4){
            return false;
        }

        final int size = dna.length;

        for (String row : dna) {
            if (row.length() != size){
                return false;
            }
            for (char c : row.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G'){
                    return false;
                }
            }
        }


        // 2. Inicializar un contador de secuencias encontradas.
        int contador = 0;

        // 3. Recorrer la matriz y buscar secuencias en las 4 direcciones (horizontal, vertical, diagonal descendente, diagonal ascendente).
        // 4. Por cada secuencia encontrada, incrementar el contador.
        // 5. Si en algún momento el contador supera 1, se puede terminar la búsqueda y retornar `true` (Early Termination).
        // 6. Si se recorre toda la matriz y el contador no supera 1, retornar `false`.

        return false;
    }
}
