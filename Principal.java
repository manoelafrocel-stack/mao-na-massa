import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    public static void main(String[] args) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        // 3.1 – Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.84"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        // Remover o funcionário "João"
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        // Imprimir todos os funcionários
        System.out.println("--- LISTA DE FUNCIONÁRIOS ---");
        imprimirLista(funcionarios, df, nf);

        // Aumento de 10%
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.10"))));

        // Agrupar por função e imprimir
        System.out.println("\n--- FUNCIONÁRIOS AGRUPADOS POR FUNÇÃO ---");
        Map<String, List<Funcionario>> porFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        porFuncao.forEach((funcao, lista) -> {
            System.out.println("\n[ " + funcao + " ]");
            imprimirLista(lista, df, nf);
        });

        // Aniversariantes meses 10 e 12
        System.out.println("\n--- ANIVERSARIANTES DOS MESES 10 E 12 ---");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento().format(df)));

        // Maior idade
        System.out.println("\n--- FUNCIONÁRIO MAIS VELHO ---");
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Pessoa::getDataNascimento));
        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade + " anos");

        // Ordem alfabética
        System.out.println("\n--- FUNCIONÁRIOS EM ORDEM ALFABÉTICA ---");
        List<Funcionario> ordenados = funcionarios.stream()
                .sorted(Comparator.comparing(Pessoa::getNome))
                .collect(Collectors.toList());
        imprimirLista(ordenados, df, nf);

        // Total salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos Salários: " + nf.format(totalSalarios));

        // Quantidade salários mínimos 
        System.out.println("\n--- SALÁRIOS MÍNIMOS POR FUNCIONÁRIO ---");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal qtdSalarios = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha " + qtdSalarios + " salários mínimos.");
        });
    }

    private static void imprimirLista(List<Funcionario> lista, DateTimeFormatter df, NumberFormat nf) {
        lista.forEach(f -> System.out.println(
                "Nome: " + f.getNome() +
                " | Data Nasc: " + f.getDataNascimento().format(df) +
                " | Salário: " + nf.format(f.getSalario()) +
                " | Função: " + f.getFuncao()
        ));
    }
}