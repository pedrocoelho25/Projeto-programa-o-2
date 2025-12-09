package br.upe.controller;
import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;
import br.upe.TarefaRepositorio;

import java.util.ArrayList;
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


        tarefaTableModel.fireTableDataChanged();
    }

    public void carregarTarefas() {

        List<List<Tarefa>> dados = repositorio.carregar();

        List<Tarefa> ativas = new ArrayList<>();
        List<Tarefa> finalizadas = new ArrayList<>();



        List<Tarefa> listaAtivas = dados.get(0);
        List<Tarefa> listaFinalizadas = dados.get(1);

        if (listaAtivas != null) {
            for (Tarefa t : listaAtivas) {
                if (t != null) {
                    ativas.add(t);
                }
            }
        }

        if (listaFinalizadas != null) {
            for (Tarefa t : listaFinalizadas) {
                if (t != null) {
                    finalizadas.add(t);
                }
            }
        }

        tarefaTableModel.setTarefasAtivas(ativas);
        tarefaTableModel.setTarefasFinalizadas(finalizadas);
        tarefaTableModel.fireTableDataChanged();
    }


    public TarefaTableModel getTarefaTableModel() {
        return tarefaTableModel;
    }
}

