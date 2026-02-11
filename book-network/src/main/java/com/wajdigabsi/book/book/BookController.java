package com.wajdigabsi.book.book;


import com.wajdigabsi.book.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService service ;


    @PostMapping
    public ResponseEntity<Integer> saveBook(
            @RequestBody @Valid BookRequest request,
            Authentication connectedUser
    ) {
        return  ResponseEntity.ok(service.save(request,connectedUser));
    }


    @GetMapping("{book-id}")
        public ResponseEntity<BookResponse> findBookById(
                @PathVariable Integer bookId) {
    return ResponseEntity.ok(service.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
           @RequestParam(name = "page" ,defaultValue= "0" ,required = false)int page,
           @RequestParam(name = "size" ,defaultValue= "10" ,required = false)int size,
           Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBooks(page,size,connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page" ,defaultValue= "0" ,required = false)int page,
            @RequestParam(name = "size" ,defaultValue= "10" ,required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBooksByOwner(page,size,connectedUser));

    }

    @GetMapping("/borrowd")
    public ResponseEntity<PageResponse<BorrowedBookResponse >> findAllBoroowedBooks(
            @RequestParam(name = "page" ,defaultValue= "0" ,required = false)int page,
            @RequestParam(name = "size" ,defaultValue= "10" ,required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBoroowedBooks(page,size,connectedUser));

    }



}
