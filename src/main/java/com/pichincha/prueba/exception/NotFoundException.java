package com.pichincha.prueba.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String messageDebug;
	private HttpStatus status;
	
	
	public NotFoundException(String message,  String _messageDebug, HttpStatus status) {
		super(message);
		this.messageDebug = _messageDebug;
		this.status = status;
	}
	
	
}
