package com.wajdigabsi.book.history;

import com.wajdigabsi.book.book.Book;
import com.wajdigabsi.book.common.BaseEntity;
import com.wajdigabsi.book.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookTransactionHistory extends BaseEntity {


    @ManyToOne
    @JoinColumn(name ="user_id" )
     private User user;

    @ManyToOne
    @JoinColumn(name ="book_id" )
     private Book book;

    private boolean returbed;
    private boolean returnedApproved;
}
