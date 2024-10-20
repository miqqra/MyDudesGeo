package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.user.UpdateUserInfoDto;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/mydudes/geo/user")
public class UserController {

    private final UserService service;

    @GetMapping("/{nickname}")
    @Operation(summary = "Получение информации пользователя")
    public UserDto getInfo(@Parameter(description = "Ник пользователя") @PathVariable String nickname) {
        return service.getInfo(nickname);
    }

    //todo edit or delete
    @PostMapping
    @Operation(summary = "Создание профиля пользователя")
    public UserDto createInfo(@Validated @RequestBody UpdateUserInfoDto dto) {
        return service.createInfo(dto);
    }

    @PutMapping
    @Operation(summary = "Редактирование информации пользователя")
    public UserDto updateInfo(@Validated @RequestBody UpdateUserInfoDto dto) {
        return service.updateInfo(dto);
    }

    @PutMapping(
            path = "/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Изменить фото профиля")
    public void changePhoto(@Parameter(description = "Фото") @RequestParam MultipartFile photo) {
        service.changePhoto(photo);
    }

    @GetMapping(
            path = "/photo/{nickname}",
            produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Получение фото пользователя")
    public ResponseEntity<Resource> getPhoto(@Parameter(description = "Никнейм пользователя") @RequestParam String nickname) {
        byte[] bytes = service.getPhoto(nickname);

        ByteArrayResource resource = new ByteArrayResource(bytes);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=image.jpg");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(bytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @DeleteMapping("/photo")
    @Operation(summary = "Удалить фото профиля")
    public void deletePhoto() {
        service.deletePhoto();
    }
}
