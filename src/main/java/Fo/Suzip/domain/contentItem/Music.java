package Fo.Suzip.domain.contentItem;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("music")
public class Music extends ContentItem {

    private String artist;
}
