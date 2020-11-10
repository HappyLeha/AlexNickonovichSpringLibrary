package com.example.demo.service;

import com.example.demo.dto.EmailDto;
import com.example.demo.dto.IdDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Value("${adminLogin}")
    private String login;

    @Value("${adminPassword}")
    private String password;

    @Value("${adminEmail}")
    private String email;


    private String phone=null;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        checkRoles();
        checkAdmin();
        Optional<User> user = userRepository.findAll().stream().
                filter(i-> i.getLogin().equals(username)).findFirst();
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public void addNewUser(User user) throws ResourceAlreadyCreateException {
        checkRoles();
        if (userRepository.findAll().stream().noneMatch(i -> i.getLogin().equals(user.getLogin()))) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            log.info("User "+user.toString()+" was created.");
        }
        else {
            throw new ResourceAlreadyCreateException();
        }
    }

    public User getById(Long id) throws ResourceNotFoundException {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    public void deleteUser(Long userId) throws ResourceRestrictException {
        if (userRepository.existsById(userId)) {
            if (userRepository.findUserById(userId).get().getRent()==null) {
                userRepository.deleteById(userId);
            }
            else throw new ResourceRestrictException();
        }
        else return;
        log.info("User "+userRepository.findUserById(userId).toString()+" was deleted.");
    }

    public void editUser(User user, Principal principal) throws ResourceAlreadyCreateException {
        User oldUser,logUser;
        String password;
        oldUser=getByLogin(principal.getName());
        if (user.getPassword()==null) password=oldUser.getPassword();
        else password=bCryptPasswordEncoder.encode(user.getPassword());
        boolean eq=!user.getLogin().equals(oldUser.getLogin());
        boolean exist=userRepository.findAll().stream().
                filter(i->i.getLogin().equals(user.getLogin())).count()==1;
        if (eq&&exist) throw new ResourceAlreadyCreateException();
        logUser=new User(oldUser.getId(),oldUser.getLogin(),oldUser.getPassword(),oldUser.getFirstName(),
                oldUser.getLastName(),oldUser.getYear(),oldUser.getEmail(),oldUser.getPhone(),
                oldUser.getPhoto(),oldUser.getRent(),oldUser.getRole());
        oldUser.setLogin(user.getLogin());
        oldUser.setPassword(password);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setYear(user.getYear());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhone(user.getPhone());
        oldUser.setPhoto(user.getPhoto());
        userRepository.save(oldUser);
        log.info("User "+logUser.toString()+" was changed to "+oldUser.toString()+".");
    }

    public List<User> getAllReader() {
        return userRepository.findAll().stream().filter(i->i.getRole().getId()==2).collect(Collectors.toList());
    }

    public List<User> getAllAdmin() {
        return userRepository.findAll().stream().filter(i->i.getRole().getId()==1).collect(Collectors.toList());
    }

    public List<User> getFreeReader() {
        return userRepository.findAll().stream().filter(i->i.getRent()==null&&i.getRole().getId()==2).
                collect(Collectors.toList());
    }

    public User getProfile(Principal principal) {
        User user=getByLogin(principal.getName());
        return user;
    }

    public User getByLogin (String login) {
        User user=userRepository.findAll().stream().
                filter(i->i.getLogin().equals(login)).findFirst().get();
        return user;
    }

    public List<EmailDto> getEmails(IdDto id) {
        ArrayList<EmailDto> emails=new ArrayList<>();
        for (User user:userRepository.findAll()) {
            if (id.getId().contains(user.getId())) emails.add(new EmailDto(user.getLogin(),user.getEmail()));
        }
        Collections.sort(emails);
        return emails;
    }

    private void checkAdmin() {
        if (userRepository.findAll().stream().noneMatch(i -> i.getRole().getId() == 1L)) {
            userRepository.save(new User(0L,login,bCryptPasswordEncoder.encode(password),
                    null,null,null,email,phone,null,null,new Role(1L,"ROLE_ADMIN",null)));
        }
    }

    private void checkRoles() {
        if ((long) roleRepository.findAll().size()==0) {
            roleRepository.save(new Role(1L,"ROLE_ADMIN",null));
            roleRepository.save(new Role(2L,"ROLE_READER",null));
        }
    }
    public void deleteProfile(Principal principal) throws ResourceRestrictException {
        deleteUser(getByLogin(principal.getName()).getId());
    }
    
}
