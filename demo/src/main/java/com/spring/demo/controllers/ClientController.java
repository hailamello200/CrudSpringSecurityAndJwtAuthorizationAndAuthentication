package com.spring.demo.controllers;

import java.util.List;
import com.spring.demo.dto.ClientDto;
import com.spring.demo.entity.Client;
import com.spring.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/clients", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClientController {

    @Autowired
    private final ClientService service;

    @GetMapping
    public ResponseEntity<List<Client>> findAllClients() {
        return ResponseEntity.ok(service.findAllClients());
    }

    @GetMapping("/pageable")
    public List<Client> findAllClientsPageable(Pageable pageable) {
        return service.listFindAllClientsPageable(pageable).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findByIdClient(@PathVariable Long uuid) {
        return ResponseEntity.ok(service.findByIdClientOrThrowBadRequestException(uuid));
    }

    @PostMapping
    @ResponseBody
    public ClientDto create(@RequestBody ClientDto clientDto) {
        return service.create(clientDto);
    }

    @PutMapping("/{clientId}")
    @ResponseBody
    public ClientDto update(@PathVariable("clientId") Long clientId,
        @RequestBody ClientDto clientDto) {
        return service.update(clientDto, clientId);
    }

    @DeleteMapping("/{clientId}")
    @ResponseBody
    public String deleted(@PathVariable("clientId") Long clientId) {
        return service.delete(clientId);
    }
}
