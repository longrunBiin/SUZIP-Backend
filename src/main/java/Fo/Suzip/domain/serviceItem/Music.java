package Fo.Suzip.domain.serviceItem;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("music")
public class Music extends ServiceItem {

    private String artist;
}
