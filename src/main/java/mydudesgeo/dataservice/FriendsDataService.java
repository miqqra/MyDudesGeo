package mydudesgeo.dataservice;

import com.vladmihalcea.hibernate.util.StringUtils;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Visibility;
import mydudesgeo.entity.friends.CloseFriends;
import mydudesgeo.entity.friends.Friends;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.FriendMapper;
import mydudesgeo.mapper.UserMapper;
import mydudesgeo.model.FriendModel;
import mydudesgeo.model.PartyModel;
import mydudesgeo.model.UserModel;
import mydudesgeo.repository.friend.CloseFriendsRepository;
import mydudesgeo.repository.friend.FriendsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendsDataService {

    private final FriendsRepository friendsRepository;
    private final CloseFriendsRepository closeFriendsRepository;

    private final FriendMapper mapper;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public boolean friendExists(Visibility visibility, String person, String friend) {
        return Optional.of(visibility)
                .map(v -> existsByPersonAndFriend(v, person, friend))
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Ошибка при проверке категории друзей"));
    }

    @Transactional(readOnly = true)
    public FriendModel getFriends(Visibility visibility, String authUser) {
        return Optional.of(visibility)
                .map(v -> getAll(visibility, authUser))
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<UserModel> getFriendsWhoVisitedParty(Long userId, Long partyId) {
        //todo сделать проверку, что нельзя смотреть невидные тебе мероприятия
        return Optional.of(userId)
                .map(uid -> friendsRepository.getFriendsWhoVisitedTheParty(uid, partyId))
                .stream()
                .flatMap(Collection::stream)
                .map(Friends::getFriend)
                .map(userMapper::toModel)
                .toList();
    }

    @Transactional
    public FriendModel addFriend(Visibility visibility, String person, String friend) {
        switch (visibility) {
            case FRIENDS -> save(mapper.toEntityFriends(person, friend));
            case CLOSE_FRIENDS -> save(mapper.toEntityCloseFriends(person, friend));
            default -> {
            }
        }
        return getFriends(visibility, person);
    }

    @Transactional
    public void deleteFriend(Visibility visibility, String person, String friend) {
        deleteByPersonAndFriend(visibility, person, friend);
    }

    @Transactional(readOnly = true)
    public boolean filterUsers(PartyModel party, String user) {
        Visibility visibility = Optional.of(party)
                .map(PartyModel::getVisibility)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Не найдена видимость мероприятия"));

        UserModel friend = Optional.of(party)
                .map(PartyModel::getCreator)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Не найден id создателя мероприятия"));

        if (StringUtils.isBlank(user)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Не указан пользователь");
        }

        return existsByPersonAndFriend(visibility, friend.getNickname(), user);
    }

    private boolean existsByPersonAndFriend(Visibility visibility, String person, String friend) {
        return switch (visibility) {
            case FRIENDS -> friendsRepository.existsByFriendNicknameAndPersonNickname(friend, person);
            case CLOSE_FRIENDS -> closeFriendsRepository.existsByFriendNicknameAndPersonNickname(friend, person);
            case ALL -> true;
        };
    }

    private FriendModel getAll(Visibility visibility, String person) {
        return switch (visibility) {
            case FRIENDS -> mapper.toModelFriends(friendsRepository.findByPersonNickname(person), person, visibility);
            case CLOSE_FRIENDS ->
                    mapper.toModelCloseFriends(closeFriendsRepository.findByPersonNickname(person), person, visibility);
            case ALL -> null;
        };
    }

    private void deleteByPersonAndFriend(Visibility visibility, String person, String friend) {
        switch (visibility) {
            case FRIENDS -> friendsRepository.deleteByPersonNicknameAndFriendNickname(person, friend);
            case CLOSE_FRIENDS -> closeFriendsRepository.deleteByPersonNicknameAndFriendNickname(person, friend);
            case ALL -> {
            }
        }
    }

    private void save(Friends friends) {
        friendsRepository.save(friends);
    }

    private void save(CloseFriends friends) {
        closeFriendsRepository.save(friends);
    }
}
