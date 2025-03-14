package com.observabilitypoc.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Book> saveBook(@RequestParam(name = "book_isbn") String isbn) {
        // debug to see cache items
        Cache book = cacheManager.getCache("books");
        return ResponseEntity.ok(bookService.getBookByISBN(isbn));
    }

    @GetMapping("/remove")
    public ResponseEntity<?> removeBook(@RequestParam(name = "book_isbn") String isbn) {
        // debug to see cache items
        Cache book = cacheManager.getCache("books");
        if(bookService.deleteBookByISBN(isbn))
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
