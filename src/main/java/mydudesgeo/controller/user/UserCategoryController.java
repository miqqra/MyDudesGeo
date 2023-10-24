package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.category.PartyCategoryDto;
import mydudesgeo.service.PartyCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mydudes/geo/category")
public class UserCategoryController {

    private final PartyCategoryService service;

    @GetMapping
    @Operation(description = "Получение категорий")
    public List<PartyCategoryDto> getCategories() {
        return service.getCategories();
    }

}
