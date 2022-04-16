import org.springframework.test.web.servlet.MockMvc
import static org.springframework.test.web.servlet.setup.MockMvcBuilder.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilder.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.mockito.Mockito.*
class ReadingListControllerSpec extends Specification {
    MockMvc mockMvc
    List<Book> books

    void setup() {
        books = new ArrayList<Book>()
        books.add(new Book (
            id:1,
            reader:"Craig",
            isbn:"9781617292545",
            title:"Spring Boot in Action",
            author:"Craig Walls",
            description:"Spring Boot in Action is ..."
        ))

        def mockRepo = mock(ReadingListRepository.class)
        when(mockRepo.findByReader("Craig")).thenReturn(books)

        def controller = new ReadingListController(readingListRepository:mockRepo)
        mockMvc = standaloneSetup(controller).build()

        def "Should put list returned from repository into model"() {
            when:
                def response = mockMvc.perform(get("/"))
            then:
                response.andExpect(view().name("readingList"))
                        .andExpect(model().attribute("books", books))
        }
    }
}