package com.haizer.projetospringboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			
			return "redirect:/usuario/admin/listar";
			
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
	
	@GetMapping("/admin/apagar/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model, RedirectAttributes attributes)
	{
		try {
			Usuario usuario = usuarioRepository.findById(id)
							.orElseThrow(() -> new IllegalArgumentException("Id inválido:" + id));
			usuarioRepository.delete(usuario);
			
			attributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
			
			return "redirect:/usuario/admin/listar";
			
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}
	
	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, Model model)
	{
		try {
			Optional<Usuario> usuarioVelho = usuarioRepository.findById(id);
			if (!usuarioVelho.isPresent()) {
				throw new IllegalArgumentException("Usuário inválido: " + id);
			}
			
			Usuario usuario = usuarioVelho.get();
			model.addAttribute("usuario", usuario);
			
			return "/auth/user/user-alterar-usuario";
			
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}
	
	@PostMapping("/alterar/{id}")
	public String alterarUsuario(@PathVariable("id") long id, @Valid Usuario usuario, BindingResult result, RedirectAttributes attributes)
	{
		try {
			if (result.hasErrors()) {
				usuario.setId(id);
				return "/auth/user/user-alterar-usuario";
			}
			usuarioRepository.save(usuario);
			attributes.addFlashAttribute("mensagem", "Usuário alterado com sucesso!");
			
			return "redirect:/usuario/admin/listar";
			
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}
	
}
