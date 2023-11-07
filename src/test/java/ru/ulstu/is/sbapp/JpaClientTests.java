package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.Client;
import ru.ulstu.is.sbapp.student.service.ClientService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
public class JpaClientTests {
    private static final Logger log = LoggerFactory.getLogger(JpaClientTests.class);

    @Autowired
    private ClientService clientService;

    @Test
    void testClientCreate() {
        clientService.deleteAllClient();
        final Client client = clientService.addClient("Иван", "Иванов");
        log.info(client.toString());
        Assertions.assertNotNull(client.getId());
    }

    @Test
    void testStudentRead() {
        clientService.deleteAllClient();
        final Client client = clientService.addClient("Иван", "Иванов");
        log.info(client.toString());
        final Client findClient = clientService.findClient(client.getId());
        log.info(findClient.toString());
        Assertions.assertEquals(client, findClient);
    }

    @Test
    void testClientReadNotFound() {
        clientService.deleteAllClient();
        Assertions.assertThrows(EntityNotFoundException.class, () -> clientService.findClient(-1L));
    }

    @Test
    void testClientReadAll() {
        clientService.deleteAllClient();
        clientService.addClient("Иван", "Иванов");
        clientService.addClient("Петр", "Петров");
        final List<Client> clients = clientService.findAllClient();
        log.info(clients.toString());
        Assertions.assertEquals(clients.size(), 2);
    }

    @Test
    void testClientReadAllEmpty() {
        clientService.deleteAllClient();
        final List<Client> clients = clientService.findAllClient();
        log.info(clients.toString());
        Assertions.assertEquals(clients.size(), 0);
    }
}
