package com.pichincha.prueba.exception;

import org.springframework.beans.factory.annotation.Autowired;

import com.pichincha.prueba.model.enums.LoggerCustomType;
import com.pichincha.prueba.service.imp.LoggerService;

import lombok.Getter;

/**
 * 
 * @author Santiago
 * @apiNote excepción lanzada si rompe con las reglas de negocio
 */
@Getter
public class BusinessLogicException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String messageDebug;

	@Autowired
	private LoggerService logger;

	public BusinessLogicException(String message, String _messageDebug) {
		super(message);
		this.messageDebug = _messageDebug;
	}

	public BusinessLogicException(String codeError, String msgError, String msgDebug, String classNameThrowable,
			String actionThrowable) {
		super(msgError);

		if (logger == null) {
			logger = new LoggerService();
		}

		logger.addAction(actionThrowable)
				.addClassName(classNameThrowable)
				.addCodeStatus(codeError)
				.addExceptionMsg(msgDebug)
				.addMsgUser(msgError)
				.printLog(LoggerCustomType.ERROR);

		this.messageDebug = msgDebug;

	}

	public BusinessLogicException(String codeError, String msgError, Throwable ex) {
		super();

		if (logger == null) {
			logger = new LoggerService();
		}

		logger.addCodeStatus(codeError)
				.addMsgUser(msgError)
				.addExceptionMsg(ex.getMessage())
				.printLog(LoggerCustomType.ERROR);

		this.messageDebug = msgError;

	}

}
