
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
// Classe para representar uma pessoa inscrita no evento
class Pessoa {
    private String nome;
    private int idade;
    private String localizacao;
    private String telefone;
    private String genero;
    private boolean portadorDeficiencia;
    
    // Construtor
    public Pessoa(String nome, int idade, String localizacao, String telefone, 
                  String genero, boolean portadorDeficiencia) {
        this.nome = nome;
        this.idade = idade;
        this.localizacao = localizacao;
        this.telefone = telefone;
        this.genero = genero;
        this.portadorDeficiencia = portadorDeficiencia;
    }
    
    // Getters
    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getLocalizacao() { return localizacao; }
    public String getTelefone() { return telefone; }
    public String getGenero() { return genero; }
    public boolean isPortadorDeficiencia() { return portadorDeficiencia; }
    
    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setIdade(int idade) { this.idade = idade; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setPortadorDeficiencia(boolean portadorDeficiencia) { 
        this.portadorDeficiencia = portadorDeficiencia; 
    }
    
    @Override
    public String toString() {
        return String.format(
            "Nome: %s | Idade: %d | Localização: %s | Telefone: %s | Gênero: %s | PcD: %s",
            nome, idade, localizacao, telefone, genero, 
            portadorDeficiencia ? "Sim" : "Não"
        );
    }
}

// Classe para gerenciar as inscrições do evento
class GerenciadorEventos {
    private final List<Pessoa> inscricoes;
    @SuppressWarnings("FieldMayBeFinal")
    private String nomeEvento;
    
    public GerenciadorEventos(String nomeEvento) {
        this.nomeEvento = nomeEvento;
        this.inscricoes = new ArrayList<>();
    }
    
    // Inscrever uma pessoa no evento
    public boolean inscreverPessoa(Pessoa pessoa) {
        // Verificar se já existe uma pessoa com mesmo nome e telefone
        for (Pessoa p : inscricoes) {
            if (p.getNome().equalsIgnoreCase(pessoa.getNome()) && 
                p.getTelefone().equals(pessoa.getTelefone())) {
                System.out.println("Erro: Pessoa já inscrita no evento!");
                return false;
            }
        }
        
        inscricoes.add(pessoa);
        System.out.println("Pessoa inscrita com sucesso!");
        return true;
    }
    
    // Listar todas as inscrições
    public void listarInscricoes() {
        if (inscricoes.isEmpty()) {
            System.out.println("Nenhuma inscrição encontrada.");
            return;
        }
        
        System.out.println("\n=== INSCRIÇÕES DO EVENTO: " + nomeEvento + " ===");
        for (int i = 0; i < inscricoes.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, inscricoes.get(i));
        }
        System.out.println("Total de inscritos: " + inscricoes.size());
    }
    
    // Buscar pessoa por nome
    public void buscarPorNome(String nome) {
        List<Pessoa> encontradas = new ArrayList<>();
        
        for (Pessoa pessoa : inscricoes) {
            if (pessoa.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontradas.add(pessoa);
            }
        }
        
        if (encontradas.isEmpty()) {
            System.out.println("Nenhuma pessoa encontrada com esse nome.");
        } else {
            System.out.println("\n=== RESULTADOS DA BUSCA ===");
            for (int i = 0; i < encontradas.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, encontradas.get(i));
            }
        }
    }
    
    // Remover pessoa por índice
    public boolean removerPessoa(int indice) {
        if (indice < 1 || indice > inscricoes.size()) {
            System.out.println("Índice inválido!");
            return false;
        }
        
        Pessoa removida = inscricoes.remove(indice - 1);
        System.out.println("Pessoa removida: " + removida.getNome());
        return true;
    }
    
    // Estatísticas do evento
    public void mostrarEstatisticas() {
        if (inscricoes.isEmpty()) {
            System.out.println("Nenhuma inscrição para mostrar estatísticas.");
            return;
        }
        
        System.out.println("\n=== ESTATÍSTICAS DO EVENTO ===");
        System.out.println("Total de inscritos: " + inscricoes.size());
        
        // Contagem por gênero
        Map<String, Integer> contadorGenero = new HashMap<>();
        int pcd = 0;
        int somaIdades = 0;
        
        for (Pessoa pessoa : inscricoes) {
            contadorGenero.put(pessoa.getGenero(), 
                contadorGenero.getOrDefault(pessoa.getGenero(), 0) + 1);
            
            if (pessoa.isPortadorDeficiencia()) {
                pcd++;
            }
            
            somaIdades += pessoa.getIdade();
        }
        
        System.out.println("\nDistribuição por gênero:");
        for (Map.Entry<String, Integer> entry : contadorGenero.entrySet()) {
            System.out.printf("- %s: %d pessoas\n", entry.getKey(), entry.getValue());
        }
        
        System.out.printf("\nPessoas com deficiência: %d (%.1f%%)\n", 
            pcd, (pcd * 100.0) / inscricoes.size());
        
        System.out.printf("Idade média: %.1f anos\n", 
            (double) somaIdades / inscricoes.size());
    }
    
    public int getTotalInscricoes() {
        return inscricoes.size();
    }
}

// Classe principal com interface de usuário
public class SistemaGerenciamentoEventos {
    private static final Scanner scanner = new Scanner(System.in);
    private static GerenciadorEventos gerenciador;
    
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GERENCIAMENTO DE EVENTOS ===");
        System.out.print("Digite o nome do evento: ");
        String nomeEvento = scanner.nextLine();
        
        gerenciador = new GerenciadorEventos(nomeEvento);
        
        while (true) {
            mostrarMenu();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1 -> inscreverNovaPessoa();
                case 2 -> gerenciador.listarInscricoes();
                case 3 -> buscarPessoa();
                case 4 -> removerPessoa();
                case 5 -> gerenciador.mostrarEstatisticas();
                case 6 -> {
                    System.out.println("Encerrando sistema...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
            
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MENU PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1. Inscrever nova pessoa");
        System.out.println("2. Listar todas as inscrições");
        System.out.println("3. Buscar pessoa por nome");
        System.out.println("4. Remover inscrição");
        System.out.println("5. Mostrar estatísticas");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void inscreverNovaPessoa() {
        System.out.println("\n=== NOVA INSCRIÇÃO ===");
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Idade: ");
        int idade;
        try {
            idade = Integer.parseInt(scanner.nextLine());
            if (idade < 0 || idade > 150) {
                System.out.println("Idade inválida!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Idade inválida!");
            return;
        }
        
        System.out.print("Localização (cidade): ");
        String localizacao = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        
        System.out.print("É portador de deficiência? (s/n): ");
        String resposta = scanner.nextLine().toLowerCase();
        boolean portadorDeficiencia = resposta.equals("s") || resposta.equals("sim");
        
        Pessoa novaPessoa = new Pessoa(nome, idade, localizacao, telefone, 
                                     genero, portadorDeficiencia);
        gerenciador.inscreverPessoa(novaPessoa);
    }
    
    private static void buscarPessoa() {
        System.out.print("Digite o nome para buscar: ");
        String nome = scanner.nextLine();
        gerenciador.buscarPorNome(nome);
    }
    
    private static void removerPessoa() {
        if (gerenciador.getTotalInscricoes() == 0) {
            System.out.println("Nenhuma inscrição para remover.");
            return;
        }
        
        gerenciador.listarInscricoes();
        System.out.print("Digite o número da pessoa a remover: ");
        
        try {
            int indice = Integer.parseInt(scanner.nextLine());
            gerenciador.removerPessoa(indice);
        } catch (NumberFormatException e) {
            System.out.println("Número inválido!");
        }
    }
}