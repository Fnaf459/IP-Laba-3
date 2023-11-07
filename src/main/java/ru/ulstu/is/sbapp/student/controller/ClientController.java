package ru.ulstu.is.sbapp.student.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.student.model.Client;
import ru.ulstu.is.sbapp.student.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.findClient(id);
    }

    @GetMapping("/")
    public List<Client> getClients() {
        return clientService.findAllClient();
    }

    @PostMapping("/")
    public Client createClient(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName) {
        return clientService.addClient(firstName, lastName);
    }

    @PatchMapping("/{id}")
    public Client updateClient(@PathVariable Long id,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName) {
        return clientService.updateClient(id, firstName, lastName);
    }

    @DeleteMapping("/{id}")
    public Client deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }
}
