package com.ecommerce.user.services;


import com.ecommerce.user.dto.AddressDto;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private List<User> userList = new ArrayList<>();
    private Long idCount = 1L;

    public List<UserResponse> createUsers(UserRequest user) {
      User user1= mapToUserEntity(user);
       // user1.setId(idCount++);
      userRepository.save(user1);
      return getAllUsers();
    }

    public List<UserResponse> getAllUsers() {
        List<User> user = userRepository.findAll();
        List<UserResponse> responseList = new ArrayList<>();
        for (User u : user) {
            responseList.add(mapToUserResponse(u));
        }
        return  responseList;
    }

    public UserResponse getUserById(Long id) {
       // return userList.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
        User u = userRepository.findById(id).orElse(null);
        UserResponse userResponse = mapToUserResponse(u);
        return userResponse;
    }

    public boolean updateUser(User u) {
        if (u.getId() == null) {
            return false;
        }
        return userRepository.findById(u.getId()).map(existing -> {
            // merge non-null properties from incoming user
            if (u.getFirstName() != null) existing.setFirstName(u.getFirstName());
            if (u.getLastName() != null) existing.setLastName(u.getLastName());
            if (u.getEmail() != null) existing.setEmail(u.getEmail());
            if (u.getPhone() != null) existing.setPhone(u.getPhone());
            if (u.getRole() != null) existing.setRole(u.getRole());
            userRepository.save(existing);
            return true;
        }).orElse(false);
    }

    private UserResponse mapToUserResponse(User u) {
        UserResponse response = new UserResponse();
        response.setId(u.getId());
        response.setFirstName(u.getFirstName());
        response.setLastName(u.getLastName());
        response.setEmail(u.getEmail());
        response.setPhone(u.getPhone());
        response.setRole(u.getRole());
       response.setAddress(u.getAddress() != null ? mapToAddressDto(u.getAddress()) : null);
        return response;

    }

    private AddressDto mapToAddressDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        dto.setZipcode(address.getZipcode());
        return dto;
    }

    private User mapToUserEntity(UserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        if (request.getAddress() != null) {
            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setCountry(request.getAddress().getCountry());
            address.setZipcode(request.getAddress().getZipcode());
            user.setAddress(address);
        }
        return user;
    }
}
