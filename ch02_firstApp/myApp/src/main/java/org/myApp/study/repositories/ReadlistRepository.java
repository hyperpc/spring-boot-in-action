package org.myApp.study.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.myApp.study.entities.*;

public interface ReadlistRepository extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);
}
