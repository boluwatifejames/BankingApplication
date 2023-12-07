package org.example.controller;


//import org.example.config.JwtService;

import org.example.entity.CustomerDetails;
import org.example.entity.Deposit;
import org.example.entity.UserTransfer;
import org.example.entity.Withdraw;
import org.example.response.*;
import org.example.service.ApplicationImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RequestsController {
//    @Autowired
//    private JwtService jwtService;

    @Autowired
    private ApplicationImplService applicationImplService;



    //THE CONTROLLER
    @PostMapping("neptune/createacctnum")
    public ResponseEntity<CreateCustomerResponse> createNewCustomer(@RequestBody CustomerDetails customerDetails){
      return ResponseEntity.status(HttpStatus.OK).body(applicationImplService.createNewCustomer(customerDetails));
    }


    @GetMapping("neptune/retreivestatement")
    public ResponseEntity<StatementOfAccountResponse> retrieveStatementOfAccount(@RequestParam String accountNumber){
             return ResponseEntity.status(HttpStatus.OK).body(applicationImplService.getTransactionStatement(accountNumber));
    }


    @GetMapping("/neptune/allcustomers")
    public ResponseEntity<List<GetAllCustomersResponse>> allCustomers(){
            List<GetAllCustomersResponse> statement2 = applicationImplService.allcustomers();
            return ResponseEntity.ok(statement2);
    }


//    @PostMapping("/neptune/user/register")
//    public ResponseEntity<?> registerUser(@RequestBody RegisterUser registerRequest){
//       return ResponseEntity.ok(applicationImplSevice.registerUser(registerRequest));
//    }
//
//
//    @PostMapping("/neptune/user/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticateAndGetToken(@RequestBody AuthenticateUserRequest authenticateUserRequest){
//        return ResponseEntity.ok(applicationImplSevice.authenticateAndGetToken(authenticateUserRequest));
//    }



    @PostMapping("/neptune/deposit")
    public ResponseEntity<DepositMoneyResponse> depositMoney(@RequestParam String accountNumber, @RequestBody Deposit deposit) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationImplService.deposit(accountNumber, deposit));
    }



    @PostMapping("/neptune/withdraw")
    private ResponseEntity<WithdrawMoneyResponse> withdrawMoney(@RequestParam String accountNumber , @RequestBody Withdraw withdraw){
   return ResponseEntity.status(HttpStatus.OK).body(applicationImplService.withdraw(accountNumber, withdraw));
    }

    @PostMapping("/neptune/transfer")
    public ResponseEntity<TransferResponse> transferFund(@RequestBody UserTransfer userTransfer){
       return ResponseEntity.status(HttpStatus.OK).body(applicationImplService.transferFunds(userTransfer));
    }
}
