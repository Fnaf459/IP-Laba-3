package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.student.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ClientService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Client addClient(String firstName, String lastName) {
        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName)) {
            throw new IllegalArgumentException("Client name is null or empty");
        }
        final Client client = new Client(firstName, lastName);
        em.persist(client);
        return client;
    }

    @Transactional(readOnly = true)
    public Client findClient(Long id) {
        final Client client = em.find(Client.class, id);
        if (client == null) {
            throw new EntityNotFoundException(String.format("Client with id [%s] is not found", id));
        }
        return client;
    }

    @Transactional(readOnly = true)
    public List<Client> findAllClient() {
        return em.createQuery("select s from Client s", Client.class)
                .getResultList();
    }

    @Transactional
    public Client updateClient(Long id, String firstName, String lastName) {
        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName)) {
            throw new IllegalArgumentException("Client name is null or empty");
        }
        final Client currentClient = findClient(id);
        currentClient.setFirstName(firstName);
        currentClient.setLastName(lastName);
        return em.merge(currentClient);
    }

    @Transactional
    public Client deleteClient(Long id) {
        final Client currentClient = findClient(id);
        em.remove(currentClient);
        return currentClient;
    }

    @Transactional
    public void deleteAllClient() {
        em.createQuery("delete from Client").executeUpdate();
    }

}
