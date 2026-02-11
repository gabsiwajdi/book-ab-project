package com.wajdigabsi.book.book;


import com.wajdigabsi.book.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book toBook(BookRequest request) {

        return Book.builder()
                .id(request.id())
                .title(request.title())
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .archived(false)
                .shaerable(request.sharable())
                .build();
    }

    public BookResponse toBookResponse(Book book) {
    return BookResponse.builder()
            .id(book.getId())
            .title(book.getTitle())
            .authorName(book.getAuthorName())
            .isbn(book.getIsbn())
            .synopsis(book.getSynopsis())
            .rate(book.getRate())
            .archived(book.isArchived())
            .shareable(book.isShaerable())
            .owner(book.getOwner().fullName())
            //.cover()
            .build();

    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
        return BorrowedBookResponse.builder()
                .id(history.getBook().getId())
                .title(history.getBook().getTitle())
                .authorName(history.getBook().getAuthorName())
                .isbn(history.getBook().getIsbn())
                .rate(history.getBook().getRate())
                .returned(history.isReturbed())
                .returnedAproved(history.isReturnedApproved())
                //.cover()
                .build();
    }
}
