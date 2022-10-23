package com.group.inventory.common.service;

import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.model.BaseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface GenericService<T extends BaseEntity, D, I> {
    JpaRepository<T, I> getRepository(); // Factory method
    ModelMapper getModelMapper();

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default List<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable).stream().collect(Collectors.toList());
    }

    default List<T> findAllIds(List<I> ids) {
        return getRepository().findAllById(ids);
    }

    default T findById(I id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new InventoryBusinessException("Id is not existed"));
    }

    default D findByIdDTO(I id, Class<D> clazz) {
         T entity = getRepository().findById(id)
                .orElseThrow(() -> new InventoryBusinessException("Id is not existed"));
        return getModelMapper().map(entity, clazz);
    }

    default List<D> findAllDTO(Class<D> clazz) {
        return getRepository().findAll()
                .stream()
                .map(entity -> getModelMapper().map(entity, clazz))
                .collect(Collectors.toList());
    }

    default List<D> findAllDTO(Pageable pageable, Class<D> clazz) {
        return getRepository().findAll(pageable)
                .stream()
                .map(entity -> getModelMapper().map(entity, clazz))
                .collect(Collectors.toList());
    }

    default D save(D dto, Class<T> clazz, Class<D> dtoClazz) {
        T model = getModelMapper().map(dto, clazz);
        T savedModel = getRepository().save(model);
        return getModelMapper().map(savedModel, dtoClazz);
    }

    default void deleteById(I id) {
        getRepository().deleteById(id);
    }

    default D update(D dto, I id, Class<D> dtoClazz) {
        T entity = getRepository().findById(id).orElseThrow(() -> new InventoryBusinessException("Id is not existed"));
        return getModelMapper().map(entity, dtoClazz);
    }
}
