package az.edu.turing.msstudent.client;

import az.edu.turing.msstudent.model.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-auth")
public interface AuthServiceClient {
    @GetMapping("/api/v1/users/{userId}/has-role")
    boolean hasRole(@PathVariable Long userId, @RequestParam String role);

    @GetMapping("/api/v1/users/{userId}")
    UserResponseDto getUserById(@PathVariable Long userId);
}