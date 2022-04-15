package org.myApp.study.controllers;

import java.util.List;

import org.myApp.study.configuration.AmazonProperties;
import org.myApp.study.entities.Book;
import org.myApp.study.entities.Reader;
import org.myApp.study.repositories.ReadlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
@ConfigurationProperties(prefix = "amazon")
public class ReadlistController {
    private ReadlistRepository readlistRepo;
    private AmazonProperties amazonProp;

    @Autowired
    public ReadlistController(ReadlistRepository repo, AmazonProperties amazonProp){
        this.readlistRepo = repo;
        this.amazonProp = amazonProp;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fail")
    public void fail(){
        throw new RuntimeException();
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    public String error(){
        return "error";
    }

    @RequestMapping(method=RequestMethod.GET)
    public String readersBooks(String reader, Model model){
        List<Book> readList = readlistRepo.findByReader(reader);
        if(readList != null){
            model.addAttribute("books", readList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonID", amazonProp.getAssociateId());
        }
        return "readList";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String addToReadList(Reader reader, Book book){
        book.setReader(reader);
        readlistRepo.save(book);
        return "redirect:/";
    }
}
