package Fo.Suzip.domain.serviceItem;

import Fo.Suzip.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class ServiceItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "service_item_id")
    private Long id;

    private String name;

    private String content;

    private String image;

    private String genre;


}
