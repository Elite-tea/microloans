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

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public NewClientDto addClient (NewClientDto newClient) {
        Client client = clientMapper.newUserMapping(newClient);
        client.setPhone(newClient.getPhone());
        client.setFullName(newClient.getFullName());
        clientRepository.save(client);
        return newClient;
    }

    public OldClientDto getClient (Long id) {
        Client client = clientRepository.getReferenceById(id);
        return clientMapper.oldUserMapping(client);
    }

    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Transactional
    public OldClientDto updateClient (OldClientDto updateClient) {
        Client client = clientRepository.findById(updateClient.getId())
                .orElseThrow(() -> new EntityNotFoundException("Клиент не найден."));

        updateFromDto(updateClient, client);
        return updateClient;
    }

    public void updateFromDto (OldClientDto updateClient, Client client) {
        if(updateClient == null || client == null) return;

        if(!client.getFullName().equals(updateClient.getFullName())) {
            client.setFullName(updateClient.getFullName());
        }

        if(!client.getPhone().equals(updateClient.getPhone())) {
            client.setPhone(updateClient.getPhone());
        }
    }

}
