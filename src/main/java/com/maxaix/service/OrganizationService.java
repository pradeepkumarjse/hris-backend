
package com.maxaix.service;

import com.maxaix.entity.Organization;
import java.util.List;


public interface OrganizationService {
    
    public Organization createOrganization(Organization organization);
    
    public Organization updateOrganization(Long id, Organization updatedOrganization);
    
     public List<Organization> getAllOrganizations();
     
     public Organization getOrganizationById(Long id);
     
       public void deleteOrganization(Long id);
    
}
