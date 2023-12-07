package org.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Customers")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDetails {
    @Column(name = "AccountNumber")
    private String accountNumber;
    @Column(name = "Customer_Name")
    private String customerName;
    @Column(name = "Balance")
    private Double initialDeposit;
    @OneToOne(mappedBy = "customerDetails",cascade = CascadeType.ALL)
    private Transactions transactions ;
    @OneToOne(mappedBy = "customerDetails")
    private Deposit deposit;
    @OneToOne(mappedBy = "customerDetails")
    private UserTransfer userTransfer;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    public CustomerDetails(String accountNumber, String customerName, Double initialDeposit) {
        this.accountNumber = accountNumber;
        this.customerName =customerName;
        this.initialDeposit = initialDeposit;
    }
    public CustomerDetails(){}
}
