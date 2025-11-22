package org.example.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MutantService {
    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    public boolean isMutant(String[] dna){
        String hash = calculateDnaHash(dna);
        log.info("Calculado hash de ADN: {}", hash);

        // Busca en la BD
        Optional<DnaRecord> recordOpt = dnaRecordRepository.findByDnaHash(hash);

        if (recordOpt.isPresent()) {
            log.info("Resultado encontrado en la base de datos (cache hit).");
            return recordOpt.get().isMutant();
        }

        log.info("Resultado no encontrado en la base de datos. Analizando ADN...");
        boolean isMutantResult = mutantDetector.isMutant(dna);

        DnaRecord newRecord = new DnaRecord(null, hash, isMutantResult, LocalDateTime.now());
        dnaRecordRepository.save(newRecord);
        log.info("Nuevo resultado guardado en la base de datos.");

        return isMutantResult;
    }

    private String calculateDnaHash(String[] dna){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dnaString = String.join("", dna);
            byte[] encodedHash = digest.digest(dnaString.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return  hexString.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error al calcular el hash", e);
        }
    }
}
