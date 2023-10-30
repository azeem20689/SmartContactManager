package com.smartcontact.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontact.entities.User;

@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	private com.smartcontact.repo.SmartUserRepo userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}

	@GetMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("title", "SignUp- Smart Contact Manager");
		return "signup";
	}

//	@PostMapping("/registerUser")
//	@ResponseBody
//	public String registerUser(@RequestBody User user ,Model model) {
//		try {
//		user.setRole("ROLE_USER");
//		user.setEnabel(true);
//		user.setImageUrl("default.png");
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		System.out.println(user);
//		userRepo.save(user);
//		return user.getName()+" registered Successfully..!!";
//		} catch(Exception e) {
//			return "Error Occured : Email Already Exist ";
//		}
//	}

	@PostMapping("/registerUser")
	@ResponseBody
	public String registerUser(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("imageUrl") MultipartFile imageUrl,
			@RequestParam("about") String about, Model model) {
		System.out.println("INSIDE THE REGISTARTION FORM ");
		try {
			User user = new User(name, email, passwordEncoder.encode(password), "ROLE_USER", true,
					imageUrl.getOriginalFilename(), about);
			if (!imageUrl.isEmpty()) {
				File f = new ClassPathResource("static/img").getFile();
				Path p = Paths.get(f.getAbsolutePath() + "/" + imageUrl.getOriginalFilename());
				Files.copy(imageUrl.getInputStream(), p, StandardCopyOption.REPLACE_EXISTING);
			} else {
				user.setImageUrl("profile.png");
			}

			System.out.println(user);
			userRepo.save(user);
			return user.getName() + " registered Successfully..!!";
		} catch (Exception e) {
			return "Error Occured : Email Already Exist ";
		}
	}

	@GetMapping("/logIn")
	public String login(Model model) {
		model.addAttribute("title", "Login | Smart Contact Manager");
		return "login";
	}

}
