package br.com.filipemesel.aluguelSalas.reuniao;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_reuniao")
public class Reuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String loja;
    private String grau;
    private int Sala;
    private Date dataInicial;
    private Date dataFinal;
}
