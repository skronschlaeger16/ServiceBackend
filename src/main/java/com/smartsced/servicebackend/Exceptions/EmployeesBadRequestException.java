package com.smartsced.servicebackend.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmployeesBadRequestException  extends RuntimeException{
    public EmployeesBadRequestException(String message)
    {
        super(message);
    }
}
