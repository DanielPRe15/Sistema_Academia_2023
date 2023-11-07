package com.sistema.academia.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.academia.entities.Curso;
import com.sistema.academia.entities.Profesor;
import com.sistema.academia.services.ProfesorServices;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {
	
	@Autowired
	private ProfesorServices servicioPro;
	
	@RequestMapping("/lista")
	public String index(Model model) {
		
		model.addAttribute("profesor",servicioPro.listarTodos());
		
		return "profesor";
	
	}
	
	@RequestMapping("/grabar")
	public String grabar (@RequestParam("codigo") Integer cod,
						@RequestParam("nombre")String nom,
						@RequestParam("apellido")String ape,
						@RequestParam("edad")String edad,
						@RequestParam("estado")String estado,
						@RequestParam("dni")String dni,
						@RequestParam("fono")String fono,
						@RequestParam("direccion")String direc,
						@RequestParam("mail")String mail,
						@RequestParam("fechaNac")String fecNac,
						@RequestParam("fechaReg")String fecReg,
						
						RedirectAttributes redirect)
	{
		
		try {
			//crear objeto de la entidad medicamento
			Profesor pro = new Profesor();
			//setear atrinutos 
			pro.setNombre(nom);
			pro.setApellido(ape);
			pro.setEdad(edad);
			pro.setEstado(estado);
			pro.setDni(dni);
			pro.setFono(dni);
			pro.setDireccion(direc);
			pro.setMail(mail);
			pro.setFecNac(LocalDate.parse(fecNac));
			pro.setFecReg(LocalDate.parse(fecReg));
		
			
			
			
			//validar parametro
		
			if(cod == 0) {
				
				//Invocar metodo registrar
				servicioPro.registrar(pro);
				//crearatribut de tipo flash
				redirect.addFlashAttribute("MENSAJE","Medico registrado");
			}
			else {
				
				pro.setCodigo(cod);
				servicioPro.actualizar(pro);
				redirect.addFlashAttribute("MENSAJE","Medico actualizado");
			}
					
			

			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return "redirect:/profesor/lista";
		}

@RequestMapping("/buscar")
@ResponseBody
public Profesor buscarProfesor (@RequestParam("codigo") Integer cod)
{
	return servicioPro.buscarPorID(cod);
}

@RequestMapping("/eliminar")
public String eliminarPorCodigo(@RequestParam("codigo")Integer cod,
								RedirectAttributes redirect) 
{
	servicioPro.eliminarPorID(cod);
	redirect.addFlashAttribute("MENSAJE","Profesor eliminado correctamente");
	
	return "redirect:/profesor/lista";
}
		
		

}