package com.haizer.projetospringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haizer.projetospringboot.modelo.Usuario;
import com.haizer.projetospringboot.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/novo")
	public String adicionarUsuario(Model model) 
	{
		try {
			model.addAttribute("usuario", new Usuario());
			
			return "/publica-criar-usuario";
		}
		catch(Exception e) {
			return e.getMessage();
		}
		
	}
	
	@PostMapping("/salvar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes)
	{
		try {
			if (result.hasErrors()) {
				return "/publica-criar-usuario";
			}
			
			usuarioRepository.save(usuario);
			attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
			
			return "redirect:/usuario/novo";
			
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}
	
	@RequestMapping("/admin/listar")
	public String listarUsuario(Model model)
	{
		try {
			model.addAttribute("usuarios", usuarioRepository.findAll());
			
			return "/auth/admin/admin-listar-usuario";
			
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}
}
