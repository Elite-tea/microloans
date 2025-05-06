package ru.hometask.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.hometask.entities.Employee;
import ru.hometask.entities.Role;

import java.util.Collection;
import java.util.List;

/**
 * Реализация UserDetails для интеграции сотрудников с Spring Security.
 */
public class EmployeeUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public EmployeeUserDetails(Employee employee) {
        this.username = employee.getLogin();
        this.password = employee.getPassword();
        this.authorities = mapRolesToAuthorities(employee.getRole());
    }

    private List<GrantedAuthority> mapRolesToAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}