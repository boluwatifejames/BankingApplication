package org.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
     private String senderAccount;
    @Column
    private Double amount ;
    @Column
    private String beneficiaryAccount;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerDetails customerDetails;

    public UserTransfer(String senderAccount, Double amount, String beneficiaryAccount) {
        this.senderAccount = senderAccount;
        this.amount = amount;
        this.beneficiaryAccount = beneficiaryAccount;
    }

}
