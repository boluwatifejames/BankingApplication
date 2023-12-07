package org.example.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositMoneyResponse {
    String accountNumber;
    Double deposit;
    String message;


    public DepositMoneyResponse( String accountNumber, Double deposit){
        this.accountNumber=accountNumber;
        this.deposit=deposit;
    }
}
