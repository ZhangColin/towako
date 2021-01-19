package com.towako.system.resource.repository;

import com.cartisan.repositories.BaseRepository;
import com.towako.system.resource.domain.Resource;

import java.util.List;

/**
 * @author colin
 */
public interface ResourceRepository extends BaseRepository<Resource, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Resource> findByIdIn(List<Long> resourceIds);
}
