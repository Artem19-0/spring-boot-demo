package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.model.Client;

import java.util.List;

public interface ClientService {

    public Client addClient(Client client);

    public List<Client> getAllClients();

    public Client updateClient(Integer id, Client client);

    public String deleteClient(Integer id);

}
