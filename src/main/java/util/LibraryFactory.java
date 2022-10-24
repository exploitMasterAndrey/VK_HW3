package util;

import com.google.inject.Inject;
import controller.Library;
import factoryInterfaces.BooksFactoryInterface;

public class LibraryFactory {
    @Inject
    BooksFactoryInterface booksFactory;

    public Library library(Integer capacity){
        return new Library(capacity, booksFactory);
    }
}
