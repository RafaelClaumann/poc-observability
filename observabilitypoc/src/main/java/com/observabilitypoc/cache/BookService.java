package com.observabilitypoc.cache;

import com.observabilitypoc.cache.except.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final List<Book> javaBooks = new ArrayList<>(
            List.of(
                    new Book("Java: Como Programar", "Deitel", "9788576055643"),
                    new Book("Use a Cabeça! Java", "Kathy Sierra, Bert Bates", "9788576081734"),
                    new Book("Java Efetivo", "Joshua Bloch", "9788550804606"),
                    new Book("Padrões de Projetos com Java", "Eduardo Guerra", "9788575223920")
            )
    );

    @Cacheable(cacheNames = "books", key = "#isbn")
    public Book getBookByISBN(final String isbn) {
        final Book result = javaBooks.stream()
                .filter(book -> book.isbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));

        log.info("Book found: {}", result);
        return result;
    }

    @CacheEvict(cacheNames = "books", key = "#isbn")
    public boolean deleteBookByISBN(final String isbn) {
        return javaBooks.removeIf(book -> book.isbn().equalsIgnoreCase(isbn));
    }

}
