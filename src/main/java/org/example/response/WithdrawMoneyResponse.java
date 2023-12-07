package org.example.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WithdrawMoneyResponse {
    private String accountNumber;
    private Double withdraw;
    private String message;


    public WithdrawMoneyResponse(String accountNumber ,Double withdraw){
        this.accountNumber =accountNumber;
        this.withdraw=withdraw;
    }
}
