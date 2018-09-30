package pr;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class prflow {

  List<No> nos;



  // Cria borda e inicializa o seu fluxo para zero
  public void addAresta(int origem, int destino, int capacidade) {
    nos.get(origem).addAresta(nos.get(destino), 0, capacidade);
  }

  /*
  * Max Flow:
  * - Fluxo máximo da fonte (primeiro nó) para o origem (último nó)
  */
  public int calculaFluxo() {
    preflow(nos.get(0));
    // Enquanto houver no ativo, empurre ou renomei
    No activeNode = noAtivo();
    while (activeNode != null) {
      if (!push(activeNode)) {
        relabel(activeNode);
      }
      activeNode = noAtivo();
    }

    return nos.get(nos.size()-1).fluxoExcesso;
  }

  /*
  * Preflow:
  * - Produz fluxo inverso no grafo residual
  */
  private void preflow(No s) {
    s.altura = nos.size();

    for (Aresta e : s.arestas) {
      e.fluxo = e.capacidade;
      e.destino.fluxoExcesso += e.fluxo;
      e.destino.addAresta(s, -e.fluxo, 0);
    }
  }

  /*
  * Push:
  * - Empurra o fluxo do nó com excesso de fluxo para aqueles abaixo dele
  */
  private boolean push(No n) {
    for (Aresta e : n.arestas) {
      if ((n.altura > e.destino.altura) && (e.fluxo != e.capacidade)) {
        int fluxo = Math.min(e.capacidade - e.fluxo, n.fluxoExcesso);
        n.fluxoExcesso -= fluxo;
        e.destino.fluxoExcesso += fluxo;
        e.fluxo += fluxo;
        arestaReversa(e, fluxo);
        return true;
      }
    }
    return false;
  }
  
  private void relabel(No n) {
    int alturaMinAdj = Integer.MAX_VALUE;
    for (Aresta e : n.arestas) {
      if ((e.fluxo != e.capacidade) && (e.destino.altura < alturaMinAdj)) {
        alturaMinAdj = e.destino.altura;
        n.altura = alturaMinAdj + 1;
      }
    }
  }

  public prflow(int noTamanho) {
    nos = new ArrayList<>();

    // Inicializa a altura e excesso de fluxo de nós e seta para 0
    for (int i = 0; i < noTamanho; i++) {
      nos.add(new No(0, 0));
    }
  }
  
  private No noAtivo() {
    for (int i = 1; i < nos.size()-1; i++) {
      if (nos.get(i).fluxoExcesso > 0) {
        return nos.get(i);
      }
    }
    return null;
  }


  private void arestaReversa(Aresta aresta, int flow) {
    for (Aresta e : aresta.destino.arestas) {
      if (e.destino.equals(aresta.origem)) {
        e.fluxo -= flow;
        return;
      }
    }
    aresta.destino.addAresta(aresta.origem, -flow, 0);
  }

  /* Estrutura */

  class No {
    int altura;
    int fluxoExcesso;
    List<Aresta> arestas;

    No(int altura, int excessoFluxo) {
      this.altura = altura;
      this.fluxoExcesso = excessoFluxo;
      arestas = new ArrayList<>();
    }

    void addAresta(No dest, int flow, int capacity) {
      Aresta edge = new Aresta(this, dest, flow, capacity);
      arestas.add(edge);
    }

  }

  class Aresta {
    No origem;
    No destino;
    int fluxo;
    int capacidade;

    Aresta(No origem, No destino, int fluxo, int capacidade) {
      this.origem = origem;
      this.destino = destino;
      this.fluxo = fluxo;
      this.capacidade = capacidade;
    }

  }
  
  public static void main(String[] args) {
    try{
        FileInputStream stream = new FileInputStream("entradaPushRelabel.txt");
        InputStreamReader reader = new InputStreamReader(stream);  
        Scanner ler = new Scanner(System.in);
        System.out.printf("Informe quantos vertices tem nesse grafo: \n");        
        int vertices = Integer.parseInt(ler.nextLine());
        String linha = null;
        BufferedReader lerArq = new BufferedReader(reader);
        prflow pr = new prflow(vertices);
        while(lerArq.readLine() != null){
                linha = lerArq.readLine(); //lê segunda e adiante.
                pr.addAresta(Integer.parseInt(linha.substring(0,1)),Integer.parseInt(linha.substring(2,3)),Integer.parseInt(linha.substring(4)));
            }


    System.out.println("O fluxo máximo para esta entrada é: "+pr.calculaFluxo());
    System.out.println();
                stream.close();
        }catch (IOException e){
            System.err.printf("Arquivo não encontrado (verifique se inseriu o caminho absoluto correto). %s.\n", e.getMessage());
        } 
  }

}
