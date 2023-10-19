package br.com.filipemesel.aluguelSalas.reuniao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReuniaoRepository extends JpaRepository<Reuniao, UUID> {
    
}
