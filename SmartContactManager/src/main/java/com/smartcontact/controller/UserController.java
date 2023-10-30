package com.smartcontact.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.smartcontact.entities.Contact;
import com.smartcontact.entities.Email;
import com.smartcontact.entities.User;
import com.smartcontact.repo.SmartContactRepo;
import com.smartcontact.repo.SmartUserRepo;
import com.smartcontact.service.Service;

@org.springframework.stereotype.Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private SmartUserRepo userRepo;
	@Autowired
	private SmartContactRepo contactRepo;
	@Autowired
	private Service service;

	@GetMapping("/user-dashboard")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userDashBoard(Model model, Principal p) {
		model.addAttribute("title", "User Dashboard | " + p.getName());
		model.addAttribute("user", userRepo.getUserByEmail(p.getName()));
		return "normal/dashboard";
	}

	@ModelAttribute
	public void addCommonData(Model model, Principal p) {
		model.addAttribute("title", "User Dashboard | " + p.getName());
		model.addAttribute("user", userRepo.getUserByEmail(p.getName()));
	}

	@GetMapping("/add-contacts")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String addContacts(Model model, Principal p) {
		model.addAttribute("contact", new Contact());
		return "normal/addContacts";
	}

	@PostMapping("/process-contact")
	@ResponseBody
	public String processContact(@RequestParam("image") MultipartFile image, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("secondName") String secondName,
			@RequestParam("phone") String phone, @RequestParam("work") String work,
			@RequestParam("description") String description, Principal principal) {
		try {
			Contact contact = new Contact(name, email, secondName, phone, work, description);
			User user = userRepo.getUserByEmail(principal.getName());
			if (image != null && !image.isEmpty()) {
				File f = new ClassPathResource("static/img").getFile();
				Path p = Paths.get(f.getAbsolutePath() + "/" + image.getOriginalFilename());
				Files.copy(image.getInputStream(), p, StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(image.getOriginalFilename());
			} else {
				contact.setImage("default.png");
			}
			if (contact.getName().isBlank() || contact.getPhone().isBlank()) {
				return "Name & Phone number should not be Empty";
			}
			contact.setUser(user);

			user.getContactList().add(contact);
			userRepo.save(user);
			return contact.getPhone() + " contact Saved Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error Occured";
		}
	}

	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model model, Principal p) {
		Pageable pg = PageRequest.of(page, 5);
		Page<Contact> list = contactRepo.findContactById(userRepo.getUserByEmail(p.getName()).getId(), pg);
		model.addAttribute("contactList", list);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", list.getTotalPages());
		return "normal/showcontacts";
	}

	@GetMapping("/contactDetails/{id}")
	public String getContactById(@PathVariable("id") Integer id, Model model, Principal p) {
		Optional<Contact> c = contactRepo.findById(id);
		if (userRepo.getUserByEmail(p.getName()).getId() == c.get().getUser().getId()) {
			model.addAttribute("contact", c.get());
		}
		return "normal/contactDetails";
	}

	@GetMapping("/deleteContact/{id}")
	public String deleteContact(@PathVariable("id") Integer id) {
		contactRepo.deleteById(id);
		return "redirect:/user/show-contacts/0";
	}

	@GetMapping("/updateContact/{id}")
	public String updateContact(@PathVariable("id") Integer id, Model m) {
		m.addAttribute("contact", contactRepo.findById(id).get());
		return "normal/updateContact";
	}

	@PostMapping("/saveUpdatedContact")
	@ResponseBody
	public String saveUpdatedContact(@RequestParam("image") MultipartFile image, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("secondName") String secondName,
			@RequestParam("phone") String phone, @RequestParam("work") String work,
			@RequestParam("description") String description, @RequestParam("cId") Integer id, Principal principal)
			throws IOException {
		Contact contact = contactRepo.findById(id).get();

		contact.setName(name);
		contact.setSecondName(secondName);
		contact.setEmail(email);
		contact.setPhone(phone);
		contact.setWork(work);
		contact.setDescription(description);
		if (!image.isEmpty()) {
			File f = new ClassPathResource("static/img").getFile();
			Path p = Paths.get(f.getAbsolutePath() + "/" + image.getOriginalFilename());
			System.out.println("Status deleted file - " + new File(p.toString()).delete());

			contact.setImage(image.getOriginalFilename());
		}
		contactRepo.save(contact);
		return contact.getName() + " updated Successfully";
	}

	@GetMapping("/profile")
	public String profile(Model model, Principal p) {
		model.addAttribute("user", userRepo.getUserByEmail(p.getName()));
		return "normal/profile";
	}

	@GetMapping("/search/{name}")
	@ResponseBody
	public ResponseEntity<List<Contact>> searchContact(@PathVariable("name") String name, Principal p) {
		List<Contact> contacts = contactRepo.searchContacts(name, userRepo.getUserByEmail(p.getName()).getId());
		return ResponseEntity.ok(contacts);
	}

	@GetMapping("/email")
	public String email() {
		
		return "normal/email";
	}

	@PostMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(@RequestBody Email email) {
		boolean status = service.sendEmail(email.getTo(), email.getFrom(), email.getSubject(), email.getMessage());
		if (status) {
			return "Email sent successfully..!!";
		} else {
			return "Failed , Something went wrong..!!";
		}
	}

	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder(@RequestBody String amt) throws RazorpayException {
		int amount = Integer.parseInt(amt);
		RazorpayClient rc = new RazorpayClient("rzp_test_Z3A6v1cb8caRQp", "PAwblv8QVMbZsYMzbppE6j2A");
		JSONObject options = new JSONObject();
		options.put("amount", amount * 100);
		options.put("currency", "INR");
		options.put("receipt", "txn_34343");
		Order order = rc.Orders.create(options);
		System.out.println(order);
		return order.toString();
	}

}
