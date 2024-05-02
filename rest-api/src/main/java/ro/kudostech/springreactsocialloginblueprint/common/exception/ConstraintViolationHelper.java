package ro.kudostech.springreactsocialloginblueprint.common.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import ro.kudostech.springreactsocialloginblueprint.api.server.model.ViolationDto;

@UtilityClass
public class ConstraintViolationHelper {
  public List<ViolationDto> createViolations(Set<ConstraintViolation<?>> violations) {
    return violations.stream()
        .map(ConstraintViolationHelper::createViolation)
        .collect(Collectors.toList());
  }

  public static <T> ConstraintViolation<T> buildConstraintViolation(
      final String interpolatedMessage, final Class<T> clazz, final String path) {

    return ConstraintViolationImpl.forParameterValidation(
        null,
        null,
        null,
        interpolatedMessage,
        clazz,
        null,
        null,
        null,
        pathFromString(path),
        null,
        null,
        null);
  }

  private ViolationDto createViolation(ConstraintViolation<?> constraintViolation) {
    final String fieldName = constraintViolation.getPropertyPath().toString();
    return ViolationDto.builder()
        .field(fieldName)
        .message(constraintViolation.getMessage())
        .build();
  }

  private static Path pathFromString(final String nodeName) {
    final PathImpl path = PathImpl.createRootPath();
    path.addPropertyNode(nodeName);
    return path;
  }
}
