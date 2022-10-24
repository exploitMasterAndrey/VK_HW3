package factoryInterfaces;

import controller.Library;

public interface LibraryFactoryInterface {
    Library createLibraryKnownCapacity(Integer capacity);
}
