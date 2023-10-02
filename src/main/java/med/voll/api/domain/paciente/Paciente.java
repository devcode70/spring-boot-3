package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name="pacientes")
@Entity(name="Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;


    public Paciente(DadosCadastroPaciente dadosPaciente) {
        this.ativo = true;
        this.nome = dadosPaciente.nome();
        this.cpf = dadosPaciente.cpf();
        this.email = dadosPaciente.email();
        this.telefone = dadosPaciente.telefone();
        this.endereco = new Endereco(dadosPaciente.endereco());
    }

    public void atualizarInformcao(DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {

        if(dadosAtualizacaoPaciente.nome() != null)
            this.nome = dadosAtualizacaoPaciente.nome();

        if(dadosAtualizacaoPaciente.telefone() != null)
            this.telefone = dadosAtualizacaoPaciente.telefone();

        if(dadosAtualizacaoPaciente.endereco() != null)
            this.endereco.atualizarInformcao(dadosAtualizacaoPaciente.endereco());
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
