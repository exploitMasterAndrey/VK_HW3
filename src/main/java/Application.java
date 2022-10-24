import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.Library;
import model.Author;
import model.Book;
import module.Module;
import util.LibraryFactory;

public class Application {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module(args[0]));
        Library library = injector.getInstance(LibraryFactory.class).library(Integer.valueOf(args[1]));

        System.out.println("before insert");
        library.printBooks();

        System.out.println("get book");
        library.getBook(50);

        System.out.println("insert new book");
        library.addBook(Book.builder()
                        .name("new book")
                        .author(Author.builder()
                                .name("Pushkin")
                                .build())
                .build());
        library.printBooks();
    }
}
