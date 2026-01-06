package com.algorceries.api.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.lang.NonNull;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SEPERATOR = ",";

    @Override
    public String convertToDatabaseColumn(@NonNull List<String> entityList) {
        return String.join(SEPERATOR, entityList);
    }

    @Override
    public List<String> convertToEntityAttribute(@NonNull String dbString) {
        return dbString.isBlank() ? Collections.emptyList() : Arrays.asList(dbString.split(SEPERATOR));
    }
}
