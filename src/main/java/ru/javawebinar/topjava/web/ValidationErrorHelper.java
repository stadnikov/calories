package ru.javawebinar.topjava.web;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.javawebinar.topjava.to.BaseTo;

import java.util.stream.Collectors;

public interface ValidationErrorHelper {

    default <T extends BaseTo> ResponseEntity<String> processValidationError(T t, BindingResult result) {
        if (result.hasErrors()) {
            String errorFieldsMsg = result.getFieldErrors().stream()
                    .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                    .collect(Collectors.joining("<br>"));
            return ResponseEntity.unprocessableEntity().body(errorFieldsMsg);
        }
        doAction(t);
        return ResponseEntity.ok().build();
    }

    <T extends BaseTo> void doAction(T t);
}
