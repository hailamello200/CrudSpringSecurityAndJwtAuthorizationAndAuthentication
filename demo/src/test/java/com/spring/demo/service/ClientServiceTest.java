package com.spring.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import com.spring.demo.entity.Client;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Test
    void findAllClients() {
        List<Client> clients = clientService.findAllClients();

        assertEquals(false, clients.isEmpty(), "Clients list is empty");
    }
}