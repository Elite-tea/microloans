package ru.hometask.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hometask.dto.NewClientDto;
import ru.hometask.dto.OldClientDto;
import ru.hometask.entities.Client;
import ru.hometask.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public NewClientDto addClient (@RequestBody @Valid NewClientDto newClient) {
        log.info("Клиент создан");
        return clientService.addClient(newClient);
    }

    @GetMapping("/{idClient}")
    @ResponseStatus(HttpStatus.OK)
    public OldClientDto getClient (@PathVariable Long idClient) {
        log.info("Я ПОЛУЧИЛ И ВЕРНУЛ КЛИЕНТА!");
        return clientService.getClient(idClient);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getAllClient() {
        log.info("Отдали всех клиентов");
        return clientService.getAllClient();
    }

    @PutMapping("/{idClient}")
    @ResponseStatus(HttpStatus.OK)
    public OldClientDto updateClient(@RequestBody @Valid OldClientDto updateClient) {
        log.info("Клиент обновлен");
        return clientService.updateClient(updateClient);
    }
}

