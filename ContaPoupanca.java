public class ContaPoupanca extends Conta {
    private double taxaJuros;

    public ContaPoupanca(String titular, String numero, double saldoInicial, double taxaJuros) {
        super(titular, numero, saldoInicial);
        if (taxaJuros < 0) {
            throw new IllegalArgumentException("A taxa de juros não pode ser negativa.");
        }
        this.taxaJuros = taxaJuros;
    }

    @Override
    public void sacar(double valor) throws ValorInvalidoException, SaldoInsuficienteException {
        validarValorPositivo(valor);
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente na poupança. Saldo atual: " + dinheiro(saldo));
        }
        saldo -= valor;
        registrarTransacao("Saque: -" + dinheiro(valor) + " | Saldo: " + dinheiro(saldo));
    }

    public double getTaxaJuros() { return taxaJuros; }

    public void aplicarRendimento() {
        if (saldo > 0) {
            double rendimento = saldo * (taxaJuros / 100.0);
            saldo += rendimento;
            registrarTransacao("Rendimento aplicado (" + taxaJuros + "%): +" + dinheiro(rendimento) + " | Saldo: " + dinheiro(saldo));
        }
    }

    @Override
    public String getResumoDetalhes() {
        return "Tipo: Conta Poupança\n" +
               "Titular: " + getTitular() + "\n" +
               "Número: " + getNumero() + "\n" +
               "Saldo: " + dinheiro(getSaldo()) + "\n" +
               "Taxa de juros: " + taxaJuros + "%";
    }
}
