package br.com.fiap.Checkpoint1;

import java.math.BigDecimal;

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
		BigDecimal num1 = new BigDecimal("0");
        BigDecimal num2 = new BigDecimal("1");
        
        // Comparando num1 e num2
        int comparison = num1.compareTo(num2);
        System.out.println(comparison);
        if (comparison > 0) {
            System.out.println("num1 é maior que num2");
        } else if (comparison < 0) {
            System.out.println("num1 é menor que num2");
        } else {
            System.out.println("num1 é igual a num2");
        }
    
		return "Rafael da Silva Camargo RM551127 Camila Soares Pedra RM 98246";
		
	}

}
