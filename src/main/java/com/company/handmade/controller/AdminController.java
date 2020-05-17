package com.company.handmade.controller;

import com.company.handmade.model.Admin;
import com.company.handmade.model.Client;
import com.company.handmade.model.Work;
import com.company.handmade.repository.ClientRepository;
import com.company.handmade.repository.WorkRepository;
import com.company.handmade.service.AdminService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private WorkRepository workRepository;


    @RequestMapping("/login")
    public String login(Admin admin, Model model, HttpSession httpSession) {



        Admin admin1 = adminService.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());

        if (admin1 == null) {
            model.addAttribute("error", "Wrong username or password");
            return "login";
        }

        httpSession.setAttribute("token", admin1.getUsername());

        return "adminPanel";
    }

    @RequestMapping("/orders")
    public String getOrders(@SessionAttribute(required = false) String token, Model model) {

        if (token == null) {
            model.addAttribute("error", "Forbidden");
            return "login";
        }

        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);


        return "orders";
    }

    @RequestMapping("/works")
    public String getWorks(@SessionAttribute(required = false) String token, Model model) {

        if (token == null) {
            model.addAttribute("error", "Forbidden");
            return "login";
        }

        List<Work> works = workRepository.findAll();
        model.addAttribute("works", works);


        return "worksAdmin";
    }


    @RequestMapping("/order/delete/{id}")
    public String deleteOrder(@SessionAttribute(required = false) String token, Model model, @PathVariable String id) {


        if (token == null) {
            model.addAttribute("error", "Forbidden");
            return "login";
        }

        clientRepository.deleteById(Integer.parseInt(id));


        return "redirect:/admin/orders";
    }

    @RequestMapping("/work/delete/{id}")
    public String deleteWork(@SessionAttribute(required = false) String token, Model model, @PathVariable String id) {


        if (token == null) {
            model.addAttribute("error", "Forbidden");
            return "login";
        }

        workRepository.deleteById(Integer.parseInt(id));


        return "redirect:/admin/works";
    }

    @GetMapping("/work/edit/{id}")
    public String getEditWork(@SessionAttribute(required = false) String token, Model model, @PathVariable String id) {


        if (token == null) {
            model.addAttribute("error", "Forbidden");
            return "login";
        }

        System.out.println(id);
        model.addAttribute("path", "/admin/work/edit/" + id);

        return "workEdit";
    }

    @PostMapping("/work/edit/{id}")
    public String editWork(@SessionAttribute(required = false) String token, Model model, @PathVariable String id, Work work) throws IOException {


        if (token == null) {
            model.addAttribute("error", "Forbidden");
            return "login";
        }

        String image = workRepository.findById(Integer.parseInt(id)).get().getImage();


        Work work1 = new Work();
        work1.setId(Integer.parseInt(id));
        work1.setPrice(work.getPrice());
        work1.setContent(work.getContent());
        work1.setImage(image);

        workRepository.save(work1);


        return "redirect:/admin/works";
    }

    @GetMapping("/work/add")
    public String getAddWork(@SessionAttribute(required = false) String token, Model model) {


        if (token == null) {
            model.addAttribute("error", "Forbidden");
            return "login";
        }


        return "workAdd";
    }

    @PostMapping("/work/add")
    public String addWork(@SessionAttribute(required = false) String token, Model model, Work work, MultipartFile f) throws IOException {


        if (token == null) {
            model.addAttribute("error", "Forbidden");
            return "login";
        }

        /*File file = new File("C:\\Users\\dav\\Desktop\\test");
        f.transferTo(file);*/

        byte[] array= IOUtils.toByteArray(f.getInputStream());

        String image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(array);


        Work work1 = new Work();
        work1.setPrice(work.getPrice());
        work1.setContent(work.getContent());
        work1.setImage(image);
        workRepository.save(work1);



        return "redirect:/admin/works";
    }

}
