package org.example.patientservice.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectUtils {
    public static <T> String[] getNullPropertyNames(T dto) {
        List<String> nullProperties = new ArrayList<>();
        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(dto) == null) {
                    nullProperties.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                // Handle access exception (unlikely but good practice)
            }
        }
        return nullProperties.toArray(new String[0]);
    }
}
