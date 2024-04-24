    package com.spring.demo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import com.spring.demo.dto.ClientDto;
import com.spring.demo.entity.Client;
import com.spring.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<Client> findAllClients() {
        return repository.findAll();
    }

    public Page<Client> listFindAllClientsPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Client findByIdClientOrThrowBadRequestException(Long uuid) {
        return repository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not found."));
    }

    public ClientDto create(ClientDto clientDto) {
        var dateCreate = Timestamp.valueOf(LocalDateTime.now());

        var client = Client.builder()
            .nameClient(clientDto.getNameClient())
            .dateOfBirth(clientDto.getDateOfBirth())
            .socialSecurityNumber(clientDto.getSocialSecurityNumber())
            .dateOfCreation(dateCreate)
            .build();

        try {
            client = repository.save(client);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the client", e);
        }

        clientDto.setIdClient(client.getIdClient());

        return clientDto;
    }

    public ClientDto update(ClientDto clientDto, Long clientId) {
        Client client = repository.findById(clientId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        updateClientFields(client, clientDto);

        Client updatedClient = repository.save(client);

        return convertToDto(updatedClient);
    }

    private ClientDto convertToDto(Client client) {
        return ClientDto.builder()
            .idClient(client.getIdClient())
            .nameClient(client.getNameClient())
            .socialSecurityNumber(client.getSocialSecurityNumber())
            .dateOfBirth(client.getDateOfBirth())
            .dateOfCreation(client.getDateOfCreation())
            .build();
    }

    private void updateClientFields(Client client, ClientDto clientDto) {
        client.setNameClient(clientDto.getNameClient());
        client.setSocialSecurityNumber(clientDto.getSocialSecurityNumber());
        client.setDateOfBirth(clientDto.getDateOfBirth());
        client.setDateOfCreation(Timestamp.valueOf(LocalDateTime.now()));
    }

    public String delete(Long clientId) {
        Client client = repository.findById(clientId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        repository.deleteById(clientId);
        return "Client with id " + clientId + " was deleted.";
    }
}
