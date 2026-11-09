public class Main {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("       MINI SISTEMA BANCÁRIO");
        System.out.println("====================================");

        ContaCorrente corrente = new ContaCorrente("Ana Souza", "001", 500.00, 300.00);
        ContaPoupanca poupanca = new ContaPoupanca("Bruno Lima", "002", 1000.00, 0.5);
        ContaSalario salario = new ContaSalario("Carla Santos", "003", 800.00);

        System.out.println("\n1) Depósito na conta corrente de R$ 150,00:");
        System.out.println(corrente.depositar(150.00) ? "Depósito realizado." : "Depósito não realizado.");

        System.out.println("\n2) Saque de R$ 700,00 na conta corrente:");
        System.out.println(corrente.sacar(700.00) ? "Saque realizado." : "Saldo/limite insuficiente.");

        System.out.println("\n3) Transferência de R$ 100,00 da conta corrente para a poupança:");
        System.out.println(corrente.transferir(poupanca, 100.00) ? "Transferência realizada." : "Transferência não permitida.");

        System.out.println("\n4) Tentativa de transferência para conta salário:");
        System.out.println(corrente.transferir(salario, 50.00) ? "Transferência realizada." : "Transferência bloqueada: conta salário não pode ser destino.");

        System.out.println("\n5) Aplicação do rendimento de 0,5% na poupança:");
        poupanca.aplicarRendimento();
        System.out.println("Rendimento aplicado.");

        System.out.println("\n6) Tentativa de saque acima do saldo na conta salário:");
        System.out.println(salario.sacar(900.00) ? "Saque realizado." : "Saque não permitido: saldo insuficiente.");

        corrente.exibirSaldo();
        poupanca.exibirSaldo();
        salario.exibirSaldo();

        System.out.println("\nFim da demonstração.");
    }
}
