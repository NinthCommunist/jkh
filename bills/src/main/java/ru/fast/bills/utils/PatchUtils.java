package ru.fast.bills.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.SneakyThrows;

public class PatchUtils {

    @SneakyThrows
    public static <C> C applyPatchToCustomer(
            JsonPatch patch, C targetCustomer) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return (C) objectMapper.treeToValue(patched, targetCustomer.getClass());
    }
}
