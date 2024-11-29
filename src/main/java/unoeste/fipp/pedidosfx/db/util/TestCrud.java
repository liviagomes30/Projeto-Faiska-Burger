package unoeste.fipp.pedidosfx.db.util;

import unoeste.fipp.pedidosfx.db.dal.IDAL;

import java.util.List;

public class TestCrud<T> {

    private IDAL<T> dal;
    private T entidade;

    public TestCrud(IDAL<T> dal, T entidade) {
        this.dal = dal;
        this.entidade = entidade;
    }

    // Testar Inserção
    public boolean testarInsercao() {
        System.out.println("Testando inserção...");
        boolean resultado = dal.gravar(entidade);
        if (resultado) {
            System.out.println("Inserção realizada com sucesso.");
        } else {
            System.out.println("Erro ao realizar inserção.");
        }
        return resultado;
    }

    // Testar Atualização
    public boolean testarAtualizacao() {
        System.out.println("Testando atualização...");
        boolean resultado = dal.alterar(entidade);
        if (resultado) {
            System.out.println("Atualização realizada com sucesso.");
        } else {
            System.out.println("Erro ao realizar atualização.");
        }
        return resultado;
    }

    // Testar Exclusão
    public boolean testarExclusao() {
        System.out.println("Testando exclusão...");
        boolean resultado = dal.apagar(entidade);
        if (resultado) {
            System.out.println("Exclusão realizada com sucesso.");
        } else {
            System.out.println("Erro ao realizar exclusão.");
        }
        return resultado;
    }

    // Testar Leitura (Recuperação)
    public boolean testarLeitura() {
        System.out.println("Testando leitura...");
        T entidadeRecuperada = dal.get((Integer) getId(entidade));
        if (entidadeRecuperada != null) {
            System.out.println("Leitura realizada com sucesso.");
            System.out.println("Entidade recuperada: " + entidadeRecuperada.toString());
        } else {
            System.out.println("Erro ao realizar leitura.");
        }
        return entidadeRecuperada != null;
    }

    // Testar Leitura com Filtro
    public boolean testarLeituraComFiltro(String filtro) {
        System.out.println("Testando leitura com filtro...");
        List<T> entidadesRecuperadas = dal.get(filtro);
        if (entidadesRecuperadas != null && !entidadesRecuperadas.isEmpty()) {
            System.out.println("Leitura com filtro realizada com sucesso.");
            entidadesRecuperadas.forEach(entidade -> System.out.println(entidade.toString()));
        } else {
            System.out.println("Erro ao realizar leitura com filtro.");
        }
        return entidadesRecuperadas != null && !entidadesRecuperadas.isEmpty();
    }

    // Método auxiliar para obter o ID da entidade (funciona apenas se a entidade tiver um método getId)
    private Object getId(T entidade) {
        try {
            return entidade.getClass().getMethod("getId").invoke(entidade);
        } catch (Exception e) {
            System.out.println("Erro ao acessar o método getId: " + e.getMessage());
            return null;
        }
    }

    // Executar todos os testes de CRUD
    public void executarTestes() {
        testarInsercao();
        testarLeitura();
        testarAtualizacao();
        testarExclusao();
    }
}
