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

/**
 * Сервис для работы с договорами.
 */
@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractMapper contractMapper;
    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final IssuePointRepository issuePointRepository;
    private final StatusRepository statusRepository;

    /**
     * Добавляет новый договор.
     * @param newContractDto DTO с данными нового договора
     * @return DTO созданного договора
     * @throws EntityNotFoundException если связанные сущности не найдены
     */
    @Transactional
    public NewContractDto addContract(NewContractDto newContractDto) {
        Contract contract = contractMapper.newContractMapping(newContractDto);

        contract.setClient(getClient(newContractDto.getClientId()));
        contract.setEmployee(getEmployee(newContractDto.getEmployeeId()));
        contract.setIssuePoint(getIssuePoint(newContractDto.getIssuePointId()));
        contract.setStatus(getStatus(newContractDto.getStatusId()));

        contractRepository.save(contract);
        return newContractDto;
    }

    /**
     * Получает договор по идентификатору.
     * @param id идентификатор договора
     * @return DTO с данными договора
     * @throws EntityNotFoundException если договор не найден
     */
    public UpdateContractDto getContract(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Договор не найден"));
        return contractMapper.oldContractMapping(contract);
    }

    /**
     * Получает список всех договоров.
     * @return список DTO договоров
     */
    public List<OldContractDto> getAllContract() {
        return contractMapper.noPasswordContractDTO(contractRepository.findAll());
    }

    /**
     * Обновляет данные договора.
     * @param dto DTO с обновленными данными договора
     * @return DTO обновленного договора
     * @throws EntityNotFoundException если договор или связанные сущности не найдены
     */
    @Transactional
    public UpdateContractDto updateContract(UpdateContractDto dto) {
        Contract contract = contractRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Договор не найден"));

        updateFromDto(dto, contract);
        return dto;
    }

/**
 * Закрывает договор (устанавливает статус "Закрыт").
 * @param closeStatusContractDto DTO с полученным для закрытия договором
 * @return DTO закрытого договора
 * @throws EntityNotFoundException если договор или статус не найдены
 */
@Transactional
public OldContractDto closeStatus (OldContractDto closeStatusContractDto) {
            Contract contract = contractRepository.findById(closeStatusContractDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Договор не найден"));

            Status closedStatus = statusRepository.findById(2L)
                    .orElseThrow(() -> new EntityNotFoundException("Статус 'Закрыт' не найден"));

            contract.setStatus(closedStatus);
            contractRepository.save(contract);
            return contractMapper.noPasswordContractDTO(List.of(contract)).get(0);
        }

    /**
     * Вынес все общие методы отдельно, для предотвращения дублирования кода и улучшения читаемости
     */
        private Client getClient(Long id) {
            return clientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Клиент не найден"));
        }

        private Employee getEmployee(Long id) {
            return employeeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Сотрудник не найден"));
        }

        private IssuePoint getIssuePoint(Long id) {
            return issuePointRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Пункт выдачи не найден"));
        }

        private Status getStatus(Long id) {
            return statusRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Статус не найден"));
        }


    private void updateFromDto(UpdateContractDto dto, Contract contract) {
        if (dto == null || contract == null) {
            throw new IllegalArgumentException("DTO и сущность договора не могут быть null");
        }

        if (dto.getClientId() != null) {
            contract.setClient(getClient(dto.getClientId()));
        }
        if (dto.getDateOfIssue() != null) {
            contract.setDateOfIssue(dto.getDateOfIssue());
        }
        if (dto.getAmount() != null) {
            contract.setAmount(dto.getAmount());
        }
        if (dto.getEmployeeId() != null) {
            contract.setEmployee(getEmployee(dto.getEmployeeId()));
        }
        if (dto.getDateTerm() != null) {
            contract.setDateTerm(dto.getDateTerm());
        }
        if (dto.getIssuePointId() != null) {
            contract.setIssuePoint(getIssuePoint(dto.getIssuePointId()));
        }
        if (dto.getStatusId() != null) {
            contract.setStatus(getStatus(dto.getStatusId()));
        }
    }
}