package ru.fast.bills.data.listeners;

import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class CreatedByMediatorEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        try {
            Field[] mediatorFields = FieldUtils.getFieldsWithAnnotation(entity.getClass(), CreatedByMediator.class);
            if (mediatorFields.length == 1) {
                Field mediatorField = mediatorFields[0];
                String serviceName = SecurityContextHolder.getContext().getAuthentication().getName();


                FieldUtils.writeField(mediatorField, entity, serviceName, true);
            }
        } catch (Exception ex) {
            log.error("Can not set mediator for entity {}", entity.getClass());
        }
    }
}
