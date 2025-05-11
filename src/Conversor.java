public class Conversor {
    public static double converter(double valor, String de, String para) {
        MoedaResponse resposta = ApiClient.getRates(de);
        if (resposta != null) {
            Double taxa = resposta.getConversion_rates().get(para);
            if (taxa != null) {
                return valor * taxa;
            } else {
                System.out.println("Moeda de destino inválida.");
            }
        } else {
            System.out.println("Erro ao buscar taxas de câmbio.");
        }
        return 0;
    }
}
