package mydudesgeo.common;

import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.util.Optional;

@RequiredArgsConstructor
public class EnumConverter<T extends Enum<T>> implements AttributeConverter<T, String> {
    private final Class<T> enumClass;
    private final T defaultValue;

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return Optional.ofNullable(attribute)
                .map(Enum::name)
                .orElse(null);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(v -> EnumUtils.getEnum(enumClass, v))
                .orElse(defaultValue);
    }
}
