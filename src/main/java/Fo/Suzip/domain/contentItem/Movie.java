package Fo.Suzip.domain.contentItem;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("movie")
public class Movie extends ContentItem {

    private String director;
}
