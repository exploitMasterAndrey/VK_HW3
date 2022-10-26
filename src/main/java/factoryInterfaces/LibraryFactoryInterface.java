package factoryInterfaces;

import controller.Library;

public interface LibraryFactoryInterface {
    Library library(Integer capacity);
}
