package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.DadosEndereco;

public record DadosDetalhamentoPaciente(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        DadosEndereco endereco,
        Boolean ativo) {

    public DadosDetalhamentoPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getEmail(), paciente.getTelefone(), new DadosEndereco(paciente.getEndereco()), paciente.getAtivo());
    }
}
