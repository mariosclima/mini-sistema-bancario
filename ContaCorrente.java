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
    public boolean sacar(double valor) {
        if (!valorValido(valor) || valor > saldo + limiteChequeEspecial) return false;
        saldo -= valor;
        return true;
    }

    public double getLimiteChequeEspecial() { return limiteChequeEspecial; }

    public double getChequeEspecialUtilizado() {
        return Math.max(0, -saldo);
    }

    public double getLimiteDisponivel() {
        return Math.max(0, limiteChequeEspecial - getChequeEspecialUtilizado());
    }

    @Override
    public void exibirSaldo() {
        System.out.println("\n--- CONTA CORRENTE ---");
        System.out.println("Titular: " + getTitular());
        System.out.println("Número: " + getNumero());
        System.out.println("Saldo: " + dinheiro(getSaldo()));
        System.out.println("Cheque especial utilizado: " + dinheiro(getChequeEspecialUtilizado()));
        System.out.println("Limite disponível: " + dinheiro(getLimiteDisponivel()));
    }
}
