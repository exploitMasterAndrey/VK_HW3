package controller;

import com.google.gson.Gson;
import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import util.FileBookFactory;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LibraryTest {
    static Gson gson = new Gson();

    @Mock
    FileBookFactory fileBookFactory;

    private List<Book> booksFromLibrary;

    @BeforeEach
    void init(){
        booksFromLibrary = Arrays.asList(
                Book.builder().name("book1").author(Author.builder().name("author1").build()).build(),
                Book.builder().name("book2").author(Author.builder().name("author2").build()).build(),
                Book.builder().name("book3").author(Author.builder().name("author3").build()).build()
        );
        Mockito.when(fileBookFactory.books()).thenReturn(booksFromLibrary);
    }

    @Test
    void libraryThrowsExceptionDuringCreationTest(){
        assertThrows(RuntimeException.class, () -> new Library(2, fileBookFactory));
    }


    @Test
    void booksOrderSameWithBooksFromBookFactoryTest(){
        Library library = new Library(5, fileBookFactory);
        List<Book> booksFromBooksFactory = (List<Book>) fileBookFactory.books();

        for (int i = 0; i < 3; i++) {
            assertEquals(booksFromBooksFactory.get(i), library.getBook(i));
        }

        assertThrows(RuntimeException.class, () -> library.getBook(3));
        assertThrows(RuntimeException.class, () -> library.getBook(4));
    }

    @Test
    void printBookInfoAfterTakingBookTest(){
        PrintStream stream = Mockito.mock(PrintStream.class);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        System.setOut(stream);

        Library library = new Library(3, fileBookFactory);

        library.getBook(2);

        Mockito.verify(stream).println(captor.capture());
        assertEquals("book_num: 2\n" + gson.toJson(booksFromLibrary.get(2)), captor.getValue());
    }

    @Test
    void getBookFromEmptyCellThrowsExceptionTest(){
        Library library = new Library(4, fileBookFactory);

        assertThrows(RuntimeException.class, () -> library.getBook(3));
    }

    @Test
    void getBookFromCellReturnBookFromCellTest(){
        Library library = new Library(3, fileBookFactory);

        assertEquals(library.getBook(2), booksFromLibrary.get(2));
    }

    @Test
    void addBookAddsBookToEmptyCell(){
        Library library = new Library(5, fileBookFactory);

        Book bookToAdd = Book.builder().author(Author.builder().name("newAuthor").build()).name("newBook").build();
        library.addBook(bookToAdd);

        assertEquals(bookToAdd, library.getBook(3));
    }

    @Test
    void addBookThrowsExceptionIfAllCellAreBusyTest(){
        Library library = new Library(3, fileBookFactory);

        assertThrows(RuntimeException.class, () -> library.addBook(Book.builder().author(Author.builder().name("newAuthor").build()).name("newBook").build()));
    }

    @Test
    void printBooksPrintsInfoAboutCellsInLibraryTest(){
        PrintStream stream = Mockito.mock(PrintStream.class);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        System.setOut(stream);

        Library library = new Library(4, fileBookFactory);

        library.printBooks();

        StringBuilder stringBuilder = new StringBuilder(gson.toJson(booksFromLibrary));
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(",null]");

        Mockito.verify(stream).println(captor.capture());
        assertEquals(captor.getValue(), stringBuilder.toString());
    }
}