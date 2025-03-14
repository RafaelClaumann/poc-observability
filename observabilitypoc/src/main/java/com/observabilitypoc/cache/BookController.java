package com.observabilitypoc.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final CacheManager cacheManager;

    public BookController(BookService bookService, CacheManager cacheManager) {
        this.bookService = bookService;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/fetch")
    public Book saveBook(@RequestParam(name = "book_isbn") String isbn) {
        // debug to see cache items
        Cache book = cacheManager.getCache("books");
        return bookService.getBookByISBN(isbn);
    }

    @GetMapping("/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeBook(@RequestParam(name = "book_isbn") String isbn) {
        // debug to see cache items
        Cache book = cacheManager.getCache("books");
        bookService.deleteBookByISBN(isbn);
    }

}
