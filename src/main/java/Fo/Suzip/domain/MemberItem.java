package Fo.Suzip.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "meber_item_id")
    private Long id;

}
