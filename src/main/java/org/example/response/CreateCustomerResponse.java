package org.example.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCustomerResponse {
    String accountNumber;
    String customerName;
    Double initialDeposit;
    String message;

    public CreateCustomerResponse(String accountNumber,String customerName,Double initialDeposit){
        this.accountNumber =accountNumber;
        this.customerName =customerName;
        this.initialDeposit =initialDeposit;

    }
}

