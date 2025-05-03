package ru.hometask.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.hometask.entities.Employee;
import ru.hometask.repositories.EmployeeRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Employee> optionalEmployee = employeeRepository.findByLogin(username);

        if (optionalEmployee.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        return new EmployeeUserDetails(optionalEmployee.get());
    }
}
