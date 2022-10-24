package controller;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import model.Author;
import model.Book;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import util.FileBookFactory;
import util.LibraryFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void libraryThrowsExceptionDuringCreationTest(){
        Mockito.when(fileBookFactory.books()).thenReturn(booksFromLibrary);

        assertThrows(RuntimeException.class, () -> new Library(2, fileBookFactory));
    }


    @Test
    void booksOrderSameWithBooksFromBookFactoryTest(){
        Mockito.when(fileBookFactory.books()).thenReturn(booksFromLibrary);

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

        Mockito.when(fileBookFactory.books()).thenReturn(booksFromLibrary);

        Library library = new Library(3, fileBookFactory);

        library.getBook(2);

        Mockito.verify(stream).println(captor.capture());
        assertEquals("book_num: 2\n" + gson.toJson(booksFromLibrary.get(2)), captor.getValue());
    }

    @Test
    void getBookFromEmptyCellThrowsExceptionTest(){
        Mockito.when(fileBookFactory.books()).thenReturn(booksFromLibrary);

        Library library = new Library(4, fileBookFactory);

        assertThrows(RuntimeException.class, () -> library.getBook(3));
    }

    @Test
    void getBookFromCellReturnBookFromCellTest(){
        Mockito.when(fileBookFactory.books()).thenReturn(booksFromLibrary);

        Library library = new Library(3, fileBookFactory);

        assertEquals(library.getBook(2), booksFromLibrary.get(2));
    }

    @Test
    void addBookAddsBookToEmptyCell(){
        Mockito.when(fileBookFactory.books()).thenReturn(booksFromLibrary);

        Library library = new Library(5, fileBookFactory);

        Book bookToAdd = Book.builder().author(Author.builder().name("newAuthor").build()).name("newBook").build();
        library.addBook(bookToAdd);

        assertEquals(bookToAdd, library.getBook(3));
    }

    @Test
    void addBookThrowsExceptionIfAllCellAreBusyTest(){
        Mockito.when(fileBookFactory.books()).thenReturn(booksFromLibrary);

        Library library = new Library(3, fileBookFactory);

        assertThrows(RuntimeException.class, () -> library.addBook(Book.builder().author(Author.builder().name("newAuthor").build()).name("newBook").build()));
    }

    @Test
    void printBooksPrintsInfoAboutCellsInLibraryTest(){
        PrintStream stream = Mockito.mock(PrintStream.class);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        System.setOut(stream);

        Mockito.when(fileBookFactory.books()).thenReturn(booksFromLibrary);

        Library library = new Library(4, fileBookFactory);

        library.printBooks();

        StringBuilder stringBuilder = new StringBuilder(gson.toJson(booksFromLibrary));
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(",null]");

        Mockito.verify(stream).println(captor.capture());
        assertEquals(captor.getValue(), stringBuilder.toString());
    }
}