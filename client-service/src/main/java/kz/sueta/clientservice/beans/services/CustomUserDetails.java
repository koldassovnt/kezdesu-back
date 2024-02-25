package kz.sueta.clientservice.beans.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.sueta.clientservice.entity.Client;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String clientId;
    @JsonIgnore
    private String password;

    public static CustomUserDetails of(Client client) {
        return new CustomUserDetails(client.clientId, client.password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return clientId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
