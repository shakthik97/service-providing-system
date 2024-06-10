package edu.icet.ServiceBookingSystem.dto;

import edu.icet.ServiceBookingSystem.enums.UserRole;
import lombok.Data;

@Data
public class SignupRequestDTO {

    private Long id;

    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phone;
}
