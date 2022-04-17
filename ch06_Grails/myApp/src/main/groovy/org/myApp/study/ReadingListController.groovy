package org.myAp.study

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
@RequestMapping("/")
@ConfigurationProperties(prefix = "amazon")
public class ReadlistController {
    @Autowired
    AmazonProperties amazonProp;

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    def error(){
        return "error";
    }

    @RequestMapping(method=RequestMethod.GET)
    def readersBooks(Reader reader, Model model){
        List<Book> readList = Book.findByReader(reader);
        if(readList){
            model.addAttribute("books", readList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonID", amazonProp.getAssociateId());
        }
        "readingList"
    }

    @RequestMapping(method=RequestMethod.POST)
    def addToReadList(Reader reader, Book book){
        Book.withTransaction{
            book.setReader(reader)
            book.save(book)
        }
        "redirect:/";
    }
}
