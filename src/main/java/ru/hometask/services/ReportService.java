package ru.hometask.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hometask.dto.AdminReportDto;
import ru.hometask.entities.Contract;
import ru.hometask.repositories.ContractRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ContractRepository contractRepository;

    public List<AdminReportDto> getReport() {
        // Все договора
        List<Contract> sourceContract = contractRepository.findAll();

        // Используем BigDecimal для точного суммирования
        // Предполагаем, что getAmount() возвращает BigDecimal

        return new ArrayList<>(sourceContract.stream()
                .filter(contract -> contract.getIssuePoint() != null && contract.getStatus() != null)
                .collect(Collectors.groupingBy(
                        contract -> Map.entry(
                                contract.getIssuePoint().getName().trim().toLowerCase(),
                                contract.getStatus().getName().trim().toLowerCase()
                        ),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                contracts -> {
                                    AdminReportDto dto = new AdminReportDto();
                                    dto.setIssuePointName(contracts.get(0).getIssuePoint().getName());
                                    dto.setStatusName(contracts.get(0).getStatus().getName());

                                    // Используем BigDecimal для точного суммирования
                                    BigDecimal totalAmount = contracts.stream()
                                            .map(Contract::getAmount)  // Предполагаем, что getAmount() возвращает BigDecimal
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                                    dto.setAllAmount(totalAmount);

                                    dto.setAllCostContract((long) contracts.size());
                                    return dto;
                                }
                        )
                ))
                .values());
    }
}
