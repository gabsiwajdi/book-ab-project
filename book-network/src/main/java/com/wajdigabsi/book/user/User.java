package com.wajdigabsi.book.user;

import com.wajdigabsi.book.book.Book;
import com.wajdigabsi.book.history.BookTransactionHistory;
import com.wajdigabsi.book.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name= " _user")
@EntityListeners(AuditingEntityListener.class)  // il faut ajouter enablejpaauditing dans main class
public class User implements UserDetails, Principal {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;
    private String firstname ;
    private String lastname ;
    private LocalDate dateOfBirth ;
    @Column(unique=true)
    private String email ;
    private String password ;
    private boolean accountLocked ;
    private boolean enabled ;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles ;

    @OneToMany(mappedBy = "owner")
    private List<Book> books ;

    @OneToMany(mappedBy = "user")
    private List<BookTransactionHistory> histories;


    @CreatedDate
    @Column(updatable=false,nullable=false)
    private LocalDate creationDate ;
    @LastModifiedDate
    @Column(insertable=false)
    private LocalDate lastModifiedDate ;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return email;
    }

    public String fullName() {
        return firstname + " " + lastname;
    }
}
