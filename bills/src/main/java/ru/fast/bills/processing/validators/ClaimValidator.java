package ru.fast.bills.processing.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.stereotype.Component;
import ru.fast.bills.processing.exception.BillsErrorsContainer;
import ru.fast.bills.web.dto.ErrorResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ClaimValidator {

    private final static List<String> ALLOWED_FIELDS = List.of("/title", "/definition");

    public void patchValidate(JsonPatch patch) {
        List<ErrorResponse> errors = new ArrayList<>();
        List<Map<String, String>> mapOfPatch = new ObjectMapper().convertValue(patch, List.class);
        List<String> fieldsForPatching = new java.util.ArrayList<>(mapOfPatch.stream().map(m -> (m.get("path"))).toList());
        fieldsForPatching.removeAll(ALLOWED_FIELDS);
        if (!fieldsForPatching.isEmpty()) {
            fieldsForPatching.forEach(field ->
                    errors.add(new ErrorResponse("path", "Use another request method for change field %s".formatted(field))));
        }

        if (!errors.isEmpty()) {
            throw new BillsErrorsContainer(errors);
        }
    }
}
