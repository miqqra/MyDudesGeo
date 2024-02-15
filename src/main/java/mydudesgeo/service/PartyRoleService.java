package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.dataservice.PartyDataService;
import mydudesgeo.dataservice.PartyRoleDataService;
import mydudesgeo.dto.partyrole.CreatePartyRoleDto;
import mydudesgeo.dto.partyrole.PartyRoleDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyRoleMapper;
import mydudesgeo.model.PartyModel;
import mydudesgeo.model.PartyRoleModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyRoleService {

    private final PartyRoleDataService dataService;
    private final PartyDataService partyDataService;

    private final PartyRoleMapper mapper;

    public PartyRoleDto createRole(CreatePartyRoleDto dto) {
        PartyModel party = Optional.of(dto)
                .map(CreatePartyRoleDto::getPartyId)
                .map(partyDataService::getParty)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));

        validatePartyCreator(party);

        return Optional.of(dto)
                .map(v -> mapper.toModel(v, party))
                .map(dataService::save)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST, "Не удалось создать роль"));
    }

    public PartyRoleDto updateRole(PartyRoleDto dto) {
        PartyRoleModel partyRoleModel = getPartyRoleById(dto.getId());

        validatePartyCreator(partyRoleModel.getParty());

        return Optional.of(dto)
                .map(v -> mapper.toModel(partyRoleModel, v))
                .map(dataService::save)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST, "Не удалось изменить роль"));
    }

    public void deleteRole(Long roleId) {
        PartyRoleModel partyRoleModel = getPartyRoleById(roleId);

        validatePartyCreator(partyRoleModel.getParty());

        dataService.delete(roleId);
    }

    public PartyRoleDto getRole(Long roleId) {
        return Optional.of(roleId)
                .map(dataService::findById)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Роль не найдена"));
    }

    public List<PartyRoleDto> getRoles(Long partyId) {
        return Optional.of(partyId)
                .map(dataService::findAllByParty)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Роли не найдены"));
    }

    //в роль может добавлять либо организатор мероприятия, либо сам пользователь
    //пользователь должен быть участником мероприятия
    public void addUserToRole(Long roleId, String username) {
        validateCurrentUserOrCreator(roleId, username);

        dataService.addUserToRole(roleId, username);
    }

    public void deleteUserFromRole(Long roleId, String username) {
        validateCurrentUserOrCreator(roleId, username);

        dataService.deleteUserFromRole(roleId, username);
    }

    public List<String> getUsersByRole(Long roleId) {
        validatePartyParticipant(roleId);

        return dataService.getUsersByRole(roleId);
    }

    private void validatePartyCreator(PartyModel partyModel) {
        String currentUser = UserContextService.getCurrentUser();

        if (!StringUtils.equals(currentUser, partyModel.getCreator())) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Вы не создатель мероприятия");
        }
    }

    private void validatePartyParticipant(PartyModel partyModel) {
        String currentUser = UserContextService.getCurrentUser();

        if (!partyModel.getParticipants().contains(currentUser)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Вы не участник мероприятия");
        }
    }

    private void validatePartyParticipant(Long roleId) {
        Optional.of(roleId)
                .map(this::getPartyRoleById)
                .map(PartyRoleModel::getParty)
                .stream()
                .peek(this::validatePartyParticipant);
    }

    private void validateCurrentUserOrCreator(Long roleId, String user) {
        String currentUser = UserContextService.getCurrentUser();

        PartyRoleModel partyRoleModel = getPartyRoleById(roleId);

        if (!StringUtils.equals(partyRoleModel.getParty().getCreator(), user)
                && !StringUtils.equals(currentUser, user)) {
            throw ClientException.of(HttpStatus.NOT_FOUND,
                    "Действие может совершать только сам пользователь или организатор мероприятия");
        }

    }

    private PartyRoleModel getPartyRoleById(Long roleId) {
        return Optional.of(roleId)
                .map(dataService::findById)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Роль не найдена"));
    }
}
