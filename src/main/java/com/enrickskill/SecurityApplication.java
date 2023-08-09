package com.enrickskill;

import com.enrickskill.request.exam.CreateExamRequest;
import com.enrickskill.service.auth.AuthenticationService;
import com.enrickskill.request.user.RegisterRequest;
import com.enrickskill.entity.Role;
import com.enrickskill.service.exam.ExamServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service,
			ExamServiceImpl examService
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.firstname("User")
					.lastname("user")
					.email("user@mail.com")
					.password("password")
					.role(Role.USER)
					.build();
			System.out.println("User token: " + service.register(manager).getAccessToken());

			var exam = CreateExamRequest.builder()
					.owner_exam(2)
					.exam_name("Toan")
					.build();
			System.out.println(examService.save(exam).getExam_name());
		};
	}
}
