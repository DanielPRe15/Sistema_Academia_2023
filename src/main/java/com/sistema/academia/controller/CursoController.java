package com.sistema.academia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.academia.entities.Curso;
import com.sistema.academia.entities.TipoCurso;
import com.sistema.academia.entities.TurnoCurso;
import com.sistema.academia.services.CursoServices;
import com.sistema.academia.services.TipoCursoServices;
import com.sistema.academia.services.TurnoCursoServices;

@Controller
@RequestMapping("/cursos")
public class CursoController {
	
	@Autowired
	private CursoServices servicioCurso;
	
	@Autowired
	private TipoCursoServices servicioTipoCurso;
	
	@Autowired
	private TurnoCursoServices servicioTurno;
	

@RequestMapping("/lista")
	public String Index(Model model)
	{
		model.addAttribute("cursos", servicioCurso.listarTodos());
		model.addAttribute("tipoCursos", servicioTipoCurso.listarTodos());
		model.addAttribute("turnos", servicioTurno.listarTodos());
		
		return "cursos";
	
	}
@RequestMapping("/grabar")
public String grabar (@RequestParam("codigo") Integer cod,
					@RequestParam("nombre")String nom,
					@RequestParam("horaIni")String horaini,
					@RequestParam("horaFin")String horafin,
					@RequestParam("seccion")String seccion,
					@RequestParam("vacantes")int vacantes,
					@RequestParam("vacantesLib")int vacantesLib,
					@RequestParam("turno")int codTurno,
					@RequestParam("tipo")int codTipo,
					RedirectAttributes redirect)
	{
		try
		{
			
			Curso curso = new Curso();
			
			curso.setNombre(nom);
			curso.setHoraini(horaini);
			curso.setHorafin(horafin);
			curso.setSeccion(seccion);
			curso.setVacantes(vacantes);
			curso.setVacantesLibre(vacantesLib);
			
			TipoCurso tipo = new TipoCurso();
			TurnoCurso turno = new TurnoCurso();
			
			tipo.setCodigo(codTipo);
			turno.setCodigo(codTurno);
			
			curso.setTipoCurs(tipo);
			curso.setTurnoCurs(turno);
			
			if(cod == 0)
			{
				servicioCurso.registrar(curso);
				redirect.addFlashAttribute("MENSAJE","Curso Registrado Correctamente");
			}
			else
			{
				curso.setCodigo(cod);
				servicioCurso.actualizar(curso);
				redirect.addFlashAttribute("MENSAJE","Curso Actualizado Correctamente");
			}
			
			
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		
		return "redirect:/cursos/lista";
		
	
	}

@RequestMapping("/buscar")
@ResponseBody
public Curso buscar (@RequestParam("codigo") Integer cod)
{
	return servicioCurso.buscarPorID(cod);
}

@RequestMapping("/eliminar")
public String eliminarPorCodigo(@RequestParam("codigo")Integer cod,
								RedirectAttributes redirect) 
{
	servicioCurso.eliminarPoID(cod);
	redirect.addFlashAttribute("MENSAJE","Curso eliminado correctamente");
	
	return "redirect:/cursos/lista";
}


	
	

}