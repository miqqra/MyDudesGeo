package mydudesgeo.mapper;

import mydudesgeo.dto.category.CreateCategoryDto;
import mydudesgeo.dto.category.PartyCategoryDto;
import mydudesgeo.entity.PartyCategory;
import mydudesgeo.model.PartyCategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class PartyCategoryMapper {

    public abstract PartyCategoryModel toModel(PartyCategory source);

    @Mapping(target = "id", ignore = true)
    public abstract PartyCategoryModel toModel(CreateCategoryDto source);

    public abstract PartyCategoryModel toModel(PartyCategoryDto source);

    public abstract PartyCategory toEntity(PartyCategoryModel source);

    public abstract PartyCategoryDto toDto(PartyCategoryModel source);

    public abstract List<PartyCategoryDto> toDto(List<PartyCategoryModel> source);

}
