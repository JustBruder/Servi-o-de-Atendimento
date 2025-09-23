# Serviço de Gestão de Projetos e Equipes
- Serviço de atendimento com cadastro de projetos e equipes correspondentes.

Este projeto é um **sistema de gestão de projetos e equipes** simples, desenvolvido em **Java**, que permite cadastrar **pessoas** e vinculá-las a **projetos**. O sistema segue a cultura organizacional da Oracle, mirando a qualidade e eficiência na gestão de seus projetos e equipes. Entretanto, o sistema pode ser usado para fins acadêmicos, projetos de extensão ou como base para aplicações mais complexas.

<img width="998" height="770" alt="Screenshot 2025-09-11 210142" src="https://github.com/user-attachments/assets/658cfce4-aa11-4ced-8bca-854d50cc0c25" />

---

# Funcionalidades
- Cadastrar pessoas com as seguintes informações:
  - Nome
  - Idade
  - Localização
  - Telefone
  - Gênero
  - Equipe
  - Portador de deficiência (Sim/Não)

- Cadastrar projetos e equipes com:
  - Nome do projeto
  - Data
  - Local
  - Equipe
  - Inscrever pessoas em projetos.
  - Listar pessoas cadastradas.
  - Listar projetos cadastrados.
  - Exibir os participantes de cada projeto e equipe.

---

# Tecnologias Utilizadas

- **Java** (versão 8+)
- Eclipse
- VSCode
- Programação Orientada a Objetos (POO)
- Estruturas de dados (listas)
- Servidores
- Banco de dados
- SGBD
- Computadores e periféricos
- Celulares e/ou tablets 
- Redes entre membros de equipe e entre a equipe e o servidor com o banco de dados
- Edição e Revisão do código pelo CHATGPT e Microsoft Copilot

---

# TUTORIAL DE USO

- Abra o arquivo "CadastroDeEquipe.java" no seu IDE Java (e.g. VSCode, Eclipse) e rode o código.

<img width="877" height="589" alt="image" src="https://github.com/user-attachments/assets/33975148-97c0-44f0-9a5d-f909bb0abc22" />

- Escreva um nome para um novo projeto (e.g. ProjetoExemplo)
- Adicione uma pessoa (e suas informações)
- Inscreva uma pessoa a algum projeto

<img width="875" height="585" alt="image" src="https://github.com/user-attachments/assets/2ba5652a-aa07-49c2-8a9d-1d16ef2c2ea5" />

Seus projetos e pessoas, incluindo suas variáveis string e numéricas, continuarão salvos em arquivos CSV (memória persistente), constituindo 3 tabelas: 

<img width="870" height="161" alt="image" src="https://github.com/user-attachments/assets/e8df70eb-0e32-466e-8939-8333ea929a1e" />

O usuário pode editar as tabelas manualmente, e o gerenciador vai entender as modificações quando ele for inicializado.

---

# USO DOS ARQUIVOS EM SGBDs (e.g. PostgreSQL)

- Para criar, compartilhar e usar um banco de dados com as 3 tabelas usadas no gerenciador de projetos:

- No SGBD, crie as tabelas "projetos", "pessoas", e "inscricoes":

CREATE TABLE projetos(
    id VARCHAR (50) PRIMARY KEY,
    nome VARCHAR (50) UNIQUE,
	descricao VARCHAR (50),
	dataInicio VARCHAR (50),
	dataFim VARCHAR (50),
	local VARCHAR (50),
	equipe VARCHAR (50)
);

CREATE TABLE pessoas(
    id VARCHAR (50) PRIMARY KEY,
    nome VARCHAR (50) UNIQUE,
	email VARCHAR (50),
	telefone VARCHAR (50),
	habilidades VARCHAR (50)
);

CREATE TABLE inscricoes(
    id VARCHAR (50) PRIMARY KEY,
    idProjeto VARCHAR (50),
	idPessoa VARCHAR (50)
);


- Para adicionar dados nas tabelas, use o INSERT INTO. Exemplos:

INSERT INTO projetos
VALUES (1, 'Projeto1', 'Analises', '1/1/2026', '5/5/2026','Goiania', 'Equipe1');

INSERT INTO pessoas
VALUES (1, 'Joao', 'joao@email.com', '62982269900', 'Excel');

INSERT INTO inscricoes
VALUES (1, '1', '1');


- Para visualizar as tabelas no SGBD:

SELECT * FROM projetos;
SELECT * FROM pessoas;
SELECT * FROM inscricoes;


- Para usar os dados das tabelas dentro do SGBD, exporte cada tabela (no postgreSQL, 'Save results to file'), exportando na extensão ".csv":

<img width="1107" height="591" alt="Screenshot 2025-09-22 233303" src="https://github.com/user-attachments/assets/33fc6c16-7e2d-4597-9e16-3f34a83b683f" />

- Abra cada tabela no Excel e passe tudo de "texto para colunas":

<img width="1374" height="697" alt="Screenshot 2025-09-22 233134" src="https://github.com/user-attachments/assets/ffdb9f5b-bda4-4cc8-895e-440ee6bcd09a" />

- Escolha "vírgula" como delimitador:

<img width="749" height="533" alt="Screenshot 2025-09-22 233350" src="https://github.com/user-attachments/assets/64149486-d6e8-4dc5-b5f1-835f6e3792c2" />

- Deve ficar mais ou menos assim no Excel:

<img width="1375" height="699" alt="Screenshot 2025-09-22 233200" src="https://github.com/user-attachments/assets/ea2e02a2-2b1d-4e90-91f0-dc702e0f01e8" />

- Tenha certeza de que as tabelas se chamam "projetos.csv", "pessoas.csv" e "inscricoes.csv". Ao carregar o gerenciador, ele vai ler as modificações:

<img width="880" height="587" alt="image" src="https://github.com/user-attachments/assets/cb9b19a0-6c09-439a-9fb1-e688591cd3d5" />


- Com isso, podemos editar e compartilhar os dados dos projetos, pessoas e inscrições usando bancos de dados dentro de SGBDs. Como já indicado acima, para usar as informações dentro do nosso gerenciador de projeto, basta extrair as tabelas como csv, passar de texto para colunas dentro do Excel, e usar os nomes corretos nos arquivos.


---

# NOTA PARA USUÁRIOS DE VSCODE:

- Não esqueça de instalar as extensões para poder trabalhar em Java:

<img width="917" height="488" alt="image" src="https://github.com/user-attachments/assets/00710e2a-ea99-445a-8403-1a074f725ba3" />

