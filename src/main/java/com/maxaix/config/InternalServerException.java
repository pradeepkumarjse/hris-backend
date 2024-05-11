package com.maxaix.config;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class InternalServerException extends RuntimeException {

	private static final long serialVersionUID = 5082350461240984517L;

	public InternalServerException(String message) {
		super(message);
	}
	
	// to include stack trace into message
	private String customMessage;
	public InternalServerException(String message, Exception ex) {
		super(message);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		this.customMessage = message.concat(", ") + stringWriter.getBuffer().toString();
		printWriter.close();
	}
	@Override
	public String getMessage() {
		return customMessage == null ? super.getMessage() : customMessage;
	}
}
