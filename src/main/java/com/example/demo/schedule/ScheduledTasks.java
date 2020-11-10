package com.example.demo.schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.dto.EmailCreateDto;
import com.example.demo.entity.User;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;

@Slf4j
@Component
public class ScheduledTasks {

	private final EmailService emailService;

	private final UserService userService;

	@Value("${formatReader}")
	private String formatReader;

	@Value("${formatAdmin}")
	private String formatAdmin;

	@Value("${subjectAdmin}")
	private String subjectAdmin;

	@Value("${subjectReader}")
	private String subjectReader;

	@Autowired
	public ScheduledTasks(EmailService emailService, UserService userService) {
		this.emailService=emailService;
		this.userService=userService;
	}

	@Scheduled(cron="0 0 12 ? * *")
	public void sendEmail() {
		try {
			List<User> users = userService.getAllReader().stream().
					filter(i -> i.getRent() != null && LocalDate.now().compareTo(i.getRent().getEndDate()) > 0).
					collect(Collectors.toList());
			String text = "";
			for (User user : users) {
				ArrayList<String> address = new ArrayList<>();
				address.add(user.getEmail());
				emailService.sendEmail(new EmailCreateDto(address, subjectReader,
						String.format(formatReader,user.getLastName(),user.getFirstName(),
								user.getRent().getBook().getTitle())));
				text += String.format(formatAdmin,user.getLogin(),
						user.getRent().getBook().getTitle()) + "\n";
			}
			List<User> admins = userService.getAllAdmin();
			ArrayList<String> emails = new ArrayList<>();
			for (User admin : admins) {
				emails.add(admin.getEmail());
			}
			emailService.sendEmail(new EmailCreateDto(emails, subjectAdmin, text));
		}
		catch (MessagingException e) {
          log.error(e.toString());
		}
	}

}
