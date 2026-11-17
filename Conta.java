import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public abstract class Conta {
    private String titular;
    private String numero;
    protected double saldo;
    protected List<String> extrato;

    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

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
        this.extrato = new ArrayList<>();
        registrarTransacao("Abertura de conta | Saldo inicial: " + dinheiro(saldoInicial));
    }

    public String getTitular() { return titular; }
    public String getNumero() { return numero; }
    public double getSaldo() { return saldo; }

    protected void validarValorPositivo(double valor) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor da operação deve ser maior que zero.");
        }
    }

    protected void registrarTransacao(String descricao) {
        String dataHora = LocalDateTime.now().format(FORMATADOR_DATA);
        extrato.add("[" + dataHora + "] " + descricao);
    }

    public List<String> getExtrato() {
        return Collections.unmodifiableList(extrato);
    }

    public String gerarExtratoTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== EXTRATO BANCÁRIO ===\n");
        sb.append("Titular: ").append(titular).append(" | Conta: ").append(numero).append("\n");
        sb.append("--------------------------------------------------\n");
        for (String linha : extrato) {
            sb.append(linha).append("\n");
        }
        sb.append("--------------------------------------------------\n");
        sb.append("Saldo Atual: ").append(dinheiro(saldo)).append("\n");
        return sb.toString();
    }

    public void depositar(double valor) throws ValorInvalidoException {
        validarValorPositivo(valor);
        saldo += valor;
        registrarTransacao("Depósito: +" + dinheiro(valor) + " | Saldo: " + dinheiro(saldo));
    }

    public abstract void sacar(double valor) throws ValorInvalidoException, SaldoInsuficienteException;

    public void transferir(Conta destino, double valor) 
            throws ValorInvalidoException, SaldoInsuficienteException, ContaDestinoInvalidaException {
        if (destino == null) {
            throw new ContaDestinoInvalidaException("A conta de destino não existe.");
        }
        if (destino instanceof ContaSalario) {
            throw new ContaDestinoInvalidaException("Transferências não são permitidas para Conta Salário.");
        }
        if (destino == this) {
            throw new ContaDestinoInvalidaException("Não é possível transferir para a mesma conta.");
        }

        this.sacar(valor);
        destino.receberTransferencia(this, valor);
        registrarTransacao("Transferência enviada para " + destino.getTitular() + " (" + destino.getNumero() + "): -" + dinheiro(valor));
    }

    protected void receberTransferencia(Conta origem, double valor) {
        saldo += valor;
        registrarTransacao("Transferência recebida de " + origem.getTitular() + " (" + origem.getNumero() + "): +" + dinheiro(valor));
    }

    public abstract String getResumoDetalhes();

    protected String dinheiro(double valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", valor);
    }

    @Override
    public String toString() {
        return numero + " - " + titular;
    }
}
