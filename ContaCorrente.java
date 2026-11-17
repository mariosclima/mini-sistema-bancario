public class ContaCorrente extends Conta {
    private double limiteChequeEspecial;

    public ContaCorrente(String titular, String numero, double saldoInicial, double limiteChequeEspecial) {
        super(titular, numero, saldoInicial);
        if (limiteChequeEspecial < 0) {
            throw new IllegalArgumentException("O limite do cheque especial não pode ser negativo.");
        }
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    @Override
    public void sacar(double valor) throws ValorInvalidoException, SaldoInsuficienteException {
        validarValorPositivo(valor);
        if (valor > saldo + limiteChequeEspecial) {
            throw new SaldoInsuficienteException(
                "Saldo e limite insuficientes. Disponível para saque: " + dinheiro(saldo + limiteChequeEspecial)
            );
        }
        saldo -= valor;
        registrarTransacao("Saque: -" + dinheiro(valor) + " | Saldo: " + dinheiro(saldo));
    }

    public double getLimiteChequeEspecial() { return limiteChequeEspecial; }

    public double getChequeEspecialUtilizado() {
        return Math.max(0, -saldo);
    }

    public double getLimiteDisponivel() {
        return Math.max(0, limiteChequeEspecial - getChequeEspecialUtilizado());
    }

    @Override
    public String getResumoDetalhes() {
        return "Tipo: Conta Corrente\n" +
               "Titular: " + getTitular() + "\n" +
               "Número: " + getNumero() + "\n" +
               "Saldo: " + dinheiro(getSaldo()) + "\n" +
               "Cheque especial utilizado: " + dinheiro(getChequeEspecialUtilizado()) + "\n" +
               "Limite disponível: " + dinheiro(getLimiteDisponivel());
    }
}
