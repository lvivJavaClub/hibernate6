package ua.lviv.javaclub.hibernate6.examples.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ua.lviv.javaclub.hibernate6.examples.utils.CryptoUtils;

@Entity
@Table(name = "foos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Foo {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Integer id;

    @JdbcTypeCode(SqlTypes.JSON)
    private Bar bar;

    @PrePersist
    @PreUpdate
    private void encryptFields() {
        if (bar != null) {
            if (bar.getPassword() != null) {
                bar.setPassword(
                        CryptoUtils.encrypt(bar.getPassword())
                );
            }
        }
    }

    @PostLoad
    private void decryptFields() {
        if (bar != null) {
            if (bar.getPassword() != null) {
                bar.setPassword(
                        CryptoUtils.decrypt(bar.getPassword())
                );
            }
        }
    }
}

