package Fo.Suzip.domain.serviceItem;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("movie")
public class Movie extends ServiceItem {

    private String director;
}
