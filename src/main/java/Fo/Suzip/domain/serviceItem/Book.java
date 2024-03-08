package Fo.Suzip.domain.serviceItem;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("Book")
@Getter
public class Book extends ServiceItem {

    private String author;
}
