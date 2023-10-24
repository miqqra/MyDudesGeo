package mydudesgeo.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.category.CreateCategoryDto;
import mydudesgeo.dto.category.PartyCategoryDto;
import mydudesgeo.service.PartyCategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/api/mydudes/geo/category")
public class AdminCategoryController {

    private final PartyCategoryService service;

    @GetMapping
    @Operation(description = "Получение категорий")
    public List<PartyCategoryDto> getCategories() {
        return service.getCategories();
    }

    @PostMapping
    @Operation(description = "Добавление новой категории")
    public PartyCategoryDto createCategory(CreateCategoryDto dto) {
        return service.createCategory(dto);
    }

    @PutMapping
    @Operation(description = "Редактирование категории")
    public PartyCategoryDto updateCategory(@Validated PartyCategoryDto dto) {
        return service.updateCategory(dto);
    }

    @DeleteMapping
    @Operation(description = "Удаление категорий")
    public void deleteCategory(Long id) {
        service.deleteCategory(id);
    }
}
