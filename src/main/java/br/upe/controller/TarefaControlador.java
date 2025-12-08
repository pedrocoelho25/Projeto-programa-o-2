package br.upe.controller;
import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;
import br.upe.TarefaRepositorio;

import java.util.List;

public class TarefaControlador {

    private TarefaTableModel tarefaTableModel;
    private TarefaRepositorio repositorio;

    public TarefaControlador() {
        tarefaTableModel = new TarefaTableModel();
        repositorio = new TarefaRepositorio();

        carregarTarefas();
    }

    public void adicionarTarefaAtiva(Tarefa tarefa) {
        tarefaTableModel.getTarefasAtivas().add(tarefa);
        salvarTarefas();
        tarefaTableModel.fireTableDataChanged();
    }

    public void exibirFinalizadas(boolean exibir) {
        tarefaTableModel.setExibirFinalizadas(exibir);
    }

    public void salvarTarefas() {
        repositorio.salvar(
                tarefaTableModel.getTarefasAtivas(),
                tarefaTableModel.getTarefasFinalizadas()

        );
        tarefaTableModel.fireTableDataChanged();
    }
    public void removerTarefa(Tarefa tarefa) {

        if (tarefa.isFinalizada()) {
            tarefaTableModel.getTarefasFinalizadas().remove(tarefa);
        } else {
            tarefaTableModel.getTarefasAtivas().remove(tarefa);
        }


        salvarTarefas();


        tarefaTableModel.fireTableDataChanged();,
    }

    public void carregarTarefas() {

        List<List<Tarefa>> dados = repositorio.carregar();
        tarefaTableModel.setTarefasAtivas(dados.get(0));
        tarefaTableModel.setTarefasFinalizadas(dados.get(1))
        tarefaTableModel.fireTableDataChanged();
    }

    public TarefaTableModel getTarefaTableModel() {
        return tarefaTableModel;
    }
}
