package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Integer>{

	//SELECT * FROM notes WHERE label LIKE '%word' OR message LIKE '%word%'
	public List<Note> findByNameContainingOrDescriptionContaining(String name, String description);
	public List <Note> findByUserId(int id);
	
	}
