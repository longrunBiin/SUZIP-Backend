package Fo.Suzip.domain.contentItem;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("movie")
public class Movie extends ContentItem {

    private String director;
}
