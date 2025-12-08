package br.upe;
import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class TarefaRepositorio {
    private static final String CAMINHO_ARQUIVO = "tarefas.dat";

    public void salvar(List<Tarefa> tarefasAtivas, List<Tarefa> tarefasFinalizadas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(tarefasAtivas);
            oos.writeObject(tarefasFinalizadas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<List<Tarefa>> carregar() {
        File file = new File(CAMINHO_ARQUIVO);


        if (!file.exists()) {
            List<List<Tarefa>> vazio = new ArrayList<>();
            vazio.add(new ArrayList<>());
            vazio.add(new ArrayList<>());
            return vazio;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Tarefa> ativas = (List<Tarefa>) ois.readObject();
            List<Tarefa> finalizadas = (List<Tarefa>) ois.readObject();

            List<List<Tarefa>> resultado = new ArrayList<>();
            resultado.add(ativas);
            resultado.add(finalizadas);
            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of(new ArrayList<>(), new ArrayList<>());
        }
    }

}
