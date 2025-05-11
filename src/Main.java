import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n=== Conversor de Moedas ===");
            System.out.println("1. Dólar → Real");
            System.out.println("2. Real → Dólar");
            System.out.println("3. Euro → Real");
            System.out.println("4. Real → Euro");
            System.out.println("5. Libra → Real");
            System.out.println("6. Real → Libra");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= 6) {
                System.out.print("Digite o valor: ");
                double valor = scanner.nextDouble();
                double convertido = 0;

                switch (opcao) {
                    case 1 -> convertido = Conversor.converter(valor, "USD", "BRL");
                    case 2 -> convertido = Conversor.converter(valor, "BRL", "USD");
                    case 3 -> convertido = Conversor.converter(valor, "EUR", "BRL");
                    case 4 -> convertido = Conversor.converter(valor, "BRL", "EUR");
                    case 5 -> convertido = Conversor.converter(valor, "GBP", "BRL");
                    case 6 -> convertido = Conversor.converter(valor, "BRL", "GBP");
                }

                System.out.printf("Valor convertido: %.2f\n", convertido);
            }

        } while (opcao != 0);

        System.out.println("Programa finalizado.");
        scanner.close();
    }
}
