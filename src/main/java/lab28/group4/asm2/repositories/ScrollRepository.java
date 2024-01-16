package lab28.group4.asm2.repositories;

import lab28.group4.asm2.models.Scroll;
import lab28.group4.asm2.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScrollRepository extends ListCrudRepository<Scroll, Long> {
    Scroll findByName(String name);

    List<Scroll> findByUser(User user);

    @Query("SELECT s FROM Scroll s WHERE (:id IS NULL OR s.id = :id) AND (:name IS NULL OR s.name LIKE %:name%) AND (:userId IS NULL OR s.user.id = :userId) AND (:createdAt IS NULL OR FORMATDATETIME(s.createdAt, 'yyyy-MM-dd') = :createdAt)")
    List<Scroll> findScrolls(Long id, String name, Long userId, Date createdAt);
}