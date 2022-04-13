package org.myApp.study.controllers;

import java.util.List;

import org.myApp.study.entities.Book;
import org.myApp.study.repositories.ReadlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/readList")
public class ReadlistController {
    private ReadlistRepository readlistRepo;

    @Autowired
    public ReadlistController(ReadlistRepository repo){
        this.readlistRepo = repo;
    }

    @RequestMapping(value="/{reader}", method=RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model){
        List<Book> readList = readlistRepo.findByReader(reader);
        if(readList != null){
            model.addAttribute("books", readList);
        }
        return "readList";
    }

    @RequestMapping(value="/{reader}", method=RequestMethod.POST)
    public String addToReadList(@PathVariable("reader") String reader, Book book){
        book.setReader(reader);
        readlistRepo.save(book);
        return "redirect:/readlist/{reader}";
    }
}
