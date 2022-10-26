package controller;

import com.google.gson.Gson;
import factoryInterfaces.BooksFactoryInterface;
import model.Book;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Library {
    private final Book[] books;
    private final BooksFactoryInterface bookFactory;

    private Gson gson = new Gson();

    public Library(@NotNull Integer booksAmount, @NotNull BooksFactoryInterface bookFactory) {
        this.books = new Book[booksAmount];
        this.bookFactory = bookFactory;

        if(bookFactory.books().size() > booksAmount) throw new RuntimeException("Sorry, there is to many books for this library :( Try again with less amount...");

        ArrayList<Book> booksFromFile = new ArrayList<>(bookFactory.books());

        for(int i = 0; i < booksFromFile.size(); i++){ books[i] = booksFromFile.get(i); }
    }

    public Book getBook(@NotNull Integer i){
        if (books[i] == null) throw new RuntimeException("There is no book in this cell...");
        System.out.println("book_num: " + i + "\n" + gson.toJson(books[i]));

        Book bookToReturn = books[i];
        books[i] = null;
        return bookToReturn;
    }

    public Book addBook(@NotNull Book book){
        boolean isSet = false;
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                books[i] = book;
                isSet = true;
                break;
            }
        }
        if (isSet) return book;
        else throw new RuntimeException("There is no place for new book :( Try again later...");
    }

    public void printBooks(){
        /*for (Book book : books){
            System.out.println(gson.toJson(book));
        }*/
        System.out.println(gson.toJson(books));
    }
}
