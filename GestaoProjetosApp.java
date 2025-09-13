// Otherwise, add: package your.package.name;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Projeto {
    int id;
    String nome, descricao, local, equipe;
    String dataInicio, dataFim;
    java.util.List<Pessoa> participantes = new ArrayList<>();

    Projeto(int id, String nome, String descricao, String dataInicio, String dataFim, String local, String equipe) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.equipe = equipe;
    }
}

class Pessoa {
    int id;
    String nome, email, telefone, habilidades;
    java.util.List<Projeto> projetos = new ArrayList<>();

    Pessoa(int id, String nome, String email, String telefone, String habilidades) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.habilidades = habilidades;
    }
}

class Inscricao {
    int id;
    Projeto projeto;
    Pessoa pessoa;

    Inscricao(int id, Projeto projeto, Pessoa pessoa) {
        this.id = id;
        this.projeto = projeto;
        this.pessoa = pessoa;
    }
}

public class GestaoProjetosApp extends JFrame {

    private final java.util.List<Projeto> projetos = new java.util.ArrayList<>();
    private final java.util.List<Pessoa> pessoas = new java.util.ArrayList<>();
    private final java.util.List<Inscricao> inscricoes = new java.util.ArrayList<>();

    private DefaultTableModel projetosModel;
    private DefaultTableModel pessoasModel;
    private DefaultTableModel inscricoesModel;
    private JTextArea relatorioProjetos;
    private JTextArea relatorioPessoas;

    public GestaoProjetosApp() {
        setTitle("Sistema de Gestão de Projetos");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane abas = new JTabbedPane();

        // --- Aba Projetos ---
        JPanel projetosPanel = new JPanel(new BorderLayout());
        projetosModel = new DefaultTableModel(new String[]{"ID", "Nome", "Local", "Equipe"}, 0);
        JTable tabelaProjetos = new JTable(projetosModel);
        projetosPanel.add(new JScrollPane(tabelaProjetos), BorderLayout.CENTER);

        JButton btnAddProjeto = new JButton("Cadastrar Projeto");
        btnAddProjeto.addActionListener(e -> cadastrarProjeto());
        projetosPanel.add(btnAddProjeto, BorderLayout.SOUTH);

        abas.add("Projetos", projetosPanel);

        // --- Aba Pessoas ---
        JPanel pessoasPanel = new JPanel(new BorderLayout());
        pessoasModel = new DefaultTableModel(new String[]{"ID", "Nome", "Email"}, 0);
        JTable tabelaPessoas = new JTable(pessoasModel);
        pessoasPanel.add(new JScrollPane(tabelaPessoas), BorderLayout.CENTER);

        JButton btnAddPessoa = new JButton("Cadastrar Pessoa");
        btnAddPessoa.addActionListener(e -> cadastrarPessoa());
        pessoasPanel.add(btnAddPessoa, BorderLayout.SOUTH);

        abas.add("Pessoas", pessoasPanel);

        // --- Aba Inscrições ---
        JPanel inscricoesPanel = new JPanel(new BorderLayout());
        inscricoesModel = new DefaultTableModel(new String[]{"Projeto", "Pessoa"}, 0);
        JTable tabelaInscricoes = new JTable(inscricoesModel);
        inscricoesPanel.add(new JScrollPane(tabelaInscricoes), BorderLayout.CENTER);

        JButton btnAddInscricao = new JButton("Inscrever Pessoa");
        btnAddInscricao.addActionListener(e -> cadastrarInscricao());
        inscricoesPanel.add(btnAddInscricao, BorderLayout.SOUTH);

        abas.add("Inscrições", inscricoesPanel);

        // --- Aba Relatórios ---
        JPanel relatoriosPanel = new JPanel(new GridLayout(2, 1));

        relatorioProjetos = new JTextArea();
        relatorioProjetos.setEditable(false);
        relatorioProjetos.setBorder(BorderFactory.createTitledBorder("Projetos e Participantes"));

        relatorioPessoas = new JTextArea();
        relatorioPessoas.setEditable(false);
        relatorioPessoas.setBorder(BorderFactory.createTitledBorder("Pessoas e Projetos"));

        relatoriosPanel.add(new JScrollPane(relatorioProjetos));
        relatoriosPanel.add(new JScrollPane(relatorioPessoas));

        JButton btnAtualizarRelatorios = new JButton("Atualizar Relatórios");
        btnAtualizarRelatorios.addActionListener(e -> atualizarRelatorios());

        JPanel relatoriosContainer = new JPanel(new BorderLayout());
        relatoriosContainer.add(relatoriosPanel, BorderLayout.CENTER);
        relatoriosContainer.add(btnAtualizarRelatorios, BorderLayout.SOUTH);

        abas.add("Relatórios", relatoriosContainer);

        add(abas);
    }

    private void cadastrarProjeto() {
        JTextField nome = new JTextField();
        JTextField descricao = new JTextField();
        JTextField local = new JTextField();
        JTextField equipe = new JTextField();
        JTextField inicio = new JTextField();
        JTextField fim = new JTextField();

        Object[] msg = {
                "Nome:", nome,
                "Descrição:", descricao,
                "Local:", local,
                "Equipe:", equipe,
                "Data início:", inicio,
                "Data fim:", fim
        };

        int option = JOptionPane.showConfirmDialog(this, msg, "Novo Projeto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Projeto p = new Projeto(projetos.size() + 1, nome.getText(), descricao.getText(),
                    inicio.getText(), fim.getText(), local.getText(), equipe.getText());
            projetos.add(p);
            projetosModel.addRow(new Object[]{p.id, p.nome, p.local, p.equipe});
        }
    }

    private void cadastrarPessoa() {
        JTextField nome = new JTextField();
        JTextField email = new JTextField();
        JTextField telefone = new JTextField();
        JTextField habilidades = new JTextField();

        Object[] msg = {
                "Nome:", nome,
                "Email:", email,
                "Telefone:", telefone,
                "Habilidades:", habilidades
        };

        int option = JOptionPane.showConfirmDialog(this, msg, "Nova Pessoa", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            for (Pessoa existente : pessoas) {
                if (existente.email.equalsIgnoreCase(email.getText())) {
                    JOptionPane.showMessageDialog(this, "Email já cadastrado!");
                    return;
                }
            }
            Pessoa p = new Pessoa(pessoas.size() + 1, nome.getText(), email.getText(),
                    telefone.getText(), habilidades.getText());
            pessoas.add(p);
            pessoasModel.addRow(new Object[]{p.id, p.nome, p.email});
        }
    }

    private void cadastrarInscricao() {
        if (projetos.isEmpty() || pessoas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre pelo menos 1 projeto e 1 pessoa primeiro!");
            return;
        }

        String[] nomesProjetos = projetos.stream().map(p -> p.id + " - " + p.nome).toArray(String[]::new);
        String[] nomesPessoas = pessoas.stream().map(p -> p.id + " - " + p.nome).toArray(String[]::new);

        JComboBox<String> comboProjetos = new JComboBox<>(nomesProjetos);
        JComboBox<String> comboPessoas = new JComboBox<>(nomesPessoas);

        Object[] msg = {"Projeto:", comboProjetos, "Pessoa:", comboPessoas};

        int option = JOptionPane.showConfirmDialog(this, msg, "Nova Inscrição", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int projIndex = comboProjetos.getSelectedIndex();
            int pessIndex = comboPessoas.getSelectedIndex();

            Projeto projeto = projetos.get(projIndex);
            Pessoa pessoa = pessoas.get(pessIndex);

            for (Inscricao i : inscricoes) {
                if (i.projeto == projeto && i.pessoa == pessoa) {
                    JOptionPane.showMessageDialog(this, "Essa pessoa já está inscrita nesse projeto!");
                    return;
                }
            }

            Inscricao insc = new Inscricao(inscricoes.size() + 1, projeto, pessoa);
            inscricoes.add(insc);
            projeto.participantes.add(pessoa);
            pessoa.projetos.add(projeto);

            inscricoesModel.addRow(new Object[]{projeto.nome, pessoa.nome});
        }
    }

    private void atualizarRelatorios() {
        StringBuilder sbProjetos = new StringBuilder();
        for (Projeto p : projetos) {
            sbProjetos.append("[").append(p.id).append("] ").append(p.nome)
                    .append(" | Local: ").append(p.local != null ? p.local : "-")
                    .append(" | Participantes: ").append(p.participantes.size()).append("\n");
            for (Pessoa pessoa : p.participantes) {
                sbProjetos.append("   - ").append(pessoa.nome).append(" (").append(pessoa.email).append(")\n");
            }
            sbProjetos.append("\n");
        }
        relatorioProjetos.setText(sbProjetos.length() > 0 ? sbProjetos.toString() : "Nenhum projeto cadastrado.");

        StringBuilder sbPessoas = new StringBuilder();
        for (Pessoa pes : pessoas) {
            sbPessoas.append("[").append(pes.id).append("] ").append(pes.nome)
                    .append(" | Email: ").append(pes.email)
                    .append(" | Projetos: ").append(pes.projetos.size()).append("\n");
            for (Projeto prj : pes.projetos) {
                sbPessoas.append("   - ").append(prj.nome).append("\n");
            }
            sbPessoas.append("\n");
        }
        relatorioPessoas.setText(sbPessoas.length() > 0 ? sbPessoas.toString() : "Nenhuma pessoa cadastrada.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GestaoProjetosApp().setVisible(true);
        });
    }
}
