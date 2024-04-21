package com.example.demo.controller.mvc;


import com.example.demo.model.Client;
import com.example.demo.service.ClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ClientMVCController {

    private static Logger logger = LoggerFactory.getLogger(ClientMVCController.class);


    private ClientServiceImpl service;

    @Autowired
    public void setService(ClientServiceImpl service) {
        this.service = service;
    }


    //    private static List<Client> clients = new ArrayList<>();
//    static {
//        logger.info("Creating Clients");
//        Client client = new Client("Alex", "Alex.G@gmail.com","123" , "Male", "Some note", true,
//                new Date(System.currentTimeMillis()), "Programmer");
//        Client client1 = new Client("Bob", "Bob.G@gmail.com","333" , "Male", "Some note", false,
//                new Date(System.currentTimeMillis()), "Programmer");
//        Client client2 = new Client("John", "John.G@gmail.com","1224" , "Male", "Some note", true,
//                new Date(System.currentTimeMillis()), "Programmer");
//        Client client3 = new Client("Jim", "Jim.G@gmail.com","2344" , "Male", "Some note", false,
//                new Date(System.currentTimeMillis()), "Programmer");
//
//        clients.addAll(Arrays.asList(client, client1, client2, client3));
//    }



    @GetMapping("/register")
    public String showForm(Model model) {
        logger.info("Show form for register");
        Client client = new Client();
        model.addAttribute("client", client);
        logger.info("Add client {}" , client);
        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);

        return "register_form";
    }



    @PostMapping("/clients/register")
    public String submitForm(@ModelAttribute("client") Client client) {
        System.out.println(client);
        return "register_success";
    }



    @GetMapping("/show_all")
    public String showAll(Model model){
        logger.info("Show all clients");
        List<Client> clients = service.getAllClients();
        model.addAttribute("clients", clients);
        return "show_all";
    }


    @PostMapping("/delete1/{id}")
    public String deleteById(@PathVariable(name = "id") int id){
        String result = service.deleteClient(id);
        logger.info(result);
        return "deleted";
    }




}
