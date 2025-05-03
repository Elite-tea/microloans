package ru.hometask.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.hometask.entities.Employee;
import ru.hometask.entities.Role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeUserDetails implements UserDetails {

    private final List<Role> authorities;
    private final String login;
    private final String password;

    public EmployeeUserDetails(Employee employee) {
        this.authorities = List.of(employee.getRole());
        this.login = employee.getLogin();
        this.password = employee.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }
}
