package ua.lviv.javaclub.hibernate6.examples.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bar {

    private UUID id;
    private String login;
    private String password;
}

