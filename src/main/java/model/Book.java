package model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Builder
@Getter
@ToString
public class Book {
    @NotNull
    private Author author;
    @NotNull
    private String name;
}
