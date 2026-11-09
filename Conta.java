public abstract class Conta {
    private String titular;
    private String numero;
    protected double saldo;

    public Conta(String titular, String numero, double saldoInicial) {
        if (titular == null || titular.trim().isEmpty()) {
            throw new IllegalArgumentException("O titular deve ser informado.");
        }
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("O número da conta deve ser informado.");
        }
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo.");
        }
        this.titular = titular;
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    public String getTitular() { return titular; }
    public String getNumero() { return numero; }
    public double getSaldo() { return saldo; }

    protected boolean valorValido(double valor) { return valor > 0; }

    public boolean depositar(double valor) {
        if (!valorValido(valor)) return false;
        saldo += valor;
        return true;
    }

    public abstract boolean sacar(double valor);

    public boolean transferir(Conta destino, double valor) {
        if (destino == null || destino instanceof ContaSalario || destino == this || !valorValido(valor)) {
            return false;
        }
        if (!sacar(valor)) return false;
        destino.depositar(valor);
        return true;
    }

    public abstract void exibirSaldo();

    protected String dinheiro(double valor) {
        return String.format(java.util.Locale.forLanguageTag("pt-BR"), "R$ %.2f", valor);
    }
}
