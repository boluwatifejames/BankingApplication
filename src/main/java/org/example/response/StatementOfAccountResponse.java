package org.example.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatementOfAccountResponse {
    Long id;
    String customerName;
    String accountNumber;
    Double accountBalance ;
    String message;

    public StatementOfAccountResponse(Long id,String customerName,String accountNumber,Double accountBalance ){
        this.id=id;
        this.customerName=customerName;
        this.accountNumber=accountNumber;
        this.accountBalance=accountBalance;
    }
}
