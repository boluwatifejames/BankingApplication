package org.example.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferResponse {
    String senderAccount;
    Double amount;
    String beneficiaryAccount;
    String message ;

    public TransferResponse(String senderAccount ,Double amount,String beneficiaryAccount){
        this.senderAccount=senderAccount;
        this.amount=amount;
        this.beneficiaryAccount=beneficiaryAccount;
    }
}
