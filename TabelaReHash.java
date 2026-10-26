import java.util.Random;

public class TabelaReHash {
    int[] tabela;
    int tamanho;
    int qtd_itens;

    public TabelaReHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = -1;
        }
        this.qtd_itens = 0;
    }

    int calcularOcupacao(int qtd_itens) {
        return (qtd_itens * 100) / this.tamanho;
    }






    public int hash(int chave){
        return chave % this.tamanho;
    }

    public int busca_hashing(int chave){
        int i = hash(chave);
        int j = 1;
        while(tabela[i] != chave && tabela[i] != -1){
            i = hash(chave + j++);
        }
        if(tabela[i] == -1) return -1;
        else return i;
    }

    public int insere_hashing(int chave) {
        if (calcularOcupacao(this.qtd_itens + 1) > 90) {
            System.out.println("Tabela acima de 90% de ocupação, inserção do número " + chave + " cancelada");
            return 0;
        }
        int i = hash(chave);
        int colisoes = 0;
        int j = 1;
        while (tabela[i] != -1) {
            i = hash(chave + j++);
            colisoes++;
        }
        tabela[i] = chave;
        this.qtd_itens++;
        return colisoes;
    }

    void mostrarLivres() {
        System.out.print("Posições vazias: ");
        for (int i = 0; i < this.tamanho; i++) {
            if (tabela[i] == -1) System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void main(String[] args){
        Random rng = new Random(1090);
        TabelaReHash tabela = new TabelaReHash(100);
        int COLISOES_CONTADAS = 3;

        int[][] max_colisoes = new int[COLISOES_CONTADAS][2];
        int colisoes_totais = 0;
        int qtd_numeros = 0;
        while (qtd_numeros != 100) {
            int numero = rng.nextInt(100001); // exclusivo
            int colisao = tabela.insere_hashing(numero);
            colisoes_totais += colisao;
            for (int i = 0; i < COLISOES_CONTADAS; i++) {
                if (colisao > max_colisoes[i][0]) {
                    for (int j = COLISOES_CONTADAS - 1; j > i; j--) {
                        max_colisoes[j][0] = max_colisoes[j - 1][0];
                        max_colisoes[j][1] = max_colisoes[j - 1][1];
                    }
                    max_colisoes[i][0] = colisao;
                    max_colisoes[i][1] = numero;
                    break;
                }
            }
        qtd_numeros++;
        }

        System.out.println("Colisoes totais: " + colisoes_totais);

        tabela.mostrarLivres();



        System.out.println("Três maiores colisões:");

        for (int i = 0; i < COLISOES_CONTADAS; i++) {
            System.out.println("Colisões: " + max_colisoes[i][0] + ", Número: " + max_colisoes[i][1]);
        }


        System.out.println("Busca das três maiores:");

        for (int i = 0; i < COLISOES_CONTADAS; i++) {
            System.out.println("Número: " + max_colisoes[i][1] + ", Posição: " + tabela.busca_hashing(max_colisoes[i][1]));
        }


    }


}
