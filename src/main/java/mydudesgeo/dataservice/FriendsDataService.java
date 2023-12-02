package mydudesgeo.dataservice;

import com.vladmihalcea.hibernate.util.StringUtils;
import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Visibility;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.FriendMapper;
import mydudesgeo.model.FriendModel;
import mydudesgeo.model.PartyModel;
import mydudesgeo.repository.friend.AllUsersRepository;
import mydudesgeo.repository.friend.CloseFriendsRepository;
import mydudesgeo.repository.friend.FriendTemplateRepository;
import mydudesgeo.repository.friend.FriendsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendsDataService {

    private final AllUsersRepository allUsersRepository;
    private final FriendsRepository friendsRepository;
    private final CloseFriendsRepository closeFriendsRepository;

    private final FriendMapper mapper;

    private final Map<Visibility, FriendTemplateRepository>
            friendsRepos = Map.of(
            Visibility.ALL, allUsersRepository,
            Visibility.FRIENDS, friendsRepository,
            Visibility.CLOSE_FRIENDS, closeFriendsRepository
    );

    @Transactional(readOnly = true)
    public boolean friendExists(Visibility visibility, String person, String friend) {
        return Optional.of(visibility)
                .map(friendsRepos::get)
                .map(friendTemplateRepository ->
                        friendTemplateRepository.existsByFriendAndPerson(person, friend))
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Ошибка при проверке категории друзей"));
    }

    @Transactional(readOnly = true)
    public FriendModel getFriends(Visibility visibility, String authUser) {
        return Optional.of(visibility) //todo all visibility??
                .map(friendsRepos::get)
                .map(repository -> repository.findAllByPerson(authUser))
                .map(friendTemplate -> mapper.toModel(friendTemplate, authUser, visibility))
                .orElse(null);
    }

    @Transactional
    public FriendModel addFriend(Visibility visibility, String person, String friend) {
        Optional.of(person)
                .map(v -> mapper.toEntity(v, friend))
                .map(friendTemplate -> friendsRepos.get(visibility).save(friendTemplate));

        return getFriends(visibility, person);
    }

    @Transactional
    public void deleteFriend(Visibility visibility, String person, String friend) {
        friendsRepos.get(visibility).deleteByPersonAndFriend(person, friend);
    }

    @Transactional(readOnly = true)
    public boolean filterUsers(PartyModel party, String user) {
        Visibility visibility = Optional.of(party)
                .map(PartyModel::getVisibility)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Не найдена видимость мероприятия"));

        String friend = Optional.of(party)
                .map(PartyModel::getCreator)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Не найден id создателя мероприятия"));

        if (StringUtils.isBlank(user)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Не указан пользователь");
        }

        return friendsRepos.get(visibility).existsByFriendAndPerson(friend, user);
    }
}
