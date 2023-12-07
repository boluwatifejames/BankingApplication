package org.example.exceptions;

import org.example.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CreateCustomerException.class)
    public ResponseEntity<CreateCustomerResponse> transactionException(CreateCustomerException exception) {
        return createApiResponse(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<CreateCustomerResponse> createApiResponse(HttpStatus httpStatus, CreateCustomerException e) {
        return new ResponseEntity<>(
                new CreateCustomerResponse(null,null,null, e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(DepositResponseException.class)
    public ResponseEntity<DepositMoneyResponse> depositException(DepositResponseException exception) {
        return depositApiResponse(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<DepositMoneyResponse> depositApiResponse(HttpStatus httpStatus, DepositResponseException e) {
        return new ResponseEntity<>(
                new DepositMoneyResponse(null,null, e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(TransferException.class)
    public ResponseEntity<TransferResponse> transferException(TransferException exception) {
        return transferApiResponse(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<TransferResponse> transferApiResponse(HttpStatus httpStatus, TransferException e) {
        return new ResponseEntity<>(
                new TransferResponse(null,null, null,e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(WithdrawException.class)
    public ResponseEntity<WithdrawMoneyResponse> withdrawException(WithdrawException exception) {
        return withdrawApiResponse(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<WithdrawMoneyResponse> withdrawApiResponse(HttpStatus httpStatus, WithdrawException e) {
        return new ResponseEntity<>(
                new WithdrawMoneyResponse(null,null, e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(GetAllCustomersException.class)
    public ResponseEntity<GetAllCustomersResponse> withdrawException(GetAllCustomersException exception) {
        return allCustomerApiResponse(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<GetAllCustomersResponse> allCustomerApiResponse(HttpStatus httpStatus, GetAllCustomersException e) {
        return new ResponseEntity<>(
                new GetAllCustomersResponse(null,null,null,null, e.getMessage()),
                httpStatus);
    }

    @ExceptionHandler(StmtOfAcctException.class)
    public ResponseEntity<StatementOfAccountResponse> withdrawException(StmtOfAcctException exception) {
        return stmntApiResponse(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<StatementOfAccountResponse> stmntApiResponse(HttpStatus httpStatus, StmtOfAcctException e) {
        return new ResponseEntity<>(
                new StatementOfAccountResponse(null,null,null,null, e.getMessage()),
                httpStatus);
    }





    }
