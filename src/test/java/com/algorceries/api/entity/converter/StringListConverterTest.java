package com.algorceries.api.entity.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class StringListConverterTest {

    @Test
    void testConvertToDatabaseColumn_emptyList_emptyString() {
        var converter = new StringListConverter();

        var string = converter.convertToDatabaseColumn(Collections.emptyList());

        assertEquals("", string);
    }

    @Test
    void testConvertToDatabaseColumn_filledList_seperatedString() {
        var converter = new StringListConverter();

        var string = converter.convertToDatabaseColumn(List.of("test1", "test2"));

        assertEquals("test1,test2", string);
    }

    @Test
    void testConvertToEntitiyAttribute_emptyString_emptyList() {
        var converter = new StringListConverter();

        var list = converter.convertToEntityAttribute("");

        assertEquals(Collections.emptyList(), list);
    }

    @Test
    void testConvertToEntitiyAttribute_seperatedString_filledList() {
        var converter = new StringListConverter();

        var list = converter.convertToEntityAttribute("test1,test2");

        assertEquals(List.of("test1", "test2"), list);
    }
}
