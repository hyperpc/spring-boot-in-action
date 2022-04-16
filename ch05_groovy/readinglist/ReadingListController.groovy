@Controller
@RequestMapping("/")
class ReadingListController{
    String reader = "Craig"

    @Autowired
    ReadingListRepository repo

    @RequestMapping(method=RequestMethod.GET)
    def readersBooks(Model model) {
        List<Book> readingList = repo.findByReader(reader);
        if(readingList){
            model.addAttribute("books", readingList)
        }
        "readingList"
    }

    @RequestMapping(method=RequestMethod.POST)
    def addToReadingList(Book book){
        book.setReader(reader)
        repo.save(book)
        "redirect:/"
    }
}