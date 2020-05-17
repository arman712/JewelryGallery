package com.company.handmade.repository;

import com.company.handmade.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work,Integer> {


    void deleteById(int id);

}
