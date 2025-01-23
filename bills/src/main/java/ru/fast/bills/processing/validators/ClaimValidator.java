package ru.fast.bills.processing.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.stereotype.Component;
import ru.fast.bills.processing.exception.ValidationError;
import ru.fast.bills.processing.exception.ValidationErrorsContainer;
import ru.fast.bills.web.dto.Claim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ClaimValidator {

    private final static List<String> ALLOWED_FIELDS = List.of("/title", "/definition");

    public void patchValidate(JsonPatch patch) {
        List<ValidationError> errors = new ArrayList<>();
        List<Map<String, String>> mapOfPatch = new ObjectMapper().convertValue(patch, List.class);
        List<String> fieldsForPatching = new java.util.ArrayList<>(mapOfPatch.stream().map(m -> (m.get("path"))).toList());
        fieldsForPatching.removeAll(ALLOWED_FIELDS);
        if (!fieldsForPatching.isEmpty()) {
            fieldsForPatching.forEach(field ->
                    errors.add(ValidationError.of(field, "bills.errors.patch.not_supported", new Object[]{field})));
        }

        if (!errors.isEmpty()) {
            throw new ValidationErrorsContainer(errors);
        }
    }

    public void validateCreate(Claim claim) {
        List<ValidationError> errors = new ArrayList<>();

        if (claim.id() != null) {
            errors.add(ValidationError.of("id", "bills.errors.should_null"));
        }

        if (claim.address().city() == null) {
            errors.add(ValidationError.of("address.city", "bills.errors.required"));
        }

        if (!errors.isEmpty()) {
            throw new ValidationErrorsContainer(errors);
        }
    }
}
