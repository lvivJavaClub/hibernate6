package ua.lviv.javaclub.hibernate6.multitenancy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TenantId;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Column
    private String name;

    @TenantId
    @Column
    private String tenant;
}