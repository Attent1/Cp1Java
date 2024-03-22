package br.com.fiap.Checkpoint1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller

public class Checkpoint1Application {

	public static void main(String[] args) {
		SpringApplication.run(Checkpoint1Application.class, args);
	}

	@RequestMapping
	@ResponseBody
	public String home(){
		return "Rafael da Silva Camargo RM551127 Camila Soares Pedra RM 98246";
	}

}
