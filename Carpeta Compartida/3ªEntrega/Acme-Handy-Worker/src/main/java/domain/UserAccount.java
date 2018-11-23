
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import security.Authority;

@Entity
@Access(AccessType.PROPERTY)
public class UserAccount extends DomainEntity {

	private String					username;
	private String					password;
	private Collection<Authority>	authorities;


	@NotBlank
	@Size(min = 5, max = 32)
	public String getUsername() {
		return this.username;
	}
	public void setUsername(final String username) {
		this.username = username;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}

	@ElementCollection
	@Override
	public Collection<Authority> getAuthorities() {
		return this.authorities;
	}
	public void setAuthorities(final Collection<Authority> authorities) {
		this.authorities = authorities;
	}

}
