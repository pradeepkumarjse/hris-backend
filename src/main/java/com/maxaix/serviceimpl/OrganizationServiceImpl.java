
package com.maxaix.serviceimpl;

import com.maxaix.entity.Organization;
import com.maxaix.repository.OrganizationRepository;
import com.maxaix.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.persistence.EntityNotFoundException;

@Service
public class OrganizationServiceImpl implements OrganizationService{

    @Autowired
    private OrganizationRepository organizationRepository;

     @Override
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

     @Override
    public Organization updateOrganization(Long id, Organization updatedOrganization) {
        Organization organization = getOrganizationById(id);
        organization.setName(updatedOrganization.getName());
        return organizationRepository.save(organization);
    }

     @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

     @Override
    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + id));
    }

    @Override
    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }
}

