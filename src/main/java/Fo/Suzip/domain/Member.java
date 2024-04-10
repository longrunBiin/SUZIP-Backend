package Fo.Suzip.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String userName;

    private String profileImage;

    private String email;

    private String userRole;

    private String provider;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDay;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Diary> diaryList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberItem> mList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberRecomendedItem> memberReconmmendedItemList = new ArrayList<>();

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "refresh_token_id")
//    private RefreshToken refreshToken;

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }
    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
