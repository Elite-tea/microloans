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
        Employee employee = employeeRepository.getReferenceById(newContract.getEmployeeId());
        Client client = clientRepository.getReferenceById(newContract.getClientId());
        IssuePoint issuePoint = issuePointRepository.getReferenceById(newContract.getIssuePointId());
        Status status = statusRepository.getReferenceById(newContract.getStatusId());

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
        return contractMapper.noPasswordContractDTO(contractRepository.findAll());
    }

    @Transactional
    public UpdateContractDto updateContract(UpdateContractDto dto) {
        Contract contract = contractRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Контракт не найден"));

        updateFromDto(dto, contract);
        return dto;
    }

    @Transactional
    public OldContractDto closeStatus (OldContractDto closeStatusContractDto) {
        Contract contract = contractRepository.findById(closeStatusContractDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Контракт не найден"));

        closeStatusContract(closeStatusContractDto, contract);
        return closeStatusContractDto;
    }

    /**
     * Закрывает статус договора, устанавливая статус "Закрыт" (ID = 2)
     *
     * @param closeStatusContractDto DTO с данными для закрытия договора
     * @param contract Сущность договора для обновления
     * @throws EntityNotFoundException если статус с ID=2 не найден
     * @throws IllegalArgumentException если переданные параметры null
     */
    private void closeStatusContract(OldContractDto closeStatusContractDto, Contract contract) {
        if (closeStatusContractDto == null || contract == null) {
            throw new IllegalArgumentException("Параметры closeStatusContractDto и contract не могут быть null");
        }

        final Long CLOSED_STATUS_ID = 2L;
        Status closedStatus = statusRepository.findById(CLOSED_STATUS_ID)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Статус с ID=%d не найден", CLOSED_STATUS_ID)));

        contract.setStatus(closedStatus);
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