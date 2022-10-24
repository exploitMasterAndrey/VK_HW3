package module;

import com.google.inject.AbstractModule;
import factoryInterfaces.BooksFactoryInterface;
import util.FileBookFactory;

public class Module extends AbstractModule {

    private final String pathToInitFile;

    public Module(String pathToInitFile) {
        super();
        this.pathToInitFile = pathToInitFile;
    }

    @Override
    protected void configure() {
        bind(BooksFactoryInterface.class).toInstance(new FileBookFactory(pathToInitFile));
    }
}
