package com.jpjeronimo.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.jpjeronimo.workshopmongo.domain.Post;
import com.jpjeronimo.workshopmongo.domain.User;
import com.jpjeronimo.workshopmongo.dto.AuthorDTO;
import com.jpjeronimo.workshopmongo.dto.CommentDTO;
import com.jpjeronimo.workshopmongo.repository.PostRepository;
import com.jpjeronimo.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiaton implements CommandLineRunner {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"),"Partiu viagem", "vou para sp, abs",new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("29/03/2018"),"Bom dia", "acordei feliz", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("boa viagem mano", sdf.parse("21/03/2018"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("good trip", sdf.parse("22/03/2018"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("have a nice day", sdf.parse("23/03/2018"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c3));
		post2.getComments().add(c2);
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);		
	}

}
