package org.myApp.study.repositories;

import org.myApp.study.entities.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, String> {    
}
