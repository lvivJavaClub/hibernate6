package ua.lviv.javaclub.hibernate6.examples.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "countries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Column
    private String iso;

    @Column
    private String iso3;

    @Column(name = "iso_numeric")

    private Integer isoNumeric;
    @Column(name = "country_name")
    private String countryName;
    @Column
    private String capital;
    @Column(name = "continent_code")
    private String continentCode;
    @Column(name = "currency_code")
    private String currencyCode;
}
