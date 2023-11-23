package ua.lviv.javaclub.hibernate6.pagination;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.javaclub.hibernate6.pagination.models.Post;
import ua.lviv.javaclub.hibernate6.pagination.models.PostComment;
import ua.lviv.javaclub.hibernate6.pagination.repository.PostRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/pagination")
public class PaginationController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/anti-pattern")
    public Page<Post> findAllByTitleAntiPattern(Pageable page) {
        long start = System.currentTimeMillis();
        Page<Post> createdOn = postRepository.findAllByTitleWithCommentsAntiPattern(
                "% %", page);
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        return createdOn;
    }

    @GetMapping("/window-function")
    public List<Post> findAllByTitleWindowFunction(@RequestParam String title) {
        long start = System.currentTimeMillis();
        List<Post> posts = postRepository.findFirstByTitleWithCommentsByTitle(
                "%" + title + "%",
                3
        );
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        return posts;
    }

    @GetMapping("/generate")
    public ResponseEntity<Void> generateFooData() {
        fillDatabase();
        return ResponseEntity.ok().build();
    }

    private void fillDatabase() {
        var executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100_010; i++) {
            var faker = new Faker();
            final Long postId = Long.valueOf(i);
            executorService.execute(() -> postRepository.save(genPost(postId, faker)));
        }

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(15, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Post genPost(Long postId, Faker faker) {
        return Post.builder()
                .id(postId)
                .title(faker.book().title())
                .createdOn(genDate())
                .comments(genComments(postId, faker))
                .build();
    }

    private static LocalDateTime genDate() {
        Instant startInclusive = Instant.now().minus(Duration.ofDays(3 * 365));
        Instant endExclusive = Instant.now().minus(Duration.ofDays(10));

        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long random = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(random), ZoneId.of("UTC"));
    }

    private List<PostComment> genComments(Long postId, Faker faker) {
        Integer count = faker.random().nextInt(1, 4);
        return IntStream.range(0, count)
                .mapToObj(i ->
                        PostComment.builder()
                                .id(((postId - 1) * count) + i)
                                .review(faker.lorem().sentence())
                                .createdOn(genDate())
                                .build())
                .toList();
    }
}
