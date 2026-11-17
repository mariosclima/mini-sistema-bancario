import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaBancarioGUI extends JFrame {
    private List<Conta> contas;
    private JComboBox<Conta> comboContas;
    private JTextArea txtAreaDetalhes;

    public SistemaBancarioGUI() {
        setTitle("Mini Sistema Bancário - POO");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contas = new ArrayList<>();
        // Contas iniciais de teste
        try {
            contas.add(new ContaCorrente("Ana Souza", "001", 500.00, 300.00));
            contas.add(new ContaPoupanca("Bruno Lima", "002", 1000.00, 0.5));
            contas.add(new ContaSalario("Carla Santos", "003", 800.00));
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Painel Superior: Seleção e Criação de Contas
        JPanel panelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTopo.add(new JLabel("Conta Ativa:"));
        
        comboContas = new JComboBox<>(contas.toArray(new Conta[0]));
        comboContas.addActionListener(e -> atualizarDetalhes());
        panelTopo.add(comboContas);

        JButton btnCriarConta = new JButton("+ Nova Conta");
        btnCriarConta.addActionListener(e -> abrirJanelaCriarConta());
        panelTopo.add(btnCriarConta);

        add(panelTopo, BorderLayout.NORTH);

        // Painel Central: Detalhes da Conta / Saldo
        txtAreaDetalhes = new JTextArea();
        txtAreaDetalhes.setEditable(false);
        txtAreaDetalhes.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(txtAreaDetalhes);
        add(scrollPane, BorderLayout.CENTER);

        // Painel Inferior: Botões de Operações
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnDepositar = new JButton("Depositar");
        JButton btnSacar = new JButton("Sacar");
        JButton btnTransferir = new JButton("Transferir");
        JButton btnExtrato = new JButton("Ver Extrato");
        JButton btnRendimento = new JButton("Aplicar Rendimento");

        btnDepositar.addActionListener(e -> realizarDeposito());
        btnSacar.addActionListener(e -> realizarSaque());
        btnTransferir.addActionListener(e -> realizarTransferencia());
        btnExtrato.addActionListener(e -> exibirExtratoCompleto());
        btnRendimento.addActionListener(e -> aplicarRendimentoPoupanca());

        panelBotoes.add(btnDepositar);
        panelBotoes.add(btnSacar);
        panelBotoes.add(btnTransferir);
        panelBotoes.add(btnExtrato);
        panelBotoes.add(btnRendimento);

        add(panelBotoes, BorderLayout.SOUTH);

        atualizarDetalhes();
    }

    private void atualizarDetalhes() {
        Conta contaSelecionada = (Conta) comboContas.getSelectedItem();
        if (contaSelecionada != null) {
            txtAreaDetalhes.setText(contaSelecionada.getResumoDetalhes());
        } else {
            txtAreaDetalhes.setText("Nenhuma conta selecionada.");
        }
    }

    private void realizarDeposito() {
        Conta conta = (Conta) comboContas.getSelectedItem();
        if (conta == null) return;

        String valorStr = JOptionPane.showInputDialog(this, "Digite o valor do depósito (R$):");
        if (valorStr != null && !valorStr.trim().isEmpty()) {
            try {
                double valor = Double.parseDouble(valorStr.replace(",", "."));
                conta.depositar(valor);
                JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarDetalhes();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na Operação", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void realizarSaque() {
        Conta conta = (Conta) comboContas.getSelectedItem();
        if (conta == null) return;

        String valorStr = JOptionPane.showInputDialog(this, "Digite o valor do saque (R$):");
        if (valorStr != null && !valorStr.trim().isEmpty()) {
            try {
                double valor = Double.parseDouble(valorStr.replace(",", "."));
                conta.sacar(valor);
                JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarDetalhes();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Saldo / Limite Insuficiente", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void realizarTransferencia() {
        Conta origem = (Conta) comboContas.getSelectedItem();
        if (origem == null) return;

        List<Conta> destinos = new ArrayList<>(contas);
        destinos.remove(origem);

        if (destinos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há outras contas cadastradas para transferência.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conta destino = (Conta) JOptionPane.showInputDialog(
                this,
                "Selecione a conta de destino:",
                "Transferência Bancária",
                JOptionPane.QUESTION_MESSAGE,
                null,
                destinos.toArray(),
                destinos.get(0)
        );

        if (destino != null) {
            String valorStr = JOptionPane.showInputDialog(this, "Digite o valor da transferência (R$):");
            if (valorStr != null && !valorStr.trim().isEmpty()) {
                try {
                    double valor = Double.parseDouble(valorStr.replace(",", "."));
                    origem.transferir(destino, valor);
                    JOptionPane.showMessageDialog(this, "Transferência realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    atualizarDetalhes();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor, digite um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Transferência Bloqueada", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    private void aplicarRendimentoPoupanca() {
        Conta conta = (Conta) comboContas.getSelectedItem();
        if (conta instanceof ContaPoupanca) {
            ((ContaPoupanca) conta).aplicarRendimento();
            JOptionPane.showMessageDialog(this, "Rendimento aplicado com sucesso!", "Poupança", JOptionPane.INFORMATION_MESSAGE);
            atualizarDetalhes();
        } else {
            JOptionPane.showMessageDialog(this, "A conta selecionada não é uma Conta Poupança.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exibirExtratoCompleto() {
        Conta conta = (Conta) comboContas.getSelectedItem();
        if (conta != null) {
            JTextArea txtExtrato = new JTextArea(conta.gerarExtratoTexto());
            txtExtrato.setEditable(false);
            txtExtrato.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scroll = new JScrollPane(txtExtrato);
            scroll.setPreferredSize(new Dimension(480, 320));
            JOptionPane.showMessageDialog(this, scroll, "Extrato Bancário", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void abrirJanelaCriarConta() {
        String[] tipos = {"Conta Corrente", "Conta Poupança", "Conta Salário"};
        String tipoEscolhido = (String) JOptionPane.showInputDialog(
                this, "Selecione o tipo de conta:", "Nova Conta",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]
        );

        if (tipoEscolhido != null) {
            String titular = JOptionPane.showInputDialog(this, "Nome do Titular:");
            if (titular == null || titular.trim().isEmpty()) return;

            String numero = JOptionPane.showInputDialog(this, "Número da Conta:");
            if (numero == null || numero.trim().isEmpty()) return;

            String saldoStr = JOptionPane.showInputDialog(this, "Saldo Inicial (R$):");
            if (saldoStr == null || saldoStr.trim().isEmpty()) return;

            try {
                double saldoInicial = Double.parseDouble(saldoStr.replace(",", "."));
                Conta novaConta = null;

                if (tipoEscolhido.equals("Conta Corrente")) {
                    String limiteStr = JOptionPane.showInputDialog(this, "Limite de Cheque Especial (R$):");
                    double limite = Double.parseDouble(limiteStr.replace(",", "."));
                    novaConta = new ContaCorrente(titular, numero, saldoInicial, limite);
                } else if (tipoEscolhido.equals("Conta Poupança")) {
                    String taxaStr = JOptionPane.showInputDialog(this, "Taxa de Juros % (ex: 0.5):");
                    double taxa = Double.parseDouble(taxaStr.replace(",", "."));
                    novaConta = new ContaPoupanca(titular, numero, saldoInicial, taxa);
                } else if (tipoEscolhido.equals("Conta Salário")) {
                    novaConta = new ContaSalario(titular, numero, saldoInicial);
                }

                if (novaConta != null) {
                    contas.add(novaConta);
                    comboContas.setModel(new DefaultComboBoxModel<>(contas.toArray(new Conta[0])));
                    comboContas.setSelectedItem(novaConta);
                    JOptionPane.showMessageDialog(this, "Conta criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao criar conta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaBancarioGUI().setVisible(true);
        });
    }
}