package com.spring.demo.controllers;

import java.util.List;
import com.spring.demo.dto.OccurrenceDto;
import com.spring.demo.entity.Occurrence;
import com.spring.demo.service.OccurrenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/occurrence", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OccurrenceController {

    @Autowired
    private final OccurrenceService service;

    @GetMapping
    public ResponseEntity<List<Occurrence>> findAllClients() {
        return ResponseEntity.ok(service.findAllOccurrences());
    }

    @GetMapping("/pageable")
    public List<Occurrence> findAllOccurrencePageable(Pageable pageable) {
        return service.listFindAllOccurrencePageable(pageable).getContent();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Occurrence> findByIdOccurrence(@PathVariable Long uuid) {
        return ResponseEntity.ok(service.findByIdOccurrenceOrThrowBadRequestException(uuid));
    }

    @PostMapping
    @ResponseBody
    public OccurrenceDto create(@RequestBody OccurrenceDto occurrenceDto) throws Exception {
        return service.create(occurrenceDto);
    }

    @PutMapping("/{occurrenceId}")
    @ResponseBody
    public OccurrenceDto update(@PathVariable("occurrenceId") Long occurrenceId,
        @RequestBody OccurrenceDto occurrenceDto) throws Exception {
        return service.update(occurrenceDto, occurrenceId);
    }

    @PutMapping("/finish/{cod_ocorrencia}")
    @ResponseBody
    public OccurrenceDto finishOccurrence(@PathVariable("cod_ocorrencia") Long occurrenceId) {
        return service.finishOccurrence(occurrenceId);
    }

    @DeleteMapping("/{occurrenceId}")
    @ResponseBody
    public String deleted(@PathVariable("occurrenceId") Long occurrenceId) {
        return service.delete(occurrenceId);
    }
}
