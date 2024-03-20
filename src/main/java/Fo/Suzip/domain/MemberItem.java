package Fo.Suzip.domain;

import Fo.Suzip.domain.serviceItem.ServiceItem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meber_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_item_id")
    private ServiceItem serviceItem;

    public void setMember(Member member) {
        if (member != null) {
//            this.member.getMemberItemList().remove(this);
        }
        this.member = member;
    }
}
