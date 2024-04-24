package com.spring.demo.controllers;

import java.util.List;
import com.spring.demo.dto.AddressDto;
import com.spring.demo.entity.Address;
import com.spring.demo.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/var/address", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AddressController {

    @Autowired
    private final AddressService service;

    @GetMapping
    public ResponseEntity<List<Address>> findAllAddress() {
        return ResponseEntity.ok(service.findAllAddress());
    }

    @GetMapping("/pageable")
    public List<Address> findAllClientsPageable(Pageable pageable) {
        return service.listFindAllAddressPageable(pageable).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> findByIdClient(@PathVariable Long uuid) {
        return ResponseEntity.ok(service.findByIdAddressOrThrowBadRequestException(uuid));
    }

    @PostMapping
    @ResponseBody
    public AddressDto create(@RequestBody AddressDto addressDto) {
        return service.createAddress(addressDto);
    }

    @PutMapping("/{addressId}")
    @ResponseBody
    public AddressDto update(@PathVariable("addressId") Long addressId,
        @RequestBody AddressDto addressDto) {
        return service.updateAddress(addressDto, addressId);
    }

    @DeleteMapping("/{addressId}")
    @ResponseBody
    public String deleted(@PathVariable("addressId") Long addressId) {
        return service.deleteAddress(addressId);
    }
}
