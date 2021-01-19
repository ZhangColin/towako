package com.towako.system.resource.application;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.towako.system.resource.domain.Resource;
import com.towako.system.resource.repository.ResourceRepository;
import com.towako.system.resource.request.ResourceParam;
import com.towako.system.resource.request.ResourceQuery;
import com.towako.system.resource.response.ResourceConverter;
import com.towako.system.resource.response.ResourceDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class ResourceAppService {
    public static final String ERR_NAME_EXISTS = "资源名称已存在。";
    private final ResourceConverter converter = ResourceConverter.CONVERTER;

    private final ResourceRepository repository;

    public ResourceAppService(ResourceRepository repository) {
        this.repository = repository;
    }

    public List<ResourceDto> getAllResources() {
        return converter.convert(repository.findAll(Sort.by(Sort.Direction.ASC, "categoryId")
                .and(Sort.by(Sort.Direction.ASC, "sort"))));
    }

    public PageResult<ResourceDto> searchResources(@NonNull ResourceQuery resourceQuery, @NonNull Pageable pageable) {
        final Page<Resource> searchResult = repository.findAll(querySpecification(resourceQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by(Sort.Direction.ASC, "categoryId")
                                .and(Sort.by(Sort.Direction.ASC, "sort"))));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    @Transactional(rollbackOn = Exception.class)
    public ResourceDto addResource(ResourceParam resourceParam) {
        if (repository.existsByName(resourceParam.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }

        final Resource resource = new Resource(resourceParam.getName(), resourceParam.getCode(),
                resourceParam.getUrl(), resourceParam.getCategoryId());

        resource.describe(resourceParam.getName(), resourceParam.getCategoryId(),
                resourceParam.getDescription(), resourceParam.getSort());

        return converter.convert(repository.save(resource));
    }

    @Transactional(rollbackOn = Exception.class)
    public ResourceDto editResource(Long id, ResourceParam resourceParam) {
        if (repository.existsByNameAndIdNot(resourceParam.getName(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }

        final Resource resource = requirePresent(repository.findById(id));

        resource.describe(resourceParam.getName(), resourceParam.getCategoryId(),
                resourceParam.getDescription(), resourceParam.getSort());

        resource.configPermission(resourceParam.getCode(), resourceParam.getUrl());

        return converter.convert(repository.save(resource));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeResource(long id) {
        repository.deleteById(id);
    }

}
