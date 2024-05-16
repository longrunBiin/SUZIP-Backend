package Fo.Suzip.domain.contentItem;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@DiscriminatorValue("music")
public class Music extends ContentItem {

    private String artist;
}
