package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.partyrole.CreatePartyRoleDto;
import mydudesgeo.dto.partyrole.PartyRoleDto;
import mydudesgeo.service.PartyRoleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mydudes/geo/party/roles")
public class PartyRoleController {

    //todo сделать валидацию на организатора
    private final PartyRoleService service;

    @PostMapping("/create")
    @Operation(summary = "Создание роли в мероприятии")
    public PartyRoleDto createRole(@RequestBody CreatePartyRoleDto dto) {
        return service.createRole(dto);
    }

    @PutMapping("/update")
    @Operation(summary = "Изменение роли в мероприятии")
    public PartyRoleDto updateRole(@RequestBody PartyRoleDto dto) {
        return service.updateRole(dto);
    }

    @DeleteMapping("/{roleId}")
    @Operation(summary = "Удаление роли из мероприятия")
    public void deleteRole(@Parameter(description = "Id роли") @PathVariable Long roleId) {
        service.deleteRole(roleId);
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "Получение роли")
    public PartyRoleDto getRole(@Parameter(description = "Id роли") @PathVariable Long roleId) {
        return service.getRole(roleId);
    }

    @GetMapping
    @Operation(summary = "Получение всех ролей мероприятия")
    public List<PartyRoleDto> getRoles(@Parameter(description = "Id мероприятия") @RequestParam Long partyId) {
        return service.getRoles(partyId);
    }

    @PostMapping("/user/{roleId}/{username}")
    @Operation(summary = "Добавление пользователя в роль")
    public void addUserToRole(@Parameter(description = "Id роли") @PathVariable Long roleId,
                              @Parameter(description = "Никнейм пользователя") @PathVariable String username) {
        service.addUserToRole(roleId, username);
    }

    @DeleteMapping("user/{roleId}/{username}")
    @Operation(summary = "Удаление пользователя из роли")
    public void deleteUserFromRole(@Parameter(description = "Id роли") @PathVariable Long roleId,
                                   @Parameter(description = "Никнейм пользователя") @PathVariable String username) {
        service.deleteUserFromRole(roleId, username);
    }

    @GetMapping("user/{roleId}")
    @Operation(summary = "Получение пользователей из роли")
    public List<String> getUsersByRole(@Parameter(description = "Id роли") @PathVariable Long roleId) {
        return service.getUsersByRole(roleId);
    }

}
