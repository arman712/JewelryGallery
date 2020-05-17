package com.company.handmade.service;

import com.company.handmade.model.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    Admin findByUsernameAndPassword(String username, String password);

}
