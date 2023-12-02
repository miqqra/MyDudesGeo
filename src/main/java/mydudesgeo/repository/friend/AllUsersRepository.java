package mydudesgeo.repository.friend;

import mydudesgeo.entity.friends.FriendTemplate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class AllUsersRepository implements FriendTemplateRepository {

    @Override
    public boolean existsByFriendAndPerson(String friend, String person) {
        return true;
    }

    @Override
    public List<FriendTemplate> findAllByPerson(String person) {
        return Collections.emptyList();
    }

    @Override
    public void deleteByPersonAndFriend(String person, String friend) {

    }

    //overrided methods
    @Override
    public void flush() {
    }

    @Override
    public <S extends FriendTemplate> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends FriendTemplate> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<FriendTemplate> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    /**
     * @param aLong
     * @deprecated
     */
    @Override
    public FriendTemplate getOne(Long aLong) {
        return null;
    }

    /**
     * @param aLong
     * @deprecated
     */
    @Override
    public FriendTemplate getById(Long aLong) {
        return null;
    }

    @Override
    public FriendTemplate getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends FriendTemplate> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends FriendTemplate> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends FriendTemplate> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends FriendTemplate> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends FriendTemplate> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends FriendTemplate> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends FriendTemplate, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends FriendTemplate> S save(S entity) {
        return null;
    }

    @Override
    public <S extends FriendTemplate> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<FriendTemplate> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<FriendTemplate> findAll() {
        return null;
    }

    @Override
    public List<FriendTemplate> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(FriendTemplate entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends FriendTemplate> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<FriendTemplate> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<FriendTemplate> findAll(Pageable pageable) {
        return null;
    }
}
