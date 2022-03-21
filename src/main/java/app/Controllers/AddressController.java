package app.Controllers;

import app.Persistance.Entities.Address;
import app.Persistance.Entities.User;
import app.Services.AddressService;
import app.Services.UserService;


import org.aspectj.weaver.bcel.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller

public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;

    @GetMapping("/addresses")
    public String showAddressBook(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        Address address = addressService.getAddress(user);
        if(address == null){
            return "address_form";
        }
        model.addAttribute("address", address);
        model.addAttribute("user",user);

        return "addresses";
    }

    @GetMapping("/addresses/new")
    public String newAddress(Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("pageTitle", "Please add Address");
        return "address_form";
    }


    @PostMapping("/addresses/save")
    public String saveAddress(Address address, Principal principal, RedirectAttributes ra,HttpServletRequest request) {
        User user = userService.findByUsername(principal.getName());
        address.setUser(user);
        addressService.save(address);
        return "redirect:/addresses";
    }

    @GetMapping("/addresses/edit/{id}")
    public String editAddress(@PathVariable("id") UUID addressId, Model model,
                              Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Optional<Address> address = addressService.get(addressId);
        model.addAttribute("address", address);
        model.addAttribute("pageTitle", "Edit Address");
        return "address_form";
    }


    @GetMapping("/addresses/delete/{id}")
    public String deleteAddress(@PathVariable("id") UUID addressId, RedirectAttributes ra,
                                Principal principal) {
        User user = userService.findByUsername(principal.getName());
        addressService.delete(addressId, user.getId());
        return "redirect:/addresses/new";
    }



}
