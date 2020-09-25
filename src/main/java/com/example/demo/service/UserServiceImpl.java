package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username "+username);
        Optional<User> user = userRepository.findAll().stream().filter(i->i.getLogin().equals(username)?true:false).findFirst();

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();

    }
    public void addNewUser(User user) throws ResourceAlreadyCreateException {
        if (userRepository.findAll().stream().filter(i->i.getLogin().equals(user.getLogin())).count()==0) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            System.out.println(user.getPhoto());
            userRepository.save(user);
            log.info("User "+user.toString()+" was created.");
        }
        else {
            throw new ResourceAlreadyCreateException();
        }

    }
    public User getById(Integer id) throws ResourceNotFoundException {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
    public void deleteUser(Integer userId) throws ResourceRestrictException {
        if (userRepository.existsById(userId)) {
            if (userRepository.findUserById(userId).get().getRent()==null) userRepository.deleteById(userId);
            else throw new ResourceRestrictException();
        }
        else return;;
        log.info("User "+userRepository.findUserById(userId).toString()+" was deleted.");
    }
    public void editUser(User user) throws ResourceAlreadyCreateException,ResourceNotFoundException {
        User oldUser,logUser;
        if (userRepository.existsById(user.getId())) oldUser =userRepository.findUserById(user.getId()).get();
        else throw new ResourceNotFoundException();
        boolean eq=!user.getLogin().equals(oldUser.getLogin());
        boolean exist=userRepository.findAll().stream().filter(i->i.getLogin().equals(user.getLogin())).count()==1;
        if (eq&&exist) throw new ResourceAlreadyCreateException();
        logUser=new User(oldUser.getId(),oldUser.getLogin(),oldUser.getPassword(),oldUser.getFirstName(),
                oldUser.getLastName(),oldUser.getYear(),oldUser.getEmail(),oldUser.getPhone(),
                oldUser.getPhoto(),oldUser.getRent(),oldUser.getRole());
        oldUser.setLogin(user.getLogin());
        oldUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setYear(user.getYear());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhone(user.getPhone());
        oldUser.setPhoto(user.getPhoto());
        userRepository.save(oldUser);
        log.info("User "+logUser.toString()+" was changed to "+oldUser.toString()+".");
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
