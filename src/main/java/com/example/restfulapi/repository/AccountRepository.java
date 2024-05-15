package com.example.restfulapi.repository;

import com.example.restfulapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account,Integer> {




    boolean existsByEmail(String string);


}
