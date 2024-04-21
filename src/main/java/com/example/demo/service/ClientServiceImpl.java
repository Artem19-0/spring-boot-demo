package com.example.demo.service;

import com.example.demo.controller.mvc.ClientMVCController;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{


    private ClientRepository repo;

    @Autowired
    public void setRepo(ClientRepository repo) {
        this.repo = repo;
    }

    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);



    @Override
    public Client addClient(Client client) {
        Client client1 = repo.save(client);
        if (client1 != null){
            logger.info("Client have already found");
            return client1;
        }else {
            logger.error("Client is empty");
            return null;
        }
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = repo.findAll();
        logger.info("show all clients ;)");
        return clients;
    }

    @Override
    public Client updateClient(Integer id, Client client) {
        Client client1 = repo.getOne(id);
        client1 = client;
        repo.save(client1);
        logger.info("client have updated successfully ;)");
        return client1;
    }

    @Override
    public String deleteClient(Integer id) {
        repo.deleteById(id);

        Client client = repo.getOne(id);
        if (client == null) {
            logger.info("client have deleted successfully ;)");
            return "Client with id: " + id + " deleted successfully!";
        }else {
            logger.error("client have deleted unsuccessfully ;)");
            return "Client with id: " + id + " deleted unsuccessfully!";
        }
    }



}
