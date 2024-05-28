package com.sideproject.shoescream.member.entity;

import com.sideproject.shoescream.bid.entity.Bid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Member implements UserDetails {

    @Id
    @Column(name = "member_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNumber;

    @Column(length = 100)
    private String memberId;

    @Column(length = 255)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 1000)
    private String profileImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Bid> bids;

    protected Member() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(memberNumber, member.memberNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberNumber);
    }
}
