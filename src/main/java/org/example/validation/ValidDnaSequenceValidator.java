package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final int MIN_DNA_LENGTH = 4;
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        // 1. El array no puede ser nulo o más pequeño que el mínimo.
        if (dna == null || dna.length < MIN_DNA_LENGTH) {
            return false;
        }

        // 2. La matriz debe ser cuadrada (NxN).
        final int n = dna.length;
        for (String row : dna) {
            // 3. Ninguna fila puede ser nula y debe tener la longitud correcta.
            if (row == null || row.length() != n) {
                return false;
            }
            // 4. La fila solo debe contener caracteres válidos.
            for (char base : row.toCharArray()) {
                if (!VALID_BASES.contains(base)) {
                    return false;
                }
            }
        }

        // Si todas las validaciones pasan, el ADN es válido.
        return true;
    }
}
