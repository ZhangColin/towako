package com.towako.system.resource.repository;

import com.cartisan.repositories.BaseRepository;
import com.towako.system.resource.domain.ResourceCategory;

/**
 * @author colin
 */
public interface ResourceCategoryRepository extends BaseRepository<ResourceCategory, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
