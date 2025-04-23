package ru.hometask.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hometask.dto.AdminReportDto;
import ru.hometask.entities.Contract;
import ru.hometask.mappers.ReportMapper;
import ru.hometask.repositories.ContractRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    ContractService contractService;
    private final ContractRepository contractRepository;
    ReportMapper reportMapper;

    public List<AdminReportDto> getReportActive() {
        //Все активные договора
        List<Contract> sourceContract = contractRepository.findAll();

        List<AdminReportDto> returnReportActive = sourceContract.stream()
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
                                    dto.setAllAmount(contracts.stream().mapToLong(Contract::getAmount).sum());
                                    dto.setAllCostContract((long) contracts.size());
                                    return dto;
                                }
                        )
                ))
                .values()
                .stream()
                .collect(Collectors.toList());

        return returnReportActive;
    }
}
