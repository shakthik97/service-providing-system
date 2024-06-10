package edu.icet.ServiceBookingSystem.entity;

import edu.icet.ServiceBookingSystem.dto.UserDto;
import edu.icet.ServiceBookingSystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phone;
    private UserRole role;

    public UserDto getDto(){
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setEmail(email);
       // userDto.setLastName(lastName);
        userDto.setRole(role);


        return userDto;
    }
}
