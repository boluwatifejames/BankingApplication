package org.example.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name ="Customer_Name")
    private String name;
    @Column(name ="Account_Number")
    private String accountNumber;
    @Column(name = "Total_Credit")
    private Double credit;
    @Column(name= "Total_Debit")
    private Double debit ;
    @Column(name = "Current_Balance")
    private Double balance;

    @OneToOne
    @JoinColumn(name = "customer_id" )
    private CustomerDetails customerDetails;

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public Transactions() {
    }



    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Transactions(Long id, String name, String accountNumber, Double credit, Double debit, Double balance) {
        this.id = id ;
        this.name = name;
        this.accountNumber = accountNumber;
        this.credit = credit;
        this.debit = debit;
        this.balance = balance;


    }
}
