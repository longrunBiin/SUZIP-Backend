package Fo.Suzip.domain.contentItem;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("music")
public class Music extends ContentItem {

    private String artist;
}
