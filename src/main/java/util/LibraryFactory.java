package util;

import com.google.inject.Inject;
import controller.Library;
import factoryInterfaces.BooksFactoryInterface;
import factoryInterfaces.LibraryFactoryInterface;

public class LibraryFactory implements LibraryFactoryInterface {
    @Inject
    BooksFactoryInterface booksFactory;

    @Override
    public Library library(Integer capacity){
        return new Library(capacity, booksFactory);
    }
}
