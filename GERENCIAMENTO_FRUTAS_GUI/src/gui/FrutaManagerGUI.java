package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrutaManagerGUI {
    // Lista para armazenar os nomes das frutas
    private ArrayList<String> frutas;
    // Modelo da lista para gerenciar a exibição de itens no JList
    private DefaultListModel<String> listModel;
    // JList para exibir as frutas na interface
    private JList<String> list;

    public FrutaManagerGUI() {
        // Inicializa a lista de frutas e o modelo da lista
        frutas = new ArrayList<>();
        listModel = new DefaultListModel<>();

        // Cria a janela principal com o título "Gerenciador de Frutas"
        JFrame frame = new JFrame("Gerenciador de Frutas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o programa ao fechar a janela
        frame.setSize(600, 300); // Define o tamanho da janela
        frame.setLayout(new BorderLayout()); // Define o layout da janela como BorderLayout

        // Painel para organizar os componentes de entrada e botões na parte superior
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // Layout que organiza os componentes em uma linha

        // Campo de texto onde o usuário insere o nome da fruta
        JTextField frutaField = new JTextField(15); // Campo de texto 
        panel.add(frutaField); // Adiciona o campo de texto ao painel

        // Botão para adicionar uma nova fruta a lista
        JButton addButton = new JButton("Adicionar");
        panel.add(addButton); // Adiciona o botão ao painel

        // Botão para modificar uma fruta selecionada (inicialmente desativado)
        JButton modifyButton = new JButton("Modificar");
        modifyButton.setEnabled(false); // Desativa o botão até que uma fruta seja selecionada
        panel.add(modifyButton); // Adiciona o botão ao painel

        // Botão para remover uma fruta selecionada (inicialmente desativado)
        JButton removeButton = new JButton("Remover");
        removeButton.setEnabled(false); // Desativa o botão até que uma fruta seja selecionada
        panel.add(removeButton); // Adiciona o botão ao painel

        // Adiciona o painel ao topo da janela
        frame.add(panel, BorderLayout.NORTH);

        // Cria uma lista visual para mostrar as frutas adicionadas
        list = new JList<>(listModel); // JList que usa o modelo listModel para exibir itens
        JScrollPane scrollPane = new JScrollPane(list); // Adiciona rolagem para a lista
        frame.add(scrollPane, BorderLayout.CENTER); // Posiciona a lista no centro da janela

        // Botão para listar todas as frutas adicionadas em um diálogo
        JButton listButton = new JButton("Listar Frutas");
        frame.add(listButton, BorderLayout.SOUTH); // Adiciona o botão na parte inferior da janela

        // Ação do botão "Adicionar" para inserir uma nova fruta na lista
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém o texto inserido no campo de texto
                String novaFruta = frutaField.getText();
                // Verifica se o campo de texto não está vazio
                if (!novaFruta.isEmpty()) {
                    frutas.add(novaFruta); // Adiciona a nova fruta à lista de frutas
                    listModel.addElement(novaFruta); // Adiciona a fruta ao modelo de lista para exibição
                    frutaField.setText(""); // Limpa o campo de texto após a adição
                    JOptionPane.showMessageDialog(frame, novaFruta + " foi adicionada.");
                } else {
                    // Exibe um alerta se o campo de texto estiver vazio
                    JOptionPane.showMessageDialog(frame, "Digite o nome de uma fruta.");
                }
            }
        });

        // Ação do botão "Modificar" para alterar a fruta selecionada na lista
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex(); // Indice da fruta selecionada
                if (selectedIndex != -1) { // Verifica se uma fruta está selecionada
                    // Obtém o nome da fruta selecionada
                    String frutaSelecionada = listModel.getElementAt(selectedIndex);
                    // Solicita um novo nome para a fruta selecionada
                    String novaFruta = JOptionPane.showInputDialog(frame, "Modificar " + frutaSelecionada + " para:", frutaSelecionada);
                    if (novaFruta != null && !novaFruta.isEmpty()) {
                        frutas.set(selectedIndex, novaFruta); // Atualiza a lista de frutas
                        listModel.set(selectedIndex, novaFruta); // Atualiza a exibição no modelo da lista
                        JOptionPane.showMessageDialog(frame, "Fruta " + frutaSelecionada + " foi modificada para " + novaFruta);
                    }
                } else {
                    // Exibe uma mensagem de erro se nenhuma fruta estiver selecionada
                    JOptionPane.showMessageDialog(frame, "Selecione uma fruta para modificar.");
                }
            }
        });

        // Ação do botão "Remover" para excluir a fruta selecionada na lista
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex(); // Índice da fruta selecionada
                if (selectedIndex != -1) { // Verifica se uma fruta está selecionada
                    String frutaRemovida = frutas.remove(selectedIndex); // Remove a fruta da lista de frutas
                    listModel.remove(selectedIndex); // Remove a fruta do modelo da lista para atualizar a exibição
                    JOptionPane.showMessageDialog(frame, frutaRemovida + " foi removida.");
                } else {
                    // Exibe uma mensagem de erro se nenhuma fruta estiver selecionada
                    JOptionPane.showMessageDialog(frame, "Selecione uma fruta para remover.");
                }
            }
        });

        // Ação do botão "Listar Frutas" para exibir todas as frutas na lista
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frutas.isEmpty()) {
                    // Exibe mensagem caso a lista esteja vazia
                    JOptionPane.showMessageDialog(frame, "Nenhuma fruta na lista.");
                } else {
                    // Exibe as frutas na lista em um diálogo
                    JOptionPane.showMessageDialog(frame, "Frutas: " + frutas);
                }
            }
        });

        // Listener para habilitar ou desabilitar os botões "Modificar" e "Remover"
        // com base na seleção de um item na lista
        list.addListSelectionListener(e -> {
            boolean selectionExists = !list.isSelectionEmpty(); // Verifica se há seleção
            removeButton.setEnabled(selectionExists); // Habilita o botão "Remover" se houver seleção
            modifyButton.setEnabled(selectionExists); // Habilita o botão "Modificar" se houver seleção
        });

        // Torna a janela visível
        frame.setVisible(true);
    }

    // Método para iniciar a aplicação
    public static void main(String[] args) {
        // Garante que a interface seja criada 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrutaManagerGUI(); // Cria uma nova instância da interface gráfica
            }
        });
    }
}
