package com.wajdigabsi.book.book;

import com.wajdigabsi.book.common.BaseEntity;
import com.wajdigabsi.book.feedback.Feedback;
import com.wajdigabsi.book.history.BookTransactionHistory;
import com.wajdigabsi.book.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shaerable;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;


    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;



    @Transient
    public double getRate(){
        if (feedbacks != null && feedbacks.isEmpty()){
            return 0.0;
        }
        var rate = this.feedbacks.stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
                double roundedRate = Math.round(rate * 10.0) / 10.0;
        return roundedRate;

    }

}
