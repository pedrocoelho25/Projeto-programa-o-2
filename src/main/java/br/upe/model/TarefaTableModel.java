package br.upe.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TarefaTableModel extends AbstractTableModel {

    private List<Tarefa> tarefasAtivas;
    private List<Tarefa> tarefasFinalizadas;
    private boolean exibirFinalizadas;

    public TarefaTableModel() {
        tarefasAtivas = new ArrayList<>();
        tarefasFinalizadas = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return getTarefasExibidas().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarefa tarefa = getTarefasExibidas().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return tarefa.isFinalizada();
            case 1:
                return tarefa.getDescricao();
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return Boolean.class;
        if (columnIndex == 1) return String.class;
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex != 0) return;

        Tarefa tarefa = getTarefasExibidas().get(rowIndex);
        boolean novoStatus = (Boolean) aValue;

        tarefa.setFinalizada(novoStatus);

        if (novoStatus) {

            tarefasAtivas.remove(tarefa);
            tarefasFinalizadas.add(tarefa);
        } else {
            tarefasFinalizadas.remove(tarefa);
            tarefasAtivas.add(tarefa);
        }

        fireTableDataChanged();
    }


    public void setExibirFinalizadas(boolean selecionado) {
        this.exibirFinalizadas = selecionado;
        fireTableDataChanged();
    }


    private List<Tarefa> getTarefasExibidas() {
        if (exibirFinalizadas) {
            return tarefasFinalizadas;
        }
        return tarefasAtivas;
    }

    public Tarefa getTarefa(int indice) {
        List<Tarefa> tarefas = getTarefasExibidas();
        if (indice >= 0 && indice < tarefas.size()) {
            return tarefas.get(indice);
        }
        return null;
    }


    public List<Tarefa> getTarefasAtivas() {
        return tarefasAtivas;
    }

    public void setTarefasAtivas(List<Tarefa> tarefasAtivas) {
        this.tarefasAtivas = tarefasAtivas;
    }

    public List<Tarefa> getTarefasFinalizadas() {
        return tarefasFinalizadas;
    }

    public void setTarefasFinalizadas(List<Tarefa> tarefasFinalizadas) {
        this.tarefasFinalizadas = tarefasFinalizadas;
    }

    public boolean isExibirFinalizadas() {
        return exibirFinalizadas;
    }
}
