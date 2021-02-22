package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repo.NoteRepository;
import com.example.demo.repo.UserRepository;

@Controller
@RequestMapping("/notes")
public class NoteController {
//	private List<Note> notes = new ArrayList<>(
//			Arrays.asList(new Note(1, "first", "aaa"), new Note(2, "second", "bbb")));

	private NoteRepository noteRepository;
	private UserRepository userRepository;
	
	@Autowired
	public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
		super();
		this.noteRepository = noteRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/all")
	public String notes(Model model, Principal principal) {
		
		if(principal!=null){
			User user = userRepository.findByUsername(principal.getName());
			List<Note>notes = user.getNotes();
			model.addAttribute("notes", notes);
		}else {
		model.addAttribute("notes", noteRepository.findAll());
		}
		return "notes";
	}

	@GetMapping("/create")
	public String create() {
		return "note_form";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute("note") Note note, Principal principal) {
//		int id = notes.stream().map(usr -> usr.getId()).max((a, b) -> a - b).orElse(0);
//		note.setId(id + 1);
//		notes.add(note);

		if(principal!=null){
			User user = userRepository.findByUsername(principal.getName());
			user.addNote(note);
			noteRepository.save(note);
			userRepository.save(user);		
		}else {
//		noteRepository.save(note);
		}
		return "redirect:/notes/all";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id) {
//		notes.removeIf(note->note.getId()==id);
		noteRepository.deleteById(id);
		return "redirect:/notes/all";
	}

	@GetMapping("/search")
	public String search(@RequestParam(name = "word") String word, Model model) {

		// поиск записок по имени или содержанию
		List<Note> notes = noteRepository.findByNameContainingOrDescriptionContaining(word, word);
		model.addAttribute("notes", notes);
		return "notes";
	}

	
	
	//HomeTask 1
	
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id,Model model) {

		model.addAttribute("note", noteRepository.findById(id).get());
		return "editNote_form";
	}

	@PostMapping("/change")
	public String change(@ModelAttribute("note") Note note) {

		int id = note.getId();
		Note editNote = noteRepository.findById(id).get();
		editNote.setName(note.getName());
		editNote.setDescription(note.getDescription());
		noteRepository.save(editNote);
		return "redirect:/notes/all";
	}
}
