package com.spring.demo.service;

import java.util.List;
import com.spring.demo.dto.AddressDto;
import com.spring.demo.entity.Address;
import com.spring.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public AddressService(AddressRepository repository) {
    }

    public List<Address> findAllAddress() {
        return repository.findAll();
    }

    public Address findByIdAddressOrThrowBadRequestException(Long uuid) {
        return repository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address not found."));
    }

    public Page<Address> listFindAllAddressPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public AddressDto createAddress(AddressDto addressDto) {
        var address = Address.builder()
            .publicPlace(addressDto.getPublicPlace())
            .neighborhood(addressDto.getNeighborhood())
            .zipCode(addressDto.getZipCode())
            .city(addressDto.getCity())
            .state(addressDto.getState())
            .build();

        try {
            address = repository.save(address);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the address", e);
        }

        addressDto.setIdAddress(address.getIdAddress());

        return addressDto;
    }

    public AddressDto updateAddress(AddressDto addressDto, Long idAddress) {
        Address address = repository.findById(idAddress)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        updateAddressFields(address, addressDto);

        Address updatedAddress = repository.save(address);

        return convertToDto(updatedAddress);
    }

    private void updateAddressFields(Address address, AddressDto addressDto) {
        address.setPublicPlace(addressDto.getPublicPlace());
        address.setNeighborhood(addressDto.getNeighborhood());
        address.setZipCode(addressDto.getZipCode());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
    }

    private AddressDto convertToDto(Address address) {
        return AddressDto.builder()
            .idAddress(address.getIdAddress())
            .publicPlace(address.getPublicPlace())
            .neighborhood(address.getNeighborhood())
            .zipCode(address.getZipCode())
            .city(address.getCity())
            .state(address.getState())
            .build();
    }

    public String deleteAddress(Long idAddress) {
        Address address = repository.findById(idAddress)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        repository.deleteById(idAddress);

        return "Address with id " + idAddress + " was deleted.";
    }
}
