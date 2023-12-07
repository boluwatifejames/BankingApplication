package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.authentication.Roles;
import org.example.authentication.Users;
import org.example.config.JwtService;
import org.example.entity.*;
import org.example.exceptions.*;
import org.example.repository.*;
import org.example.response.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ApplicationImplService implements ApplicationService{
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepostory customerRepostory ;
    private final TransactionsRepository transactionsRepository;
    private final WithdrawalRepository withdrawalRepo;
    private final DepositRepository depositRepository;


@Override
    public CreateCustomerResponse createNewCustomer(CustomerDetails customerDetails){
    try {
        Users users = getCurrentUser();
        if (users.getRole().name().equals("USER")) {
            Double initialDeposit = customerDetails.getInitialDeposit();
            String accountNumber = RandomNumGenerator();
            customerDetails.setAccountNumber(accountNumber);
            customerDetails.setCustomerName(customerDetails.getCustomerName());
//        customerDetails.setInitalDeposit(500.00);
            if (customerRepostory.findByAccountNumber(accountNumber) != null || customerRepostory.findByCustomerName(customerDetails.getCustomerName()) != null) {
                throw new IllegalArgumentException("Account already exists");
            }
            if (initialDeposit < 500) {
                throw new IllegalArgumentException("Initial Deposit Must be up to 500 Naira");
            }
            CustomerDetails savedDetails = customerRepostory.save(customerDetails);
            Transactions transactions = new Transactions();
            transactions.setCustomerDetails(savedDetails);
            transactions.setName(customerDetails.getCustomerName());
            transactions.setAccountNumber(accountNumber);
            transactions.setCredit(0.0);
            transactions.setDebit(0.0);
            transactions.setBalance(customerDetails.getInitialDeposit());
            transactionsRepository.save(transactions);
            return new CreateCustomerResponse(accountNumber, customerDetails.getCustomerName(), initialDeposit);
        }
        else {
            return new CreateCustomerResponse(null, null, null, "Only users can create customers.");
        }
    }
    catch (Exception e){
        throw new CreateCustomerException(e.getMessage());
    }
    }


    private String RandomNumGenerator ()
    {
        Random rand = new Random();
        StringBuilder acctNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = rand.nextInt(10);
            acctNumber.append(digit);
        }
        String acctNum = acctNumber.toString();
        return acctNum;
    }

    @Override
    public WithdrawMoneyResponse withdraw(String accountNumber, Withdraw withdraw) {
        try {
            Users users = getCurrentUser();
            if (users.getRole().name() == "USER") {
                CustomerDetails customer = customerRepostory.findByAccountNumber(accountNumber);
                CustomerDetails balanceChecker = customerRepostory.findByAccountNumber(accountNumber);
                if (customer == null) {
                    throw new IllegalArgumentException("Account Not Found");
                }
                Double withdrawAmount = withdraw.getWithdraw();
                if (!withdrawalChecker(withdrawAmount)) {
                    throw new IllegalArgumentException("Invalid amount");
                }
                if (withdrawAmount > balanceChecker.getInitialDeposit()) {
                    throw new IllegalArgumentException("Insufficient Funds");
                }
                if (balanceChecker.getInitialDeposit() == 500 && withdrawAmount > 0) {
                    throw new IllegalArgumentException("Unable to withdraw base Aamount");
                }
                Double balCalculator = balanceChecker.getInitialDeposit() - withdrawAmount;
                if (balCalculator < 500) {
                    throw new IllegalArgumentException("Error : Minimum Balance left must be at least 500 Naira");
                }
                Transactions transactions = transactionsRepository.findByAccountNumberA(accountNumber);
                Double currentBalance = customer.getInitialDeposit();
                Double updatedBalance = currentBalance - withdrawAmount;
                customer.setInitialDeposit(updatedBalance);
                transactions.setBalance(updatedBalance);
                Double debit = transactions.getDebit() + withdrawAmount;
                transactions.setDebit(debit);
                customerRepostory.save(customer);
                transactionsRepository.save(transactions);

                withdraw.setAccountNumber(accountNumber);
                withdraw.setUserAccount("Active");
                //  depositsAndWithdrawals.setCustomerDetails(customer.getId());
                withdrawalRepo.save(withdraw);
                return new WithdrawMoneyResponse(accountNumber, withdrawAmount);


            }

       else{
           return new WithdrawMoneyResponse(null,null,"Only users can access this endpoint");
            }  }

            catch (Exception e) {
            throw new WithdrawException(e.getMessage());
        }
    }
@Override
    public DepositMoneyResponse deposit(String accountNumber, Deposit deposit){
    try{
    Users users = getCurrentUser();
    if(users.getRole().name()=="USER") {
        CustomerDetails customer = customerRepostory.findByAccountNumber(accountNumber);
        if (customer == null) {
            throw new IllegalArgumentException("Account Not Found");
        }
        Double depositAmount = deposit.getDeposit();
        if (!depositChecker(depositAmount)) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }
        Transactions transactions = transactionsRepository.findByAccountNumberA(accountNumber);
        Double currentBalance = customer.getInitialDeposit();
        Double updatedBalance = currentBalance + depositAmount;
        customer.setInitialDeposit(updatedBalance);
        transactions.setBalance(updatedBalance);
        Double cred = transactions.getCredit() + depositAmount;
        transactions.setCredit(cred);
        customerRepostory.save(customer);
        transactionsRepository.save(transactions);
        deposit.setAccountNumber(accountNumber);
        deposit.setUserAccount("Active");
//        depositsAndWithdrawals.setCustomerDetails(customer);
         depositRepository.save(deposit);
         return new DepositMoneyResponse(accountNumber,depositAmount);
    }
     else {
         return new DepositMoneyResponse(null,null,"Only users can access this endpoint");
     }
    }
    catch (Exception e){
        throw new CreateCustomerException(e.getMessage());
    }
    }

    private boolean depositChecker(Double amount){
        if(amount<10||Double.isNaN(amount)||Double.isInfinite(amount)||amount>1000000){
            return false ;
        }
        return true;
    }


    private boolean withdrawalChecker(Double amount){
        if(amount<10||Double.isNaN(amount)||Double.isInfinite(amount)||amount>1000000){
            return false ;
        }
        return true;
    }



@Override
    public TransferResponse transferFunds(UserTransfer userTransfer) {
    try {
        Users users = getCurrentUser();
        if (users.getRole().name() == "USER") {

            CustomerDetails fundsChecker = customerRepostory.findByAccountNumber(userTransfer.getSenderAccount());

            if (!isSenderAccountValid(userTransfer.getSenderAccount())) {
                throw new IllegalArgumentException("Account Number / Account :  " +" "+ userTransfer.getSenderAccount() + "does not exist");
            }
            if(!isReceiverAccountValid(userTransfer.getBeneficiaryAccount())){
                throw new IllegalArgumentException("Account Number / Account : " +" "+ userTransfer.getBeneficiaryAccount() + "does not exist");
            }

            if (!isAmountValid(userTransfer.getAmount())) {
                throw new IllegalArgumentException("Amount : " + userTransfer.getAmount() + " is invalid  ");
            }
            if (userTransfer.getAmount() > fundsChecker.getInitialDeposit()) {
                throw new IllegalArgumentException("Insufficient Funds");
            }
            Double amount = userTransfer.getAmount();
            if (!isbeneficiaryAccountValid(userTransfer.getBeneficiaryAccount())) {
                throw new IllegalArgumentException("Account Number / Account : " + userTransfer.getSenderAccount() + "does not exist");
            }
            Double balCalculator = fundsChecker.getInitialDeposit() - userTransfer.getAmount();
            if (balCalculator < 500) {
                throw new IllegalArgumentException("Insufficient funds , Minimum balance left must be at least 500 Naira");
            }
            //Customers Table
            CustomerDetails senderDetails = customerRepostory.findByAccountNumber(userTransfer.getSenderAccount());
            CustomerDetails receiverDetails = customerRepostory.findByAccountNumber(userTransfer.getBeneficiaryAccount());
            Double senderBalance = senderDetails.getInitialDeposit();
            Double beneficiaryBalance = receiverDetails.getInitialDeposit();
            senderDetails.setInitialDeposit(senderBalance - amount);
            receiverDetails.setInitialDeposit(beneficiaryBalance + amount);
            customerRepostory.save(senderDetails);
            customerRepostory.save(receiverDetails);

            /// Transactions Table
            Transactions sendersDebitDetails = transactionsRepository.findByAccountNumberA(userTransfer.getSenderAccount());
            Transactions receiversCreditDetails = transactionsRepository.findByAccountNumberA(userTransfer.getBeneficiaryAccount());
            receiversCreditDetails.setBalance(receiverDetails.getInitialDeposit());
            sendersDebitDetails.setBalance(senderDetails.getInitialDeposit());
            Double updatedBalanceD = sendersDebitDetails.getDebit() + amount;
            Double updatedBalanceC = receiversCreditDetails.getCredit() + amount;
            sendersDebitDetails.setDebit(updatedBalanceD);
            receiversCreditDetails.setCredit(updatedBalanceC);

            transactionsRepository.save(sendersDebitDetails);
            transactionsRepository.save(receiversCreditDetails);
            ///
            return new TransferResponse(userTransfer.getSenderAccount(), amount, userTransfer.getBeneficiaryAccount());
        } else {
            return new TransferResponse(null,null,null,"Only users can access this endpoint");
        }
    }
    catch (Exception e){
        throw new TransferException(e.getMessage());
    }
    }


    private boolean isSenderAccountValid(String accountNumber){

        CustomerDetails customer = customerRepostory.findByAccountNumber(accountNumber);
        if(customer == null){
            return  false;
        }
        return true ;
    }
    private boolean isReceiverAccountValid(String accountNumber){

        CustomerDetails customer = customerRepostory.findByAccountNumber(accountNumber);
        if(customer == null){
            return  false;
        }
        return true ;
    }

    private boolean isAmountValid(Double amount){
        if(amount<10||Double.isNaN(amount)||Double.isInfinite(amount)||amount>1000000){
            return false ;
        }
        return true;
    }

    private boolean isbeneficiaryAccountValid(String accountNumber ){

        CustomerDetails customer = customerRepostory.findByAccountNumber(accountNumber);
        if(customer == null){
            return false ;
        }
        return true ;
    }
    @Override
    public StatementOfAccountResponse getTransactionStatement(String accountNumber) {
        try {

            Users users = getCurrentUser();
            if (users.getRole().name().equals("USER")) {
                CustomerDetails customer = customerRepostory.findByAccountNumber(accountNumber);
                if (customer == null) {
                    throw new IllegalArgumentException("Account Number Doesn't Exist");
                }
                List<Map<String, Object>> transactions = transactionsRepository.findByAccountNumber(accountNumber);
                StatementOfAccountResponse response = new StatementOfAccountResponse();
                response.setId(customer.getId());
                response.setAccountNumber(accountNumber);
                response.setAccountBalance(customer.getInitialDeposit());
                response.setCustomerName(customer.getCustomerName());
                return response;
            } else {
                throw new AccessDeniedException("Only Users Can Access This Endpoint");
            }
        }
        catch (Exception e){throw new StmtOfAcctException(e.getMessage());}
    }




    @Override
    public List<GetAllCustomersResponse> allcustomers() {
    try {

        Users users = getCurrentUser();
        if ("ADMIN".equals(users.getRole().name())) {
            List<CustomerDetails> customerDetailsList = customerRepostory.findAll();
            return customerDetailsList.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } else {
            throw new AccessDeniedException("Only Admin Can Access this Endpoint");
        }
    }
    catch (Exception e){
        throw new GetAllCustomersException(e.getMessage());
    }
    }

    private GetAllCustomersResponse mapToResponse(CustomerDetails customerDetails) {
        GetAllCustomersResponse response = new GetAllCustomersResponse();
        response.setId(customerDetails.getId());
        response.setAccountNumber(customerDetails.getAccountNumber());
        response.setCustomerName(customerDetails.getCustomerName());
        response.setAccountBalance(customerDetails.getInitialDeposit());
        return response;
    }


    @Override
    public Users registerUser(RegisterUser request) {
     var user = Users.builder()
             .firstName(request.getFirstName())
             .lastName(request.getLastName())
             .email(request.getEmail())
             .password(encoder.encode(request.getPassword()))
             .role(Roles.USER)
             .build();
     user = userRepo.save(user);
     return user;
    }

     @Override
    public AuthenticationResponse authenticateAndGetToken(AuthenticateUserRequest authenticateUserRequest) {
         Authentication authentication =  authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken
                         (
                         authenticateUserRequest.getEmail(),
                         authenticateUserRequest.getPassword())

         );
         SecurityContextHolder.getContext().setAuthentication(authentication);
         String token = jwtService.generateToken(authentication);
         return AuthenticationResponse.builder()
                 .token(token)
                 .build();
    }
    @Transactional(readOnly = true)
    private Users getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("No User not found with such email"));
    }

    @Override
    public AuthAdminResponse authenticateAdmin(AuthAdminRequest authAdminRequest) {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authAdminRequest.getEmail(),
              authAdminRequest.getPassword()));
     // log.info();
      SecurityContextHolder.getContext().setAuthentication(authentication);
      final String token = jwtService.generateToken(authentication);
      return AuthAdminResponse.builder()
              .token(token)
              .build();
    }



}

