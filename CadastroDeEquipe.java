// Definir pacotes a serem usados
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Criar classe para Projetos
class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descricao;
    private String local;
    private String equipe;
    private String dataInicio;
    private String dataFim;
    private java.util.List<Pessoa> participantes = new ArrayList<>();

    public Projeto(int id, String nome, String descricao, String dataInicio, String dataFim, String local, String equipe) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.equipe = equipe;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getEquipe() { return equipe; }
    public void setEquipe(String equipe) { this.equipe = equipe; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public java.util.List<Pessoa> getParticipantes() { return participantes; }
    public void setParticipantes(java.util.List<Pessoa> participantes) { this.participantes = participantes; }
}

// Criar classe para Pessoas
class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String habilidades;
    private java.util.List<Projeto> projetos = new ArrayList<>();

    public Pessoa(int id, String nome, String email, String telefone, String habilidades) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.habilidades = habilidades;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public java.util.List<Projeto> getProjetos() { return projetos; }
    public void setProjetos(java.util.List<Projeto> projetos) { this.projetos = projetos; }
}

// Criar classe para Inscrições
class Inscricao implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Projeto projeto;
    private Pessoa pessoa;

    public Inscricao(int id, Projeto projeto, Pessoa pessoa) {
        this.id = id;
        this.projeto = projeto;
        this.pessoa = pessoa;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }
}

// Criar classe para Cadastro de Equipes
public class CadastroDeEquipe extends JFrame {

    private static final long serialVersionUID = 1L;

    private java.util.List<Projeto> projetos = new ArrayList<>();
    private java.util.List<Pessoa> pessoas = new ArrayList<>();
    private java.util.List<Inscricao> inscricoes = new ArrayList<>();

    private DefaultTableModel projetosModel;
    private DefaultTableModel pessoasModel;
    private DefaultTableModel inscricoesModel;
    private JTextArea relatorioProjetos;
    private JTextArea relatorioPessoas;

    private static final String ARQUIVO_PROJETOS = "projetos.dat";
    private static final String ARQUIVO_PESSOAS = "pessoas.dat";
    private static final String ARQUIVO_INSCRICOES = "inscricoes.dat";

    public CadastroDeEquipe() {
        setTitle("Sistema de Gestão de Projetos");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Carrega dados salvos (se existirem)
        carregarDados();

        JTabbedPane abas = new JTabbedPane();

        // Aba Projetos da interface
        JPanel projetosPanel = new JPanel(new BorderLayout());
        projetosModel = new DefaultTableModel(new String[]{"ID", "Nome", "Local", "Equipe"}, 0);
        JTable tabelaProjetos = new JTable(projetosModel);
        projetosPanel.add(new JScrollPane(tabelaProjetos), BorderLayout.CENTER);

        JButton btnAddProjeto = new JButton("Cadastrar Projeto");
        btnAddProjeto.addActionListener(e -> cadastrarProjeto());
        projetosPanel.add(btnAddProjeto, BorderLayout.SOUTH);

        abas.add("Projetos", projetosPanel);

        // Aba Pessoas da interface
        JPanel pessoasPanel = new JPanel(new BorderLayout());
        pessoasModel = new DefaultTableModel(new String[]{"ID", "Nome", "Email"}, 0);
        JTable tabelaPessoas = new JTable(pessoasModel);
        pessoasPanel.add(new JScrollPane(tabelaPessoas), BorderLayout.CENTER);

        JButton btnAddPessoa = new JButton("Cadastrar Pessoa");
        btnAddPessoa.addActionListener(e -> cadastrarPessoa());
        pessoasPanel.add(btnAddPessoa, BorderLayout.SOUTH);

        abas.add("Pessoas", pessoasPanel);

        // Aba Inscrições da interface
        JPanel inscricoesPanel = new JPanel(new BorderLayout());
        inscricoesModel = new DefaultTableModel(new String[]{"Projeto", "Pessoa"}, 0);
        JTable tabelaInscricoes = new JTable(inscricoesModel);
        inscricoesPanel.add(new JScrollPane(tabelaInscricoes), BorderLayout.CENTER);

        JButton btnAddInscricao = new JButton("Inscrever Pessoa");
        btnAddInscricao.addActionListener(e -> cadastrarInscricao());
        inscricoesPanel.add(btnAddInscricao, BorderLayout.SOUTH);

        abas.add("Inscrições", inscricoesPanel);

        // Aba Relatórios da interface
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

        // Preenche tabelas com os dados carregados
        atualizarTabelas();

        // Salvar ao fechar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                salvarDados();
            }
        });
    }

    // Persistencia de dados
    private void salvarDados() {
        salvarObjeto(projetos, ARQUIVO_PROJETOS);
        salvarObjeto(pessoas, ARQUIVO_PESSOAS);
        salvarObjeto(inscricoes, ARQUIVO_INSCRICOES);
    }

    private void carregarDados() {
        projetos = carregarObjeto(ARQUIVO_PROJETOS, new ArrayList<>());
        pessoas = carregarObjeto(ARQUIVO_PESSOAS, new ArrayList<>());
        inscricoes = carregarObjeto(ARQUIVO_INSCRICOES, new ArrayList<>());
    }

    private void salvarObjeto(Object obj, String arquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T carregarObjeto(String arquivo, T valorPadrao) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (T) in.readObject();
        } catch (Exception e) {
            return valorPadrao;
        }
    }

    // Atualizar tabelas
    private void atualizarTabelas() {
        // limpa antes de popular para evitar duplicação
        projetosModel.setRowCount(0);
        pessoasModel.setRowCount(0);
        inscricoesModel.setRowCount(0);

        for (Projeto p : projetos) {
            projetosModel.addRow(new Object[]{p.getId(), p.getNome(), p.getLocal(), p.getEquipe()});
        }
        for (Pessoa pes : pessoas) {
            pessoasModel.addRow(new Object[]{pes.getId(), pes.getNome(), pes.getEmail()});
        }
        for (Inscricao insc : inscricoes) {
            inscricoesModel.addRow(new Object[]{insc.getProjeto().getNome(), insc.getPessoa().getNome()});
        }
    }

    // Funções originais
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
            projetosModel.addRow(new Object[]{p.getId(), p.getNome(), p.getLocal(), p.getEquipe()});
            salvarDados();
        }
    }

    // Cadastrar Pessoas
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
                if (existente.getEmail().equalsIgnoreCase(email.getText())) {
                    JOptionPane.showMessageDialog(this, "Email já cadastrado!");
                    return;
                }
            }
            Pessoa p = new Pessoa(pessoas.size() + 1, nome.getText(), email.getText(),
                    telefone.getText(), habilidades.getText());
            pessoas.add(p);
            pessoasModel.addRow(new Object[]{p.getId(), p.getNome(), p.getEmail()});
            salvarDados();
        }
    }

    // Cadastrar Inscricoes
    private void cadastrarInscricao() {
        if (projetos.isEmpty() || pessoas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre pelo menos 1 projeto e 1 pessoa primeiro!");
            return;
        }

        String[] nomesProjetos = projetos.stream()
                .map(p -> p.getId() + " - " + p.getNome())
                .toArray(String[]::new);
        String[] nomesPessoas = pessoas.stream()
                .map(p -> p.getId() + " - " + p.getNome())
                .toArray(String[]::new);

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
                if (i.getProjeto() == projeto && i.getPessoa() == pessoa) {
                    JOptionPane.showMessageDialog(this, "Essa pessoa já está inscrita nesse projeto!");
                    return;
                }
            }

            Inscricao insc = new Inscricao(inscricoes.size() + 1, projeto, pessoa);
            inscricoes.add(insc);
            projeto.getParticipantes().add(pessoa);
            pessoa.getProjetos().add(projeto);

            inscricoesModel.addRow(new Object[]{projeto.getNome(), pessoa.getNome()});
            salvarDados();
        }
    }

    // Atualizar relatorios
    private void atualizarRelatorios() {
        StringBuilder sbProjetos = new StringBuilder();
        for (Projeto p : projetos) {
            sbProjetos.append("[").append(p.getId()).append("] ").append(p.getNome())
                    .append(" | Local: ").append(p.getLocal() != null ? p.getLocal() : "-")
                    .append(" | Participantes: ").append(p.getParticipantes().size()).append("\n");
            for (Pessoa pessoa : p.getParticipantes()) {
                sbProjetos.append("   - ").append(pessoa.getNome()).append(" (").append(pessoa.getEmail()).append(")\n");
            }
            sbProjetos.append("\n");
        }
        relatorioProjetos.setText(sbProjetos.length() > 0 ? sbProjetos.toString() : "Nenhum projeto cadastrado.");

        StringBuilder sbPessoas = new StringBuilder();
        for (Pessoa pes : pessoas) {
            sbPessoas.append("[").append(pes.getId()).append("] ").append(pes.getNome())
                    .append(" | Email: ").append(pes.getEmail())
                    .append(" | Projetos: ").append(pes.getProjetos().size()).append("\n");
            for (Projeto prj : pes.getProjetos()) {
                sbPessoas.append("   - ").append(prj.getNome()).append("\n");
            }
            sbPessoas.append("\n");
        }
        relatorioPessoas.setText(sbPessoas.length() > 0 ? sbPessoas.toString() : "Nenhuma pessoa cadastrada.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CadastroDeEquipe().setVisible(true);
        });
    }
}
