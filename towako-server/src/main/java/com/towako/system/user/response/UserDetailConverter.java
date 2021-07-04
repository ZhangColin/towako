package com.towako.system.user.response;

import com.cartisan.CartisanContext;
import com.cartisan.dtos.Converter;
import com.towako.system.organization.OrganizationAppService;
import com.towako.system.organization.reponse.OrganizationDto;
import com.towako.system.role.RoleAppService;
import com.towako.system.role.response.RoleDto;
import com.towako.system.user.domain.User;
import com.towako.system.user.domain.UserOrganization;
import com.towako.system.user.domain.UserRole;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Mapper
public interface UserDetailConverter extends Converter<User, UserDetailDto>{
    UserDetailConverter CONVERTER = Mappers.getMapper(UserDetailConverter.class);

    @Override
    @InheritConfiguration
    @Mapping(source="roles", target="roles")
    @Mapping(source="organizations", target="organization")
    UserDetailDto convert(User user);

    default List<RoleDto> userRoles2RoleDtos(List<UserRole> userRoles){
        final RoleAppService roleAppService =
                CartisanContext.getBean(RoleAppService.class);

        final List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId).collect(toList());
        return roleAppService.getAllEnableRoles().stream()
                .filter(roleDto -> roleIds.contains(roleDto.getId()))
                .collect(toList());
    }

    default OrganizationDto userOrganization2OrganizationDto(List<UserOrganization> userOrganizations){
        final OrganizationAppService organizationAppService =
                CartisanContext.getBean(OrganizationAppService.class);

        final List<Long> organizationIds = userOrganizations.stream()
                .map(UserOrganization::getOrganizationId).collect(toList());
        return organizationAppService.getAllOrganizations().stream()
                .filter(organizationDto -> organizationIds.contains(organizationDto.getId()))
                .findFirst()
                .orElse(null);
    }
}
