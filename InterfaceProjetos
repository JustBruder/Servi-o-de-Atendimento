import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InterfaceProjetos extends JFrame {

    // =================== CLASSE PESSOA ===================
    static class Pessoa implements Serializable {
        private String nome;
        private int idade;
        private String localizacao;
        private String telefone;
        private String genero;
        private boolean pcd;

        public Pessoa(String nome, int idade, String localizacao, String telefone, String genero, boolean pcd) {
            this.nome = nome;
            this.idade = idade;
            this.localizacao = localizacao;
            this.telefone = telefone;
            this.genero = genero;
            this.pcd = pcd;
        }

        public String getNome() { return nome; }
        public int getIdade() { return idade; }
        public String getLocalizacao() { return localizacao; }
        public String getTelefone() { return telefone; }
        public String getGenero() { return genero; }
        public boolean isPCD() { return pcd; }
    }

    // =================== CLASSE GERENCIADOR ===================
    static class GerenciadorProjetos implements Serializable {
        private String nomeProjeto;
        private List<Pessoa> inscritos;

        public GerenciadorProjetos(String nomeProjeto) {
            this.nomeProjeto = nomeProjeto;
            this.inscritos = new ArrayList<>();
        }

        public String getNomeProjeto() {
            return nomeProjeto;
        }

        public void inscreverPessoa(Pessoa p) {
            inscritos.add(p);
        }

        public List<Pessoa> getInscritos() {
            return inscritos;
        }

        public Pessoa buscarPorNome(String nome) {
            for (Pessoa p : inscritos) {
                if (p.getNome().equalsIgnoreCase(nome)) {
                    return p;
                }
            }
            return null;
        }

        public void removerPessoa(int indice) {
            if (indice >= 0 && indice < inscritos.size()) {
                inscritos.remove(indice);
            }
        }

        public String mostrarEstatisticas() {
            if (inscritos.isEmpty()) return "Nenhum inscrito.";

            int total = inscritos.size();
            long qtdPCD = inscritos.stream().filter(Pessoa::isPCD).count();
            double mediaIdade = inscritos.stream().mapToInt(Pessoa::getIdade).average().orElse(0);

            return "Projeto: " + nomeProjeto +
                    "\nTotal de inscritos: " + total +
                    "\nPCDs: " + qtdPCD +
                    "\nIdade média: " + String.format("%.1f", mediaIdade);
        }
    }

    // =================== INTERFACE ===================
    private List<GerenciadorProjetos> projetos;
    private JComboBox<String> comboProjetos;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoNomeProjeto;

    private static final String ARQUIVO = "projetos.dat";

    public InterfaceProjetos() {
        setTitle("Sistema de Gerenciamento de Projetos");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        projetos = carregarProjetos();

        // Painel superior com seleção de projeto
        JPanel painelTopo = new JPanel(new BorderLayout());
        JPanel painelProjetos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelProjetos.add(new JLabel("Projeto atual: "));

        comboProjetos = new JComboBox<>();
        comboProjetos.addActionListener(e -> atualizarTabela());
        painelProjetos.add(comboProjetos);

        campoNomeProjeto = new JTextField(15);
        painelProjetos.add(campoNomeProjeto);

        JButton btnNovoProjeto = new JButton("Adicionar Projeto");
        painelProjetos.add(btnNovoProjeto);

        painelTopo.add(painelProjetos, BorderLayout.CENTER);
        add(painelTopo, BorderLayout.NORTH);

        // Tabela de pessoas
        String[] colunas = {"Índice", "Nome", "Idade", "Localização", "Telefone", "Gênero", "PCD"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel inferior com botões
        JPanel painelBotoes = new JPanel(new GridLayout(1, 6));
        JButton btnInscrever = new JButton("Inscrever");
        JButton btnListar = new JButton("Listar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnRemover = new JButton("Remover");
        JButton btnEstatisticas = new JButton("Estatísticas");
        JButton btnSair = new JButton("Sair");

        painelBotoes.add(btnInscrever);
        painelBotoes.add(btnListar);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnEstatisticas);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.SOUTH);

        // Eventos
        btnNovoProjeto.addActionListener(this::criarNovoProjeto);
        btnInscrever.addActionListener(this::inscreverPessoa);
        btnListar.addActionListener(e -> atualizarTabela());
        btnBuscar.addActionListener(this::buscarPessoa);
        btnRemover.addActionListener(this::removerPessoa);
        btnEstatisticas.addActionListener(this::mostrarEstatisticas);
        btnSair.addActionListener(e -> {
            salvarProjetos();
            System.exit(0);
        });

        // Inicializa a lista de projetos carregados
        atualizarComboProjetos();
        atualizarTabela();
    }

    private GerenciadorProjetos getProjetoAtual() {
        int idx = comboProjetos.getSelectedIndex();
        if (idx >= 0 && idx < projetos.size()) {
            return projetos.get(idx);
        }
        return null;
    }

    private void criarNovoProjeto(ActionEvent e) {
        String nome = campoNomeProjeto.getText().trim();
        if (!nome.isEmpty()) {
            GerenciadorProjetos novo = new GerenciadorProjetos(nome);
            projetos.add(novo);
            salvarProjetos();
            atualizarComboProjetos();
            comboProjetos.setSelectedIndex(projetos.size() - 1);
            campoNomeProjeto.setText("");
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Digite um nome para o projeto!");
        }
    }

    private void inscreverPessoa(ActionEvent e) {
        GerenciadorProjetos projeto = getProjetoAtual();
        if (projeto == null) {
            JOptionPane.showMessageDialog(this, "Nenhum projeto selecionado!");
            return;
        }

        try {
            String nome = JOptionPane.showInputDialog(this, "Nome:");
            if (nome == null || nome.trim().isEmpty()) return;

            String idadeStr = JOptionPane.showInputDialog(this, "Idade:");
            int idade = Integer.parseInt(idadeStr);

            String localizacao = JOptionPane.showInputDialog(this, "Localização:");
            if (localizacao == null) localizacao = "";

            String telefone = JOptionPane.showInputDialog(this, "Telefone:");
            if (telefone == null) telefone = "";

            String genero = JOptionPane.showInputDialog(this, "Gênero:");
            if (genero == null) genero = "";

            int def = JOptionPane.showConfirmDialog(this, "É portador de deficiência?");
            boolean pcd = def == JOptionPane.YES_OPTION;

            Pessoa novaPessoa = new Pessoa(nome, idade, localizacao, telefone, genero, pcd);
            projeto.inscreverPessoa(novaPessoa);
            salvarProjetos();
            atualizarTabela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade inválida!");
        }
    }

    private void atualizarComboProjetos() {
        comboProjetos.removeAllItems();
        for (GerenciadorProjetos p : projetos) {
            comboProjetos.addItem(p.getNomeProjeto());
        }
    }

    private void atualizarTabela() {
        GerenciadorProjetos projeto = getProjetoAtual();
        modeloTabela.setRowCount(0);

        if (projeto != null) {
            List<Pessoa> pessoas = projeto.getInscritos();
            for (int i = 0; i < pessoas.size(); i++) {
                Pessoa p = pessoas.get(i);
                modeloTabela.addRow(new Object[]{
                        i,
                        p.getNome(),
                        p.getIdade(),
                        p.getLocalizacao(),
                        p.getTelefone(),
                        p.getGenero(),
                        p.isPCD() ? "Sim" : "Não"
                });
            }
        }
    }

    private void buscarPessoa(ActionEvent e) {
        GerenciadorProjetos projeto = getProjetoAtual();
        if (projeto == null) return;

        String nome = JOptionPane.showInputDialog(this, "Digite o nome para buscar:");
        if (nome == null || nome.trim().isEmpty()) return;

        Pessoa encontrada = projeto.buscarPorNome(nome);
        if (encontrada != null) {
            JOptionPane.showMessageDialog(this,
                    "Encontrado: " + encontrada.getNome() + " (" + encontrada.getIdade() + " anos)");
        } else {
            JOptionPane.showMessageDialog(this, "Pessoa não encontrada!");
        }
    }

    private void removerPessoa(ActionEvent e) {
        GerenciadorProjetos projeto = getProjetoAtual();
        if (projeto == null) return;

        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            projeto.removerPessoa(linha);
            salvarProjetos();
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa na tabela!");
        }
    }

    private void mostrarEstatisticas(ActionEvent e) {
        GerenciadorProjetos projeto = getProjetoAtual();
        if (projeto == null) return;

        String stats = projeto.mostrarEstatisticas();
        JOptionPane.showMessageDialog(this, stats);
    }

    // =================== PERSISTÊNCIA ===================
    private void salvarProjetos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(projetos);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar projetos: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<GerenciadorProjetos> carregarProjetos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<GerenciadorProjetos>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar projetos: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    // =================== MAIN ===================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceProjetos().setVisible(true));
    }
}
