package org.myApp.study;

import java.util.ArrayList;
import java.util.List;

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
@ConfigurationProperties("amazon")
public class ReadlistController {
    
	private ReadlistRepository readingListRepository;
    private AmazonProperties amazonConfig;
  
      @Autowired
      public ReadlistController(ReadlistRepository readingListRepository,
          AmazonProperties amazonConfig) {
            this.readingListRepository = readingListRepository;
            this.amazonConfig = amazonConfig;
      }
  
      @RequestMapping(method = RequestMethod.GET, value = "/fail")
      public void fail(){
          throw new RuntimeException();
      }
  
      @ExceptionHandler(value = RuntimeException.class)
      @ResponseStatus(value = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
      @RequestMapping(value = "/error", method = RequestMethod.GET)
      public String error(){
          return "error";
      }
      
      @RequestMapping(method=RequestMethod.GET)
      public String readersBooks(Reader reader, Model model) {
          if(reader==null){
            reader = new Reader();
            reader.setUsername("craig");
            reader.setPassword("password");
            reader.setFullname("Craig Walls");
          }
          List<Book> readingList = readingListRepository.findByReader(reader);
          if (readingList == null) {
            readingList = new ArrayList<>();
          }
              model.addAttribute("books", readingList);
              model.addAttribute("reader", reader);
              model.addAttribute("amazonID", amazonConfig.getAssociateId());
          return "readingList";
        }
      
      @RequestMapping(method=RequestMethod.POST)
      public String addToReadingList(Reader reader, Book book) {
          book.setReader(reader);
          readingListRepository.save(book);
          return "redirect:/";
      }
      
}
