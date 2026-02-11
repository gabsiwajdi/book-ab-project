package com.wajdigabsi.book.user;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;


    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;

}
