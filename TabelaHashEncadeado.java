import java.util.Random;

public class TabelaHashEncadeado {
    class No {
        int chave;
        No prox;

        public No(int chave) {
            this.chave = chave;
            this.prox = null;
        }

    }

    No[] tabela;
    int tamanho;
    int qtd_itens;


    public TabelaHashEncadeado(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new No[tamanho];
        this.qtd_itens = 0;
    }


    public int hash(int chave) {
        return chave % this.tamanho;
    }

    public No busca_hashing(int chave) {
        int i = hash(chave);

        if (tabela[i] == null) return null;
        else if (tabela[i].chave == chave) return tabela[i];
        else {
            No atual = tabela[i];
            while (atual != null && atual.chave != chave) atual = atual.prox;
            return atual;
        }
    }

    public void imprimir_lista(int chave) {
        int i = hash(chave);

        if (tabela[i] == null) {
            System.out.println("Posição vazia");
        } else {
            No atual = tabela[i];
            System.out.print(atual.chave);
            atual = atual.prox;

            while (atual != null) {
                System.out.print(" -> ");
                System.out.print(atual.chave);
                atual = atual.prox;
            }
            System.out.println();
        }
    }

    public int insere_hashing(int chave) {
        int i = hash(chave);
        int tamanho_lista = 1;
        if (tabela[i] != null) {
            No atual = tabela[i];
            No novo = new No(chave);
            tamanho_lista++;
            if (atual.chave > chave) {
                novo.prox = atual;
                tabela[i] = novo;
                while (atual.prox != null) {
                    atual = atual.prox;
                    tamanho_lista++;
                }
            } else {
                boolean adicionado = false;
                while (atual.prox != null) {
                    if (atual.prox.chave > chave && !adicionado) {
                        novo.prox = atual.prox;
                        atual.prox = novo;
                        adicionado = true;
                    }
                    else {
                        tamanho_lista++;
                    }
                    atual = atual.prox;
                }
                if (!adicionado) {
                    atual.prox = novo;
                }
            }

        } else tabela[i] = new No(chave);
        this.qtd_itens++;
        return tamanho_lista;
    }

    void mostrarLivres() {
        int qtd_vazias = 0;
        System.out.print("Posições vazias: ");
        for (int i = 0; i < this.tamanho; i++) {
            if (tabela[i] == null) {
                System.out.print(i + " ");
                qtd_vazias++;
            }
        }
        System.out.println();
        System.out.println(qtd_vazias + " posições ficaram vazias");
    }

    public static void main(String[] args) {
        Random rng = new Random(1090);
        TabelaHashEncadeado tabela = new TabelaHashEncadeado(100);

        int listas_geradas = 0;
        int qtd_numeros = 0;
        int maior_lista = -1;
        int maior_chave = -1;

        int QTD_VALORES = 3;
        int[] ultimos_valores = new int[QTD_VALORES];
        int index = 0;


        while (qtd_numeros != 1000) {
            int numero = rng.nextInt(100001); // limite é exclusivo, não sei se era pra deixar como 100000 ou 100001 :(
            int tamanho_lista = tabela.insere_hashing(numero);
            if (tamanho_lista > maior_lista) {
                maior_lista = tamanho_lista;
                maior_chave = numero;
            }
            if (tamanho_lista > 1) {
                ultimos_valores[index] = numero;
                if (++index >= QTD_VALORES) index = 0;
            }
            if (tamanho_lista == 2) listas_geradas++;
            qtd_numeros++;
        }

        System.out.println("A maior lista encadeada foi: ");
        tabela.imprimir_lista(maior_chave);
        System.out.println("Com " + maior_lista + " elementos");


        System.out.println("Foram geradas " + listas_geradas + " listas encadeadas");

        tabela.mostrarLivres();

        System.out.println("Os três ultimos itens inseridos em listas encadeadas foram: ");
        int count = 0;
        while (count != QTD_VALORES) {
            System.out.print(ultimos_valores[index] + " ");
            count++;
            if (++index >= QTD_VALORES) index = 0;
        }

    }
}
