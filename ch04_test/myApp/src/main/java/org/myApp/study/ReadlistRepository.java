package org.myApp.study;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadlistRepository extends JpaRepository<Book, Long> {
	
	List<Book> findByReader(Reader reader);

}
