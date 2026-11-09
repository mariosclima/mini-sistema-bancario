public class ContaSalario extends Conta {
    public ContaSalario(String titular, String numero, double saldoInicial) {
        super(titular, numero, saldoInicial);
    }

    @Override
    public boolean sacar(double valor) {
        if (!valorValido(valor) || valor > saldo) return false;
        saldo -= valor;
        return true;
    }

    @Override
    public void exibirSaldo() {
        System.out.println("\n--- CONTA SALÁRIO ---");
        System.out.println("Titular: " + getTitular());
        System.out.println("Número: " + getNumero());
        System.out.println("Saldo: " + dinheiro(getSaldo()));
        System.out.println("Sem cheque especial e sem rendimento.");
    }
}
