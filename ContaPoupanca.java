public class ContaPoupanca extends Conta {
    private double taxaJuros;

    // A taxa é informada em percentual. Exemplo: 0.5 representa 0,5%.
    public ContaPoupanca(String titular, String numero, double saldoInicial, double taxaJuros) {
        super(titular, numero, saldoInicial);
        if (taxaJuros < 0) {
            throw new IllegalArgumentException("A taxa de juros não pode ser negativa.");
        }
        this.taxaJuros = taxaJuros;
    }

    @Override
    public boolean sacar(double valor) {
        if (!valorValido(valor) || valor > saldo) return false;
        saldo -= valor;
        return true;
    }

    public double getTaxaJuros() { return taxaJuros; }

    public void aplicarRendimento() {
        if (saldo > 0) {
            saldo += saldo * (taxaJuros / 100.0);
        }
    }

    @Override
    public void exibirSaldo() {
        System.out.println("\n--- CONTA POUPANÇA ---");
        System.out.println("Titular: " + getTitular());
        System.out.println("Número: " + getNumero());
        System.out.println("Saldo: " + dinheiro(getSaldo()));
        System.out.println("Taxa de juros: " + taxaJuros + "%");
    }
}
