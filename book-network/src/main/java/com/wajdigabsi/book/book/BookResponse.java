package com.wajdigabsi.book.book;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {


    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String owner;
    private byte[] cover;
    private double rate; //doit etre calculer dans book entity.
    private boolean archived;
    private boolean shareable;


}
