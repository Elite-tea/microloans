package ru.hometask.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hometask.dto.OldIssuePointDto;
import ru.hometask.entities.IssuePoint;
import ru.hometask.mappers.IssuePointMapper;
import ru.hometask.repositories.IssuePointRepository;

import java.util.List;

/**
 * Сервис для работы с пунктами выдачи.
 */
@Service
@RequiredArgsConstructor
public class IssuePointService {
    private final IssuePointRepository issuePointRepository;
    private final IssuePointMapper issuePointMapper;

    /**
     * Получает пункт выдачи по ID.
     * @param issuePointId ID пункта выдачи
     * @return DTO пункта выдачи
     * @throws EntityNotFoundException если пункт не найден
     */
    @Transactional(readOnly = true)
    public OldIssuePointDto getIssuePointById(Long issuePointId) {
        IssuePoint issuePoint = issuePointRepository.findById(issuePointId)
                .orElseThrow(() -> new EntityNotFoundException("Пункт выдачи не найден"));
        return issuePointMapper.oldIssuePointMapper(issuePoint);
    }

    /**
     * Получает все пункты выдачи.
     * @return список пунктов выдачи
     */
    @Transactional(readOnly = true)
    public List<IssuePoint> getAllIssuePoints() {
        return issuePointRepository.findAll();
    }

    /**
     * Обновляет данные пункта выдачи.
     * @param updateDto DTO с обновленными данными
     * @return обновленное DTO
     * @throws EntityNotFoundException если пункт не найден
     */
    @Transactional
    public OldIssuePointDto updateIssuePoint(OldIssuePointDto updateDto) {
        IssuePoint issuePoint = issuePointRepository.findById(updateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пункт выдачи не найден"));

        if (updateDto.getName() != null) {
            issuePoint.setName(updateDto.getName());
        }
        if (updateDto.getAddress() != null) {
            issuePoint.setAddress(updateDto.getAddress());
        }

        issuePointRepository.save(issuePoint);
        return updateDto;
    }
}