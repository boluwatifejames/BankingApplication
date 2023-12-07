package org.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerException extends RuntimeException{
    private String message;
}
