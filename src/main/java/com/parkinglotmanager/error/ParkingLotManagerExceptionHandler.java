package com.parkinglotmanager.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.parkinglotmanager.ErrorType;

/**
 * Handles specific errors differently.
 * Internal, custom errors are built here.
 * 
 * @author Mat
 *
 */
@ControllerAdvice
public class ParkingLotManagerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ErrorType error = new ErrorType().error("Unexepected error occured");
		ex.printStackTrace();
		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ ParkingLotManagerException.class })
	ResponseEntity<Object> handleCustomException(ParkingLotManagerException plmex, WebRequest request) {
		ErrorType error = new ErrorType().error(plmex.getMessage());
		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
