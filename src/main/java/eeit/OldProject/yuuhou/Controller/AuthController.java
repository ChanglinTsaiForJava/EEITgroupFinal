package eeit.OldProject.yuuhou.Controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.RequestDTO.LoginRequest;
import eeit.OldProject.yuuhou.RequestDTO.RegisterReques;
import eeit.OldProject.yuuhou.Service.CaregiversService;
import eeit.OldProject.yuuhou.Util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private CaregiversService caregiversService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterReques request) {
		// 檢查 Email 是否已存在
		if (caregiversService.findByEmail(request.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body("此 Email 已被註冊！");
		}

		// 建立照顧者
		Caregiver caregiver = Caregiver.builder().caregiverName(request.getCaregiverName()).gender(request.getGender())
				.birthday(request.getBirthday()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())) // 加密
				.phone(request.getPhone()).nationality(request.getNationality()).languages(request.getLanguages())
				.yearOfExperience(request.getYearOfExperience()).serviceArea(request.getServiceArea())
				.description(request.getDescription()).photoPath(request.getPhotoPath())
				.hourlyRate(request.getHourlyRate()).halfDayRate(request.getHalfDayRate())
				.fullDayRate(request.getFullDayRate()).status(Caregiver.Status.ACTIVE).createdAt(LocalDateTime.now())
				.build();

		caregiversService.save(caregiver);
		return ResponseEntity.status(HttpStatus.CREATED).body("註冊成功！");
	}

	// 登入功能
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(request.getEmail());

		if (caregiverOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號錯誤！");
		}

		Caregiver caregiver = caregiverOpt.get();

		if (!passwordEncoder.matches(request.getPassword(), caregiver.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密碼錯誤！");
		}

		String token = jwtUtil.generateToken(caregiver.getEmail());

		return ResponseEntity.ok().body(Collections.singletonMap("token", token));
	}

}
