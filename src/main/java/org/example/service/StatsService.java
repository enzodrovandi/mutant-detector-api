package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse getStats(){
        long countMutantDna = dnaRecordRepository.countByIsMutant(true);
        long countHumanDna = dnaRecordRepository.countByIsMutant(false);
        double ratio;
        if (countHumanDna == 0){
            ratio = countMutantDna;
        }else{
            ratio = (double) countMutantDna / countHumanDna;
        }
        return new StatsResponse(countMutantDna, countHumanDna, ratio);
    }
}
