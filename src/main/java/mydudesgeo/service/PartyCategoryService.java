package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.dataservice.PartyCategoryDataService;
import mydudesgeo.dto.category.CreateCategoryDto;
import mydudesgeo.dto.category.PartyCategoryDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyCategoryMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyCategoryService {

    private final PartyCategoryDataService dataService;

    private final PartyCategoryMapper mapper;

    public List<PartyCategoryDto> getCategories() {
        return Optional.of(dataService.getCategories())
                .map(mapper::toDto)
                .orElse(Collections.emptyList());
    }

    public PartyCategoryDto createCategory(CreateCategoryDto dto) {
        if (dataService.existsByName(dto.getCategory())) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Категория с таким именем уже существует");
        }

        return Optional.of(dto)
                .map(mapper::toModel)
                .map(dataService::createCategory)
                .map(mapper::toDto)
                .orElse(null);
    }

    public PartyCategoryDto updateCategory(PartyCategoryDto dto) {
        if (dataService.existsById(dto.getId())) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Такой категории не существует");
        }

        if (dataService.existsByName(dto.getCategory())) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Категория с таким именем уже существует");
        }

        return Optional.of(dto)
                .map(mapper::toModel)
                .map(dataService::updateCategory)
                .map(mapper::toDto)
                .orElse(null);
    }

    public void deleteCategory(Long id) {
        if (dataService.existsById(id)) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Такой категории не существует");
        }

        dataService.deleteCategory(id);
    }
}
