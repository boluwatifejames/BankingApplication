package org.example.repository;

import org.example.entity.CustomerDetails;
import org.example.entity.Transactions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TransactionsRepository extends CrudRepository<Transactions,Long> {
  @Query("SELECT NEW Map(t.id as id, t.name as customer_name, t.accountNumber as account_number, t.credit as total_credit, t.debit as total_debit, t.balance as current_balance) FROM Transactions t WHERE t.accountNumber = :accountNumber")
  List<Map<String, Object>> findByAccountNumber(String accountNumber);
    @Query("SELECT t FROM Transactions t WHERE t.accountNumber = :accountNumber")
    Transactions findByAccountNumberA(String accountNumber);
}
