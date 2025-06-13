package com.test.iam.service;

import com.github.javafaker.Faker;
import com.test.iam.model.role.Privilege;
import com.test.iam.model.role.Role;
import com.test.iam.model.user.User;
import com.test.iam.repository.PrivilegeRepository;
import com.test.iam.repository.RoleRepository;
import com.test.iam.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class FakeUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    private final Faker faker = new Faker(new Locale("ko"));
    private final Random rand = new Random();

    public FakeUserService(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    public void generateFakeUsers(int count) {
        if (userRepository.count() >= count) {
            System.out.println("🔁 이미 " + userRepository.count() + "명의 사용자가 존재하므로 더미 데이터를 생성하지 않습니다.");
            return;
        }

        // 1. 권한 생성
        List<String> privilegeNames = List.of("READ_USER", "WRITE_USER", "READ_DEPARTMENT", "MANAGE_ROLE");
        List<Privilege> allPrivileges = new ArrayList<>();

        for (String name : privilegeNames) {
            Privilege p = privilegeRepository.findByName(name)
                    .orElseGet(() -> {
                        Privilege newPrivilege = new Privilege(name); // ✅ name 설정 필수
                        return privilegeRepository.save(newPrivilege);
                    });
            allPrivileges.add(p);
        }

        // 2. 역할 생성
        List<Role> allRoles = new ArrayList<>();
        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setName("ADMIN");
            admin.setPrivileges(new HashSet<>(allPrivileges));
            roleRepository.save(admin);
            allRoles.add(admin);

            Role user = new Role();
            user.setName("USER");
            user.setPrivileges(Set.of(allPrivileges.get(0))); // READ_USER
            roleRepository.save(user);
            allRoles.add(user);
        } else {
            allRoles = roleRepository.findAll();
        }

        // 3. 사용자 생성
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUsername("user" + (i + 1));
            user.setGender(i % 2 == 0 ? "F" : "M");
            user.setEmail("user" + (i + 1) + "@example.com");
            user.setWorkEmail("work." + (i + 1) + "@company.com");

            int deptIdx = i % 5;
            user.setDepartment(List.of("IT", "HR", "FIN", "MKT", "SALES").get(deptIdx));
            user.setDepartmentName(List.of("IT팀", "인사팀", "재무팀", "마케팅팀", "영업팀").get(deptIdx));

            user.setNationality("KR");
            user.setMaritalStatus(rand.nextBoolean() ? "Married" : "Single");
            user.setPosition(List.of("Staff", "Manager", "Lead", "Director").get(rand.nextInt(4)));
            user.setJobTitle(List.of("개발자", "회계", "인사담당", "마케터").get(rand.nextInt(4)));
            user.setHireDate(LocalDate.now().minusDays(rand.nextInt(3650)));

            if (rand.nextInt(100) < 20) {
                user.setTerminationDate(user.getHireDate().plusDays(rand.nextInt(1000)));
            }

            user.setWorkPhone("02-6000-" + String.format("%04d", rand.nextInt(10000)));
            user.setMobilePhone("010-" + (rand.nextInt(9000) + 1000) + "-" + (rand.nextInt(9000) + 1000));
            user.setAddress(faker.address().fullAddress());
            user.setBankAccount("110-" + faker.number().digits(3) + "-" + faker.number().digits(5));
            user.setTaxId("TAX-" + faker.number().digits(6));
            user.setEnabled(true);

            Collections.shuffle(allRoles);
            int maxAssignable = Math.min(allRoles.size(), 2);
            int assignCount = rand.nextInt(maxAssignable + 1);
            Set<Role> assignedRoles = new HashSet<>(allRoles.subList(0, assignCount));
            user.setRoles(assignedRoles);

            userRepository.save(user);
        }
    }
}

