/**
 * 
 */
package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Julio
 *
 */
@Controller
public class RegistroController {

	
	 @GetMapping("/login")
	 public  String iniciarSesion(){
		 return "login";
	 }
}
