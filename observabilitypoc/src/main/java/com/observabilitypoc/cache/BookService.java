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

    // is public static to be accessible on @Cacheable annotation of getBookByISBN method
    public static final String BOOK_ISBN_WILL_NOT_BE_CACHED = "9788550804606";
    private static final String BOOK_ISBN_WILL_SLEEP_FIRST_CALL = "9788575223920";


    private final List<Book> javaBooks = new ArrayList<>(
            List.of(
                    new Book("Java: Como Programar", "Deitel", "9788576055643"),
                    new Book("Use a Cabeça! Java", "Kathy Sierra, Bert Bates", "9788576081734"),
                    new Book("Java Efetivo", "Joshua Bloch", BOOK_ISBN_WILL_NOT_BE_CACHED),
                    new Book("Padrões de Projetos com Java", "Eduardo Guerra", BOOK_ISBN_WILL_SLEEP_FIRST_CALL)
            )
    );


    @CacheEvict(cacheNames = "books", key = "#isbn")
    public boolean deleteBookByISBN(final String isbn) {
        log.info("Trying to delete book by ISBN: {}", isbn);
        return javaBooks.removeIf(book -> book.isbn().equalsIgnoreCase(isbn));
    }

    // another way to access class attribute is: T(<package>.<class_name>).<class_static_attribute>
    // will cache if and only if isbn received in getBookByISBN is not equals to BOOK_ISBN_WILL_NOT_BE_CACHED
    @Cacheable(cacheNames = "books", key = "#isbn", condition = "!#isbn.equalsIgnoreCase(@bookService.BOOK_ISBN_WILL_NOT_BE_CACHED)")
    public Book getBookByISBN(final String isbn) {

        // will sleep in EVERY call for ISBN #BOOK_ISBN_WILL_NOT_BE_CACHED
        // will sleep in FIRST call for ISBN #BOOK_ISBN_WILL_SLEEP_FIRST_CALL
        if (isbn.equalsIgnoreCase(BOOK_ISBN_WILL_NOT_BE_CACHED) || isbn.equalsIgnoreCase(BOOK_ISBN_WILL_SLEEP_FIRST_CALL)) {
            try {
                log.info("Sleeping when get book by ISBN = {}", isbn);
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        final Book result = javaBooks.stream()
                .filter(book -> book.isbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));

        log.info("Book found with ISBN: {}", result);
        return result;
    }

}
