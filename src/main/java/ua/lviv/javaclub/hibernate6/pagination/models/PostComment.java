package ua.lviv.javaclub.hibernate6.pagination.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "PostComment")
@Table(name = "post_comment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostComment {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String review;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
