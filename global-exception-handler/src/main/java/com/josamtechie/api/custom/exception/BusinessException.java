package com.josamtechie.api.custom.exception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class BusinessException extends RuntimeException{
	

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMessage;

}
