package com.spring.demo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import com.spring.demo.dto.OccurrenceDto;
import com.spring.demo.entity.Occurrence;
import com.spring.demo.repository.AddressRepository;
import com.spring.demo.repository.ClientRepository;
import com.spring.demo.repository.OccurrenceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OccurrenceService {

    @Autowired
    private OccurrenceRepository repository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;

    public List<Occurrence> findAllOccurrences() {
        return repository.findAll();
    }

    public Occurrence findByIdOccurrenceOrThrowBadRequestException(Long uuid) {
        return repository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Occurrence not found."));
    }

    public Page<Occurrence> listFindAllOccurrencePageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public OccurrenceDto create(OccurrenceDto occurrenceDto) throws Exception {
        boolean clientExists = clientRepository.existsById(Long.valueOf(occurrenceDto.getIdClient()));
        if (!clientExists) {
            throw new Exception("Client with ID " + occurrenceDto.getIdClient() + " not found.");
        }

        boolean addressExists = addressRepository.existsById(Long.valueOf(occurrenceDto.getIdAddress()));
        if (!addressExists) {
            throw new Exception("Address with ID " + occurrenceDto.getIdAddress() + " not found.");
        }

        var dateCreate = Timestamp.valueOf(LocalDateTime.now());

        var occurrence = Occurrence.builder()
            .statusOccurrence(occurrenceDto.getStatusOccurrence())
            .idClient(occurrenceDto.getIdClient())
            .idAddress(occurrenceDto.getIdAddress())
            .dateOccurrence(dateCreate)
            .build();

        try {
            occurrence = repository.save(occurrence);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the occurrence", e);
        }

        occurrenceDto.setIdOccurrence(occurrence.getIdOccurrence());

        return occurrenceDto;
    }

    public OccurrenceDto update(OccurrenceDto occurrenceDto, Long occurrenceId) throws Exception {
        Occurrence occurrenceEntity = repository.findById(occurrenceId)
            .orElseThrow(() -> new EntityNotFoundException("Occurrence not found with id: " + occurrenceId));

        if (isOccurrenceFinished(occurrenceId)) {
            throw new IllegalStateException("This incident has already ended and cannot be updated.");
        }

        boolean clientExists = clientRepository.existsById(Long.valueOf(occurrenceDto.getIdClient()));
        if (!clientExists) {
            throw new Exception("Client with ID " + occurrenceDto.getIdClient() + " not found.");
        }

        boolean addressExists = addressRepository.existsById(Long.valueOf(occurrenceDto.getIdAddress()));
        if (!addressExists) {
            throw new Exception("Address with ID " + occurrenceDto.getIdAddress() + " not found.");
        }

        updateOccurrenceFields(occurrenceEntity, occurrenceDto);

       Occurrence updateOccurrence = repository.save(occurrenceEntity);

        return convertToDto(updateOccurrence);
    }

    private OccurrenceDto convertToDto(Occurrence occurrence) {
        return OccurrenceDto.builder()
            .idOccurrence(occurrence.getIdOccurrence())
            .idClient(occurrence.getIdClient())
            .idAddress(occurrence.getIdAddress())
            .statusOccurrence(occurrence.getStatusOccurrence())
            .dateOccurrence(occurrence.getDateOccurrence())
            .build();
    }

    private void updateOccurrenceFields(Occurrence occurrence, OccurrenceDto occurrenceDto) {
        var dateCreate = Timestamp.valueOf(LocalDateTime.now());

        occurrence.setStatusOccurrence("finished");
        occurrence.setIdClient(occurrenceDto.getIdClient());
        occurrence.setIdAddress(occurrenceDto.getIdAddress());
        occurrence.setDateOccurrence(dateCreate);
    }

    public boolean isOccurrenceFinished(Long occurrenceId) {
        Occurrence occurrenceEntity = repository.getOne(occurrenceId);

        return "finished".equalsIgnoreCase(occurrenceEntity.getStatusOccurrence());
    }

    public OccurrenceDto finishOccurrence(Long occurrenceId) {
        Occurrence occurrenceEntity = repository.findById(occurrenceId)
            .orElseThrow(() -> new EntityNotFoundException("Occurrence not found with id: " + occurrenceId));

        if ("finished".equalsIgnoreCase(occurrenceEntity.getStatusOccurrence())) {
            throw new IllegalStateException("This incident has already ended and cannot be updated.");
        }

        occurrenceEntity.setStatusOccurrence("finished");
        repository.save(occurrenceEntity);

        return convertToDto(occurrenceEntity);
    }

    public String delete(Long occurrenceId) {
        Occurrence occurrenceEntity = repository.findById(occurrenceId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Occurrence not found with id: " + occurrenceId));

        repository.deleteById(occurrenceId);
        return "Occurrence with id " + occurrenceId + " was deleted.";
    }
}
