package Fo.Suzip.domain.contentItem;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("book")
@Getter
public class Book extends ContentItem {

    private String author;
}
