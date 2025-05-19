package az.edu.turing.msnotification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-auth")
public interface StaffClient {
    @GetMapping("/api/v1/staff/emails")
    List<String> getAllStaffEmails();
}
