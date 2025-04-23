package ru.hometask.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hometask.dto.NewContractDto;
import ru.hometask.dto.OldContractDto;
import ru.hometask.dto.UpdateContractDto;
import ru.hometask.entities.*;
import ru.hometask.mappers.ContractMapper;
import ru.hometask.repositories.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractMapper contractMapper;
    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final IssuePointRepository issuePointRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public NewContractDto addContract(NewContractDto newContract) {
        Employee employee = employeeRepository.getReferenceById(newContract.getIdEmployee());
        Client client = clientRepository.getReferenceById(newContract.getIdClient());
        IssuePoint issuePoint = issuePointRepository.getReferenceById(newContract.getIdIssuePoint());
        Status status = statusRepository.getReferenceById(newContract.getIdStatus());

        Contract contract = contractMapper.newContractMapping(newContract);
        contract.setClient(client);
        contract.setEmployee(employee);
        contract.setIssuePoint(issuePoint);
        contract.setStatus(status);

        contractRepository.save(contract);
        return newContract;
    }

    public UpdateContractDto getContract(Long id) {

        Contract contract = contractRepository.getReferenceById(id);

        UpdateContractDto updateContract = contractMapper.oldContractMapping(contract);
        updateContract.setClientId(contract.getClient().getId());
        updateContract.setEmployeeId(contract.getEmployee().getId());
        updateContract.setIssuePointId(contract.getIssuePoint().getId());
        updateContract.setStatusId(contract.getStatus().getId());

        return updateContract;
    }

    public List<OldContractDto> getAllContract() {
        List <Contract> allContract = contractRepository.findAll();
        return contractMapper.noPasswordContractDTO(allContract);
    }

    @Transactional
    public UpdateContractDto updateContract(UpdateContractDto dto) {
        Contract contract = contractRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Контракт не найден"));

        updateFromDto(dto, contract);
        // save() не обязателен, т.к. @Transactional сохранит изменения автоматически
        return dto;
    }

    private void updateFromDto(UpdateContractDto dto, Contract contract) {
        if (dto == null || contract == null) return;
        Contract oldContract = contractRepository.getReferenceById(dto.getId());

        // Обновляем только те поля, которые не null в DTO
        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Контракт не найден."));
            contract.setClient(client);
        }
        if (dto.getAmount() != 0 && dto.getAmount() != oldContract.getAmount() ) {
            contract.setAmount(dto.getAmount());
        }
        if (!dto.getDateOfIssue().isEqual(oldContract.getDateOfIssue())) {
            contract.setDateOfIssue(dto.getDateOfIssue());
        }
        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Сотрудник не найден."));
            contract.setEmployee(employee);
        }
        if (!dto.getDateTerm().isEqual(oldContract.getDateTerm())) {
            contract.setDateTerm(dto.getDateTerm());
        }
        if (dto.getIssuePointId() != null) {
            IssuePoint issuePoint = issuePointRepository.findById(dto.getIssuePointId())
                    .orElseThrow(() -> new EntityNotFoundException("Точка выдачи не найдена."));
            contract.setIssuePoint(issuePoint);
        }
        if (dto.getStatusId() != null) {
            Status status = statusRepository.findById(dto.getStatusId())
                    .orElseThrow(() -> new EntityNotFoundException("Статус не существует."));
            contract.setStatus(status);
        }
    }
}