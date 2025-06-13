package com.test.iam.Controller;

import com.test.iam.service.FakeUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final FakeUserService fakeUserService;

    public AdminController(FakeUserService fakeUserService) {
        this.fakeUserService = fakeUserService;
    }

    @PostMapping("/generate-fake-users")
    public ResponseEntity<String> generateFakeUsers(@RequestParam(defaultValue = "100") int count) {
        fakeUserService.generateFakeUsers(count);
        return ResponseEntity.ok(count + "명의 테스트 사용자 생성 완료");
    }
}
