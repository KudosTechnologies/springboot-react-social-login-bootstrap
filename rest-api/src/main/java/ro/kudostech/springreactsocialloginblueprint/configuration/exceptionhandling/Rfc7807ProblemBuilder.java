package ro.kudostech.springreactsocialloginblueprint.configuration.exceptionhandling;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import ro.kudostech.springreactsocialloginblueprint.api.server.model.RFC7807ProblemDto;
import ro.kudostech.springreactsocialloginblueprint.api.server.model.ViolationDto;

@UtilityClass
public class Rfc7807ProblemBuilder {
  public static ResponseEntity<RFC7807ProblemDto> buildErrorResponse(
      String detail, HttpStatus httpStatus, WebRequest request, List<ViolationDto> violations) {
    RFC7807ProblemDto problem = RFC7807ProblemDto.builder().build();
    problem.setStatus(httpStatus.value());
    problem.setTitle(httpStatus.name());
    problem.setDetail(detail);
    problem.setInstance(URI.create(request.getDescription(false)));
    problem.setTimestamp(Instant.now());
    problem.setViolations(violations);

    return new ResponseEntity<>(problem, httpStatus);
  }
}
