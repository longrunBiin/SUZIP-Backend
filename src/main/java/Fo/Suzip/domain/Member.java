package Fo.Suzip.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String nickName;

    private String profileImage;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDay;
}
