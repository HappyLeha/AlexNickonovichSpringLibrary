/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.demo.schedule;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.example.demo.dto.EmailDto;
import com.example.demo.entity.User;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class ScheduledTasks {
	private final EmailService emailService;
	private final UserService userService;
	@Autowired
	public ScheduledTasks(EmailService emailService, UserService userService) {
		this.emailService=emailService;
		this.userService=userService;
	}
	@Scheduled(cron="0 0 21 ? * *")
	public void sendEmail() {
		List<User> users=userService.getAllUser().stream().
				filter(i->i.getRent()==null?false:LocalDate.now().compareTo(i.getRent().getEndDate())>0).
				collect(Collectors.toList());
		String text="";
		for (User user:users) {
			ArrayList<String> address=new ArrayList<>();
			address.add(user.getEmail());
			emailService.sendEmail(new EmailDto(address,"Задолженность по книге",
					"Здравствуйте! "+user.getLastName()+" "+user.getFirstName()+
							". Пожалуйста, верните книгу "+user.getRent().getBook().getTitle()+"."));
			text+=user.getLogin()+": "+user.getRent().getBook().getTitle()+"\n";
		}
		List<User> admins=userService.getAllUser().stream().filter(i->i.getRole().getId()==1).collect(Collectors.toList());
		ArrayList<String> emails=new ArrayList<>();
		for (User admin:admins) {
			emails.add(admin.getEmail());
		}
		emailService.sendEmail(new EmailDto(emails,"Список задолженностей",text));
	}
}
