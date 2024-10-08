package brianpelinku.u5w3d1.repositories;

import brianpelinku.u5w3d1.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Integer> {

    Optional<Dipendente> findByEmail(String email);
}
