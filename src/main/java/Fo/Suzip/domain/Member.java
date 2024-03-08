package Fo.Suzip.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String nickName;

    private String profileImage;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDay;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Diary> diaryList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberItem> memberItemList = new ArrayList<>();
}
