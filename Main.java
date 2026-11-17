public class Main {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("    MINI SISTEMA BANCÁRIO - TESTES");
        System.out.println("====================================\n");

        try {
            // 1. Criação das contas
            ContaCorrente corrente = new ContaCorrente("Ana Souza", "001", 500.00, 300.00);
            ContaPoupanca poupanca = new ContaPoupanca("Bruno Lima", "002", 1000.00, 0.5);
            ContaSalario salario = new ContaSalario("Carla Santos", "003", 800.00);

            // 2. Testando Depósito
            System.out.println("--- TESTE DE DEPÓSITO ---");
            corrente.depositar(150.00);
            System.out.println("Depósito de R$ 150,00 realizado na conta corrente.");

            // 3. Testando Saque dentro do limite (com cheque especial)
            System.out.println("\n--- TESTE DE SAQUE (CONTA CORRENTE) ---");
            corrente.sacar(700.00);
            System.out.println("Saque de R$ 700,00 realizado com sucesso.");

            // 4. Testando Transferência permitida
            System.out.println("\n--- TESTE DE TRANSFERÊNCIA ---");
            corrente.transferir(poupanca, 100.00);
            System.out.println("Transferência de R$ 100,00 da Corrente para a Poupança realizada.");

            // 5. Testando Rendimento da Poupança
            System.out.println("\n--- TESTE DE RENDIMENTO ---");
            poupanca.aplicarRendimento();
            System.out.println("Rendimento de 0.5% aplicado na poupança.");

            // 6. Testes de Exceções (Erros esperados sendo capturados)
            System.out.println("\n--- TESTES DE EXCEÇÕES E BLOQUEIOS ---");

            try {
                System.out.println("Tentando transferir para uma Conta Salário (deve falhar)...");
                corrente.transferir(salario, 50.00);
            } catch (Exception e) {
                System.out.println("[EXCEÇÃO CAPTURADA COM SUCESSO]: " + e.getMessage());
            }

            try {
                System.out.println("Tentando sacar valor acima do saldo/limite na conta salário...");
                salario.sacar(1500.00);
            } catch (Exception e) {
                System.out.println("[EXCEÇÃO CAPTURADA COM SUCESSO]: " + e.getMessage());
            }

            try {
                System.out.println("Tentando depositar um valor inválido (negativo)...");
                corrente.depositar(-50.00);
            } catch (Exception e) {
                System.out.println("[EXCEÇÃO CAPTURADA COM SUCESSO]: " + e.getMessage());
            }

            // 7. Exibindo resumos e extratos finais
            System.out.println("\n====================================");
            System.out.println("       RESUMO E EXTRATOS FINAIS");
            System.out.println("====================================");
            
            System.out.println("\n" + corrente.getResumoDetalhes());
            System.out.println("\n" + corrente.gerarExtratoTexto());

            System.out.println("\n" + poupanca.getResumoDetalhes());
            System.out.println("\n" + poupanca.gerarExtratoTexto());

            System.out.println("\n" + salario.getResumoDetalhes());
            System.out.println("\n" + salario.gerarExtratoTexto());

        } catch (Exception e) {
            System.out.println("Erro inesperado na execução: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nFim da demonstração.");
    }
}
