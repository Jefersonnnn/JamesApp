# TODO:

## Requisitos e Funcionalidades da Aplicação

### Requisitos do Usuário

1. **Registro de Usuário:**
    - Os usuários devem poder se registrar na aplicação fornecendo um nome de usuário, email e senha.

2. **Login de Usuário:**
    - Os usuários devem poder fazer login na aplicação usando suas credenciais.

3. **Gerenciamento de Conta do Usuário:**
    - Os usuários devem poder atualizar suas informações de conta, como nome de usuário, email e senha.
    - Os usuários devem poder excluir suas contas, se desejarem.

### Gerenciamento de Clientes (Pessoas)

4. **Registro de Clientes:**
    - Os usuários devem poder adicionar informações de clientes, como nome e CPF.

5. **Recuperação de Clientes:**
    - Os usuários devem poder visualizar uma lista de todos os clientes (especificos do usuário) registrados na aplicação .

6. **Atualização de Clientes:**
    - Os usuários devem poder atualizar as informações dos clientes.

7. **Exclusão de Clientes:**
    - Os usuários devem poder excluir clientes quando necessário.

### Gerenciamento de Grupos

8. **Criação de Grupos:**
    - Os usuários devem poder criar grupos e atribuir nomes a eles, além de informações de pagamentos (data, valor).
    - Os usuários devem poder definir uma programação para execução automática dos pagamentos de grupo (JOB).

9. **Recuperação de Grupos:**
    - Os usuários devem poder ver uma lista de todos os grupos existentes (especificos do usuário).

10. **Atualização de Grupos:**
    - Os usuários devem poder atualizar informações sobre os grupos (especificos do usuário), como nome e programação de pagamento.

11. **Exclusão de Grupos:**
    - Os usuários devem poder excluir grupos quando necessário.

### Gerenciamento de Faturas (Bills)

12. **Criação de Faturas:**
    - Os usuários devem poder criar faturas associadas a um grupo, especificando o valor total, a data limite de pagamento e uma descrição.

13. **Recuperação de Faturas:**
    - Os usuários devem poder visualizar uma lista de todas as faturas criadas.

14. **Atualização de Faturas:**
    - Os usuários devem poder atualizar informações sobre as faturas, como o valor total, a data limite de pagamento e a descrição.

15. **Exclusão de Faturas:**
    - Os usuários devem poder excluir faturas quando necessário.

### Transações

Ainda pensar como uma transação irá vim de um webhook (de algum operador de pagamentos externo)

16. **Registro de Transações:**
    - Os usuários devem poder registrar transações, incluindo pagamentos e reembolsos, associados a faturas e clientes.

17. **Recuperação de Transações:**
    - Os usuários devem poder visualizar uma lista de todas as transações registradas.

18. **Cálculo Automático do Saldo:**
    - A aplicação deve calcular automaticamente o saldo de cada cliente com base nas transações registradas.

### Histórico de Participações

19. **Registro do Histórico de Participações:**
    - A aplicação deve manter um histórico das participações de clientes em grupos, incluindo datas de início e término de cada participação.

20. **Recuperação do Histórico de Participações:**
    - Os usuários devem poder visualizar o histórico de participações de um cliente em um grupo específico.

### JOB de Pagamento de Grupo

21. **Execução Automática do Pagamento de Grupo:**
    - A aplicação deve agendar um job que será executado periodicamente (por exemplo, mensalmente) para debitar o valor do grupo e atualizar o status dos clientes no grupo.

### Documentação da API

22. **Documentação de Rotas da API:**
    - A aplicação deve fornecer documentação clara das rotas da API, incluindo parâmetros, tipos de solicitação e exemplos de uso.

23. **Testes Automatizados:**
    - A aplicação deve incluir testes automatizados para garantir que todas as funcionalidades estejam funcionando corretamente.