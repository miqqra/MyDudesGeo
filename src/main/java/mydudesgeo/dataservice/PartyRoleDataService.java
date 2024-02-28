package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.entity.PartyRole;
import mydudesgeo.entity.User;
import mydudesgeo.mapper.PartyRoleMapper;
import mydudesgeo.model.PartyRoleModel;
import mydudesgeo.repository.PartyRoleRepository;
import mydudesgeo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyRoleDataService {

    private final PartyRoleRepository repository;

    private final UserRepository userRepository;

    private final PartyRoleMapper mapper;

    @Transactional(readOnly = true)
    public PartyRoleModel findById(Long id) {
        return Optional.of(id)
                .flatMap(repository::findById)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PartyRoleModel> findAllByParty(Long partyId) {
        return Optional.of(partyId)
                .map(repository::findByParty)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional
    public PartyRoleModel save(PartyRoleModel partyRoleModel) {
        return Optional.of(partyRoleModel)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional
    public void delete(Long roleId) {
        repository.deleteById(roleId);
    }

    @Transactional
    public void addUserToRole(Long roleId, String username) {
        User user = userRepository.getUserByName(username);

        Optional.of(roleId)
                .flatMap(repository::findById)
                .map(v -> {
                    v.getUsers().add(user);
                    return v;
                })
                .map(repository::save);
    }

    @Transactional
    public void deleteUserFromRole(Long roleId, String username) {
        User user = userRepository.getUserByName(username);

        Optional.of(roleId)
                .flatMap(repository::findById)
                .map(v -> {
                    v.getUsers().remove(user);
                    return v;
                })
                .map(repository::save);
    }

    @Transactional(readOnly = true)
    public List<String> getUsersByRole(Long roleId) {
        return Optional.of(roleId)
                .flatMap(repository::findById)
                .map(PartyRole::getUsers)
                .stream()
                .flatMap(Collection::stream)
                .map(User::getName)
                .toList();
    }
}
