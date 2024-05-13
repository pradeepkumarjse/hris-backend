package com.maxaix.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter

@ToString
public class User extends Auditable<String>  implements Serializable {
    
                 @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	@Column
	private String username;
        
	@Column
	private String password;
        
                  @Column
	private String phone; 
                   
	@Column
	private String email;    
        
	private String firstname;
	@Column
	private String lastname;
        
                   @Column
	private String status;
                   
                 @Column
	private String workLocation;
        
	
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "user_role_mapping", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role;
        
        
                @ManyToOne
                @JoinColumn(name = "organization_id")
                private Organization organization;
	


}
