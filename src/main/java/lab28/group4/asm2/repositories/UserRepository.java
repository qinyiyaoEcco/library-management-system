package lab28.group4.asm2.repositories;

import lab28.group4.asm2.Role;
import lab28.group4.asm2.models.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    User findByUsername(String username);

    Long countUserByRole(Role role);
}
