package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.mapper.PartyCategoryMapper;
import mydudesgeo.model.PartyCategoryModel;
import mydudesgeo.repository.PartyCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyCategoryDataService {

    private final PartyCategoryRepository repository;

    private final PartyCategoryMapper mapper;

    @Transactional(readOnly = true)
    public List<PartyCategoryModel> getCategories() {
        return Optional.of(repository.findAll())
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toModel)
                .toList();
    }

    @Transactional
    public PartyCategoryModel createCategory(PartyCategoryModel partyCategoryModel) {
        return Optional.of(partyCategoryModel)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional
    public PartyCategoryModel updateCategory(PartyCategoryModel partyCategoryModel) {
        return Optional.of(partyCategoryModel)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return repository.existsByCategory(name);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
