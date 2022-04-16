import org.springframework.test.web.servlet.MockMvc.*
import org.springframework.test.web.servlet.MvcResult.*
import org.springframework.test.web.servlet.ResultActions.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.Mockito.*
import org.springframework.boot.test.mock.mockito.MockBean.*
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener.*
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes=SpringBootMain.class)
//@TestExecutionListener(MockitoTestExecutionListener.class)
class ReadingListControllerTest {
    @Test
    void shouldReturnReadingListFromRepository() {
        List<Book> books = new ArrayList<Book>()
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
        MockMvc mvc = standaloneSetup(controller).build()
        mvc.perform(get("/"))
        .andExpect(view().name("readingList"))
        .andExpect(model().attribute("books", books))
    }
}