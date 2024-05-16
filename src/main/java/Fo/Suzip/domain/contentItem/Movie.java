package Fo.Suzip.domain.contentItem;

import Fo.Suzip.domain.Diary;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@DiscriminatorValue("movie")
public class Movie extends ContentItem {

    private String director;


}
