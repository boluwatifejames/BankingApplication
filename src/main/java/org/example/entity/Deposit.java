package org.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Transfer_Transactions")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerDetails customerDetails;
    @Column(name = "Deposited")
    private Double deposit ;
    @Column(name = "User_Account")
    private String userAccount;
    @Column(name = "Account_Numbers")
    private String accountNumber ;


    public Deposit(String accountNumber, Double deposit){
        this.accountNumber = accountNumber;
        this.deposit = deposit;
    }





}
