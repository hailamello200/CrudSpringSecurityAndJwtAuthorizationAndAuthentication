package com.spring.demo.repository;

import com.spring.demo.entity.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepositoryPageable extends PagingAndSortingRepository<Client, Long> {

}
