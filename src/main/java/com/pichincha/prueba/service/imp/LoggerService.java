package com.pichincha.prueba.service.imp;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.pichincha.prueba.model.enums.LoggerCustomType;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Primary
@Slf4j
@Getter
public class LoggerService {

	private String className;
	private String action;
	private String msgUser;
	private String exceptionMsg;
	private String codeStatus;

	/**
	 * 
	 * @param action       - Metodo o API que despliega en el log
	 * @param msgUser-     Mensaje que se muestra al usuario
	 * @param exceptionMsg - excepción en caso de error
	 * @param codeStatus   - código de HTTP de respuesta
	 * @return Mensaje estadar para el log
	 */
	public void build(String action, String msgUser, String exceptionMsg, String codeStatus) {
		log.info(String.format("[%s]\nmessage-user: %s\nexception:%s \ncode-status: %s", action, msgUser, exceptionMsg,
				codeStatus));
		cleanFields();
	}

	/**
	 * 
	 * @param action       - Metodo o API que despliega en el log
	 * @param msgUser-     Mensaje que se muestra al usuario
	 * @param exceptionMsg - excepción en caso de error
	 * @param codeStatus   - código de HTTP de respuesta
	 * @return Mensaje estadar para el log
	 */
	public void msgInfo(String action, String msgUser, String exceptionMsg, String codeStatus) {
		log.info(String.format("[%s]\nmessage-user: %s\nexception:%s \ncode-status: %s", action, msgUser, exceptionMsg,
				codeStatus));
		cleanFields();
	}

	/**
	 * @param className    - Clase que imprime el log
	 * @param action       - Metodo o API que despliega en el log
	 * @param msgUser-     Mensaje que se muestra al usuario
	 * @param exceptionMsg - excepción en caso de error
	 * @param codeStatus   - código de HTTP de respuesta
	 * @return Mensaje estadar para el log
	 */
	public void msgInfo(String className, String action, String msgUser, String exceptionMsg, String codeStatus) {
		log.info(String.format("[(%s) %s]\nmessage-user: %s\nexception:%s \ncode-status: %s", className, action,
				msgUser, exceptionMsg, codeStatus));
		cleanFields();
	}

	/**
	 * 
	 * @param action       - Metodo o API que despliega en el log
	 * @param msgUser-     Mensaje que se muestra al usuario
	 * @param exceptionMsg - excepción en caso de error
	 * @param codeStatus   - código de HTTP de respuesta
	 * @return Mensaje estadar para el log
	 */
	public void msgWarn(String action, String msgUser, String exceptionMsg, String codeStatus) {
		log.warn(String.format("[%s]\nmessage-user: %s\nexception:%s \ncode-status: %s", action, msgUser, exceptionMsg,
				codeStatus));
		cleanFields();
	}

	/**
	 * 
	 * @param action       - Metodo o API que despliega en el log
	 * @param msgUser-     Mensaje que se muestra al usuario
	 * @param exceptionMsg - excepción en caso de error
	 * @param codeStatus   - código de HTTP de respuesta
	 * @return Mensaje estadar para el log
	 */
	public void msgWarn(String className, String action, String msgUser, String exceptionMsg, String codeStatus) {
		log.warn(String.format("[(%s) %s]\nmessage-user: %s\nexception:%s \ncode-status: %s", className, action,
				msgUser, exceptionMsg,
				codeStatus));
		cleanFields();
	}

	/**
	 * 
	 * @param action       - Metodo o API que despliega en el log
	 * @param msgUser-     Mensaje que se muestra al usuario
	 * @param exceptionMsg - excepción en caso de error
	 * @param codeStatus   - código de HTTP de respuesta
	 * @return Mensaje estadar para el log
	 */
	public void buildError(String action, String msgUser, String exceptionMsg, String codeStatus) {
		log.error(String.format("[%s]\nmessage-user: %s\nexception:%s \ncode-status: %s", action, msgUser, exceptionMsg,
				codeStatus));
		cleanFields();
	}

	/**
	 * 
	 * @param action       - Metodo o API que despliega en el log
	 * @param msgUser-     Mensaje que se muestra al usuario
	 * @param exceptionMsg - excepción en caso de error
	 * @param codeStatus   - código de HTTP de respuesta
	 * @return Mensaje estadar para el log
	 */
	public void buildError(String className, String action, String msgUser, String exceptionMsg, String codeStatus) {
		log.error(String.format("(%s) [%s]\nmessage-user: %s\nexception:%s \ncode-status: %s", className, action,
				msgUser, exceptionMsg, codeStatus));
		cleanFields();
	}

	/**
	 * 
	 * @param action       - Metodo o API que despliega en el log
	 * @param msgUser-     Mensaje que se muestra al usuario
	 * @param exceptionMsg - excepción en caso de error
	 * @param codeStatus   - código de HTTP de respuesta
	 * @return Mensaje estadar para el log
	 */
	public void buildDebug(String action, String msgUser, String exceptionMsg, String codeStatus) {
		log.debug(String.format("[%s]\nmessage-user: %s\nexception:%s \ncode-status: %s", action, msgUser, exceptionMsg,
				codeStatus));
		cleanFields();
	}

	/**
	 * 
	 * @param action       - Metodo o API que despliega en el log
	 * @param msgUser-     Mensaje que se muestra al usuario
	 * @param exceptionMsg - excepción en caso de error
	 * @param codeStatus   - código de HTTP de respuesta
	 * @return Mensaje estadar para el log
	 */
	public void buildDebug(String className, String action, String msgUser, String exceptionMsg, String codeStatus) {
		log.debug(String.format("[(%s) %s]\nmessage-user: %s\nexception:%s \ncode-status: %s", className, action,
				msgUser, exceptionMsg,
				codeStatus));
		cleanFields();
	}

	public LoggerService addClassName(String className) {
		this.className = className;
		return this;
	}

	public LoggerService addAction(String action) {
		this.action = action;
		return this;
	}

	public LoggerService addMsgUser(String msgUser) {
		this.msgUser = msgUser;
		return this;
	}

	public LoggerService addExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
		return this;
	}

	public LoggerService addCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
		return this;
	}

	public void printLog(LoggerCustomType type) {
		switch (type) {
			case INFO:
				this.msgInfo(className, action, msgUser, exceptionMsg, codeStatus);
				break;
			case DEBUG:
				this.buildDebug(className, action, msgUser, exceptionMsg, codeStatus);
				break;
			case WARN:
				this.msgWarn(className, action, msgUser, exceptionMsg, codeStatus);
				break;
			case ERROR:
				this.buildError(className, action, msgUser, exceptionMsg, codeStatus);
				break;
			default:
				this.msgInfo(className, action, msgUser, exceptionMsg, codeStatus);
				break;
		}
	}

	private void cleanFields() {
		this.action = "";
		this.className = "";
		this.codeStatus = "";
		this.exceptionMsg = "";
		this.msgUser = "";
	}

}
