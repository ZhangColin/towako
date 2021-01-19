package com.towako.system.user.domain;

import com.towako.system.organization.Organization;
import com.towako.system.organization.OrganizationRepository;
import com.towako.system.role.RoleRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static com.towako.system.user.UserFixture.userOf;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AssignServiceTest {
    private User user;
    private OrganizationRepository organizationRepository;
    private Organization organization;
    private AssignService assignService;
    private RoleRepository roleRepository;

    @Before
    public void setUp() throws Exception {
        user = userOf();
        organizationRepository = mock(OrganizationRepository.class);
        roleRepository = mock(RoleRepository.class);
        organization = new Organization(1L, 0L, "Cartisan");
        assignService = new AssignService(roleRepository, organizationRepository);

        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(organization));
    }

    @Test
    public void when_assign_role_ids_is_null_then_not_call_repository() {
        // when
        assignService.assignRoles(user, null);

        // then
        verify(roleRepository, never()).findAllById(any());
    }

    @Test
    public void when_assign_organization_id_is_null_then_not_call_repository() {
        // when
        assignService.assignOrganization(user, null);

        // then
        verify(organizationRepository, never()).findById(any());
    }

    @Test
    public void should_assign_organization_success() {
        // when
        assignService.assignOrganization(user, Arrays.asList(1L));

        // then
        assertThat(user.getOrganizations().size()).isEqualTo(1);
        assertThat(user.getOrganizations().get(0).getOrganizationId()).isEqualTo(1L);
    }


    @Test
    public void when_organization_already_in_user_organizations_then_no_error() {
        // given
        user.assignOrganizations(singletonList(organization.getId()));

        // when
        assignService.assignOrganization(user, Arrays.asList(1L));

        // then
        assertThat(user.getOrganizations().size()).isEqualTo(1);
        assertThat(user.getOrganizations().get(0).getOrganizationId()).isEqualTo(1L);
    }
}
