package edu.icet.ServiceBookingSystem.services.authentications;

import edu.icet.ServiceBookingSystem.dto.SignupRequestDTO;
import edu.icet.ServiceBookingSystem.dto.UserDto;

public interface AuthService {

    UserDto signupClient(SignupRequestDTO signupRequestDTO);

    boolean presentByEmail(String email);

    UserDto signupCompany(SignupRequestDTO signupRequestDTO);
}
