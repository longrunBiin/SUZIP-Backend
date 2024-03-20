package Fo.Suzip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String nickName;

    private String profileImage;

    private String email;

    private String password;

    @Setter
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberProvider provider;
//
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Diary> diaryList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<MemberItem> memberItemList = new ArrayList<>();
//
//    public void addDiary(Diary diary) {
//        if(!getDiaryList().contains(diary)){
//            getDiaryList().add(diary);
//        }
//        diary.setMember(this);
//    }
//
//    public void removeDiary(Diary diary) {
//        this.getDiaryList().remove(diary);
//        diary.setMember(null);
//    }
//
//    public void addMemberItem(MemberItem memberItem) {
//        if (!getMemberItemList().contains(memberItem)) {
//            getMemberItemList().add(memberItem);
//        }
//        memberItem.setMember(this);
//    }
//
//    public void removeMemberItem(MemberItem memberItem) {
//        this.getMemberItemList().remove(memberItem);
//        memberItem.setMember(null);
//    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.memberRole.getAuthority()));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
