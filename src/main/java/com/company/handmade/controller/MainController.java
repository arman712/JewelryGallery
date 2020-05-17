package com.company.handmade.controller;

import com.company.handmade.model.Client;
import com.company.handmade.repository.ClientRepository;
import com.company.handmade.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping(value = "/works")
    public String getWorks(Model model, HttpServletRequest request ) {


        List works = workRepository.findAll();
        model.addAttribute("works", works);


        return "works";
    }


    @RequestMapping("/order/{id}")
    public String order(@PathVariable int id, Model model) {

        model.addAttribute("workId", id);
        return "order";
    }

    @RequestMapping("/addClient/{id}")
    public String addClient(@PathVariable String id, Client client, Model model) {

        Client client1 = new Client();
        client1.setName(client.getName());
        client1.setAddress(client.getAddress());
        client1.setPhoneNumber(client.getPhoneNumber());
        client1.setWorkId(id);

        clientRepository.save(client1);



        return "redirect:/works";
    }





}
