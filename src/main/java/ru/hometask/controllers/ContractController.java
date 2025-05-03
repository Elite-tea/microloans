package ru.hometask.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hometask.dto.NewContractDto;
import ru.hometask.dto.OldContractDto;
import ru.hometask.dto.UpdateContractDto;
import ru.hometask.entities.Contract;
import ru.hometask.services.ContractService;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
@Slf4j
public class ContractController {

    public final ContractService contractService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public NewContractDto createContract(@RequestBody @Valid NewContractDto newContract) {
        log.info("Я СОЗДАЛ КОНТРАКТ!");
        return contractService.addContract(newContract);
    }

    @GetMapping("/{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateContractDto getContract(@PathVariable Long idContract) {
        log.info("Я ПОЛУЧИЛ И ВЕРНУЛ КОНТРАКТ!");
        return contractService.getContract(idContract);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<OldContractDto> getAllContract() {
        log.info("Пу-пу-пу, отдал все контракты");
        return contractService.getAllContract();
    }

    @PutMapping("/{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateContractDto updateContract(@RequestBody @Valid UpdateContractDto newContract) {
        log.info("Обновил контракт");
        return contractService.updateContract(newContract);
    }

}