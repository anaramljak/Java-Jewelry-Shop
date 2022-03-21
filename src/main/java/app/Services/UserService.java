package app.Services;

import app.DTO.UserDTO;
import app.DTO.UserRegistrationDTO;
import app.Persistance.Entities.Cart;
import app.Persistance.Entities.Orders;
import app.Persistance.Entities.Role;
import app.Persistance.Entities.User;
import app.Persistance.Repositories.CartRepository;
import app.Persistance.Repositories.OrdersRepository;
import app.Persistance.Repositories.UserRepository;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrdersRepository ordersRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CartRepository cartRepository, OrdersRepository ordersRepository) {
        super();
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.ordersRepository = ordersRepository;
    }

    @Transactional
    public String save(UserRegistrationDTO registrationDTO) {
        User localUser = userRepository.findByEmail(registrationDTO.getEmail());
        if (localUser != null) {
            return "redirect:/registration?noSuccess";
        }
        if (registrationDTO.getPassword().length() < 6) {
            return "redirect:/registration?incorrectPassword";
        }
        if(!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())){
            return "redirect:/registration?incorrectConfirmPassword";

        }
        Cart cart = new Cart();
        User user = new User(
                registrationDTO.getName(),
                registrationDTO.getSurname(),
                registrationDTO.getEmail(),
                passwordEncoder.encode(registrationDTO.getConfirmPassword()),
                Arrays.asList(new Role("ROLE_USER")));
        cart.setUser(user);
        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        return "redirect:/login";

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String name) {
        return userRepository.findByEmail(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
