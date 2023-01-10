package com.capstone.AuthenticationService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "OOPS! No user exist for these credentials.")
public class UserNotFoundException extends Exception{
}
