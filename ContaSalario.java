public class ContaSalario extends Conta {
    public ContaSalario(String titular, String numero, double saldoInicial) {
        super(titular, numero, saldoInicial);
    }

    @Override
    public void sacar(double valor) throws ValorInvalidoException, SaldoInsuficienteException {
        validarValorPositivo(valor);
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta salário. Saldo atual: " + dinheiro(saldo));
        }
        saldo -= valor;
        registrarTransacao("Saque: -" + dinheiro(valor) + " | Saldo: " + dinheiro(saldo));
    }

    @Override
    public String getResumoDetalhes() {
        return "Tipo: Conta Salário\n" +
               "Titular: " + getTitular() + "\n" +
               "Número: " + getNumero() + "\n" +
               "Saldo: " + dinheiro(getSaldo()) + "\n" +
               "Observação: Sem cheque especial e sem rendimento.";
    }
}
