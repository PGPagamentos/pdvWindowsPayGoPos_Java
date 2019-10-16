# PayGo POS Java
Exemplo de integração em Java com a biblioteca Pay&Go para interação com POS.

## Ambiente e configuração da aplicação

Para o funcionamento da aplicação e correto reconhecimento da biblioteca de integração disponibilizada pela Pay&Go, algumas detalhes devem ser observados:
* JVM **32 bits**;
* Deve ser utilizado **Java 8**;
* O arquivo da *PTI_DLL.dll* (incluso no repositório) deve ser incluído na pasta raíz do projeto;
* A pasta do projeto deve possuir permissão de leitura e escrita;

---

**Obs:** O projeto deve ser importado como *Maven Project* dentro da sua IDE de preferência.

----

Ao executar a aplicação, será utilizado o mesmo diretório do projeto para salvar os arquivos relativos à comunição da Automação com a infraestrutura da Pay&Go na pasta __**PayGoPOS**__.

---

## Funcionalidades Implementadas Neste Exemplo:

- Inicialização - Configura Biblioteca de Integração;
- Conexão com múltiplos POS;
- Identificação do terminal conectado;
- Exibição de mensagens customizadas no display do POS;
- Menus e submenus de operações;
- Captura de dados do usuário (com e sem máscara);
- Emissão de QR Code e Código de Barras;
- Fluxo de venda e confirmação de transação (REDE, BIN e CIELO);
- Cancelamento de transações.

---

## Mais informações

- [Site Pay&Go](https://www.paygo.com.br)
- [dev@paygo.com.br](dev@paygo.com.br)
- 0800 737 2255 - opção 1