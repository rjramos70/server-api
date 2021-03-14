package com.company.connectionmanager.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.company.connectionmanager.domain.exception.BusinessException;
import com.company.connectionmanager.domain.exception.ConnectorNotFoundException;
import com.company.connectionmanager.domain.exception.SchemaNotFoundException;
import com.company.connectionmanager.domain.exception.TableNotFoundException;

@ControllerAdvice
public class ApiExceptionHandlers extends ResponseEntityExceptionHandler {

	public static final String GENERIC_CLIENT_ERRO_MSG = "An unexpected internal system error has occurred. "
			+ "Try again and if the problem persists, contact your system administrator.";

	
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;

		String detail = ex.getMessage();
				

		Problem problem = createProblemBuilder(status, problemType, detail).userMassage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.INVALID_DATA;

		String detail = "One or more fields are invalid. Fill in these correctly and try again.";

		// get a list of fields with problem
		BindingResult bindingResult = ex.getBindingResult();

		List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream().map(fieldError -> Problem.Field
				.builder().name(fieldError.getField()).userMessage(fieldError.getDefaultMessage()).build())
				.collect(Collectors.toList());
		Problem problem = createProblemBuilder(status, problemType, detail).userMassage(detail).fields(problemFields)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		ProblemType problemType = ProblemType.BUSINESS_RULE_ERROR; // business rule error

		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).userMassage(GENERIC_CLIENT_ERRO_MSG)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}

	@ExceptionHandler(ConnectorNotFoundException.class)
	public ResponseEntity<?> handleConnectionNotFound(ConnectorNotFoundException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;

		String detail = ex.getMessage();
				

		Problem problem = createProblemBuilder(status, problemType, detail).userMassage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}

	@ExceptionHandler(TableNotFoundException.class)
	public ResponseEntity<?> handleTableNotFound(TableNotFoundException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;

		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMassage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}
	
	@ExceptionHandler(SchemaNotFoundException.class)
	public ResponseEntity<?> handleSchemaNotFound(SchemaNotFoundException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;

		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMassage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		ProblemType problemType = ProblemType.ARGUMENT_TYPE_MISMATCH;

		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMassage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value())
					.timestamp(LocalDateTime.now()).userMassage(GENERIC_CLIENT_ERRO_MSG).build();
		} else if (body instanceof String) {
			body = Problem.builder().title((String) body).status(status.value()).timestamp(LocalDateTime.now())
					.userMassage(GENERIC_CLIENT_ERRO_MSG).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail).timestamp(LocalDateTime.now());
	}

}
