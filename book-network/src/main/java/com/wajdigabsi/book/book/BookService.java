package com.wajdigabsi.book.book;


import com.wajdigabsi.book.common.PageResponse;
import com.wajdigabsi.book.history.BookTransactionHistory;
import com.wajdigabsi.book.history.BookTransactionHistoryRepository;
import com.wajdigabsi.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.wajdigabsi.book.book.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper ;
    private final BookRepository bookRepository;
    private final BookTransactionHistoryRepository transactionHistoryRepository;

    public Integer save(BookRequest request, Authentication connectedUser) {
        User user = ((User)connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setOwner(user);

        return bookRepository.save(book).getId();
    }

    public BookResponse findById(Integer bookId) {
        return  bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("no book found with the ID" +bookId));
    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User)connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findDisplayedBooks(pageable,user.getId());// recuperer tous les books de tous les useres expet connected uszer
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();

        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        ) ;
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {

        User user = ((User)connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()),pageable);

        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();

        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        ) ;

    }

    public PageResponse<BorrowedBookResponse> findAllBoroowedBooks(int page, int size, Authentication connectedUser) {

        User user = ((User)connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowdbooks = transactionHistoryRepository.findAllBorrowedBooks(pageable,user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowdbooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();

        return new PageResponse<>(
                bookResponses,
                allBorrowdbooks.getNumber(),
                allBorrowdbooks.getSize(),
                allBorrowdbooks.getTotalElements(),
                allBorrowdbooks.getTotalPages(),
                allBorrowdbooks.isFirst(),
                allBorrowdbooks.isLast()
        ) ;
    }
}
