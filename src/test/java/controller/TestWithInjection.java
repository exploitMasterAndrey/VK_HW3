package controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import module.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.FileBookFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestWithInjection {

    @Inject
    FileBookFactory injectedFileBookFactory;

    @BeforeEach
    void init() {
        Module module = new Module("books.txt");
        Injector injector = Guice.createInjector(module);
    }

    @Test
    void libraryThrowsExceptionDuringCreationTest() {
        assertThrows(RuntimeException.class, () -> new Library(2, injectedFileBookFactory));
    }
}
