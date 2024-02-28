package mydudesgeo.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import mydudesgeo.common.EnumConverter;
import org.aspectj.asm.internal.NameConvertor;
import org.mapstruct.Named;

import java.util.Optional;

public enum Visibility {
    //todo add fully private parties
    FRIENDS,
    ALL,
    CLOSE_FRIENDS;

    @Converter(autoApply = true)
    public static class VisibilityConverter extends EnumConverter<Visibility> {
        public VisibilityConverter() {
            super(Visibility.class, null); //add none
        }
    }
}
