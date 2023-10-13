package com.pedrycz.cinehub.model.entities;

import com.pedrycz.cinehub.model.enums.Role;
import com.pedrycz.cinehub.validation.Nickname;
import com.pedrycz.cinehub.validation.Password;
import com.pedrycz.cinehub.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Nickname
    private String nickname;

    @Email
    @UniqueEmail
    private String email;

    @Password
    private String password;

    private Role role;

    @ManyToMany
    @JoinTable(
            name = "watch_later_movie",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> watchLater;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Review> myReviews;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
