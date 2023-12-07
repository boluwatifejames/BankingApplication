package org.example.repository;


import org.example.entity.CustomerDetails;
import org.example.response.GetAllCustomersResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepostory extends CrudRepository<CustomerDetails , Long> {
   CustomerDetails findByAccountNumber(String accountNumber);
   CustomerDetails findByCustomerName(String customerName);

   List<CustomerDetails> findAll();

}

