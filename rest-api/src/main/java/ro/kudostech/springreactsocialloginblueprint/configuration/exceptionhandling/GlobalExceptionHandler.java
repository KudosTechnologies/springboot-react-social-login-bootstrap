package ro.kudostech.springreactsocialloginblueprint.configuration.exceptionhandling;

import static ro.kudostech.springreactsocialloginblueprint.configuration.exceptionhandling.Rfc7807ProblemBuilder.buildErrorResponse;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ro.kudostech.springreactsocialloginblueprint.api.server.model.RFC7807ProblemDto;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<RFC7807ProblemDto> handleAllUncaughtExceptions(
      Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return buildErrorResponse(
        ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request, List.of());
  }
}
