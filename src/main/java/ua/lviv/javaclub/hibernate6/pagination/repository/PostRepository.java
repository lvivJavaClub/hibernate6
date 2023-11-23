package ua.lviv.javaclub.hibernate6.pagination.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.lviv.javaclub.hibernate6.pagination.models.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Query(
            value = """
        select p
        from Post p
        left join fetch p.comments
        where p.title like :titlePattern
        """,
            countQuery = """
        select count(p)
        from Post p
        where p.title like :titlePattern
        """
    )
    Page<Post> findAllByTitleWithCommentsAntiPattern(
            @Param("titlePattern") String titlePattern,
            Pageable pageable
    );

    @Query("""
    select p
    from Post p
    left join fetch p.comments pc
    where p.id in (
        select id
        from (
          select
             id as id,
             dense_rank() over (order by createdOn ASC) as ranking
          from Post
          where title like :titlePattern
        ) pr
        where ranking <= :maxCount
    )
    """
    )
    List<Post> findFirstByTitleWithCommentsByTitle(
            @Param("titlePattern") String titlePattern,
            @Param("maxCount") int maxCount
    );
}
