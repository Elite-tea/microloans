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

@Service
@RequiredArgsConstructor
public class IssuePointService {

    private final IssuePointRepository issuePointRepository;
    private final IssuePointMapper issuePointMapper;

    @Transactional
    public OldIssuePointDto getIssuePoint(Long issuePointId) {
        IssuePoint issuePoint = issuePointRepository.getReferenceById(issuePointId);
        return issuePointMapper.oldIssuePointMapper(issuePoint);
    }

    public List<IssuePoint> getAllIssuePoint () {
        List<IssuePoint> listpoint = issuePointRepository.findAll();

        return listpoint;
    }

    @Transactional
    public OldIssuePointDto updateIssuePoint (OldIssuePointDto updatePoint) {
        IssuePoint issuePoint = issuePointRepository.findById(updatePoint.getId())
                .orElseThrow(() -> new EntityNotFoundException("Точка выдачи не найдена"));
        issuePointToDto(updatePoint, issuePoint);
        return updatePoint;
    }

    public void issuePointToDto (OldIssuePointDto updatePoint, IssuePoint issuePoint) {
        if(updatePoint == null || issuePoint == null) return;

        if(!updatePoint.getName().equals(issuePoint.getName())) {
            issuePoint.setName(updatePoint.getName());
        }
    }

}
