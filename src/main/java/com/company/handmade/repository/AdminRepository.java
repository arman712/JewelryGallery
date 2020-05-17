package com.company.handmade.repository;

import com.company.handmade.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsernameAndPassword(String username, String password);
    void deleteById(int id);

}
