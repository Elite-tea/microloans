package ru.hometask.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hometask.dto.NewClientDto;
import ru.hometask.dto.OldClientDto;
import ru.hometask.entities.Client;
import ru.hometask.mappers.ClientMapper;
import ru.hometask.repositories.ClientRepository;

import java.util.List;

/**
 * Сервис для работы с клиентами.
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    /**
     * Сохраняет клиента в базу данных.
     * @param client сущность клиента для сохранения
     * @return сохраненная сущность клиента
     */
    @Transactional
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    /**
     * Добавляет нового клиента.
     * @param newClientDto DTO с данными нового клиента
     * @return DTO созданного клиента
     */
    @Transactional
    public NewClientDto addClient(NewClientDto newClientDto) {
        Client client = clientMapper.newUserMapping(newClientDto);
        clientRepository.save(client);
        return newClientDto;
    }

    /**
     * Получает клиента по идентификатору.
     * @param id идентификатор клиента
     * @return DTO с данными клиента
     * @throws EntityNotFoundException если клиент не найден
     */
    public OldClientDto getClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Клиент не найден"));
        return clientMapper.oldUserMapping(client);
    }

    /**
     * Получает список всех клиентов.
     * @return список сущностей клиентов
     */
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    /**
     * Обновляет данные клиента.
     * @param updateClientDto DTO с обновленными данными клиента
     * @return DTO обновленного клиента
     * @throws EntityNotFoundException если клиент не найден
     */
    @Transactional
    public OldClientDto updateClient(OldClientDto updateClientDto) {
        Client client = clientRepository.findById(updateClientDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Клиент не найден"));

        updateFromDto(updateClientDto, client);
        clientRepository.save(client);
        return updateClientDto;
    }

    private void updateFromDto(OldClientDto dto, Client client) {
        if (dto == null || client == null) {
            throw new IllegalArgumentException("DTO и сущность клиента не могут быть null");
        }

        if (dto.getFullName() != null && !dto.getFullName().equals(client.getFullName())) {
            client.setFullName(dto.getFullName());
        }

        if (dto.getPhone() != null && !dto.getPhone().equals(client.getPhone())) {
            client.setPhone(dto.getPhone());
        }
    }
}