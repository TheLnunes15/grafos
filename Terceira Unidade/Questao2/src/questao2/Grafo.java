package questao2;

import java.util.ArrayList;
import java.util.Collections;

public class Grafo {
    private int[][] matrix;
    private int size = 0;

    public Grafo(int[][] m) {
        matrix = m.clone();
        size = m.length;
    }

    public Grafo(int size) {
        this.size = size;
        matrix = new int[size][size];
    }

    @Override
    public String toString() {
        String res = "";
        for(int i = 0; i < size; i++) {
            for(int j = i; j < size; j++) {
                if(matrix[i][j] == 1) {
                    res += String.valueOf(i) + " <----> " + String.valueOf(j) + "\n";
                }
            }
        }
        return res;
    }

    public void addEdge(int k, int m) {
        matrix[k][m] = matrix[m][k] = 1;
    }

    public boolean containsEdge(int k, int m) {
        return (matrix[k][m] == 1);
    }

    //Procure um loop simples usando o algoritmo DFS
    private boolean dfsCycle(ArrayList<Integer> result, int[] used, int parent, int v) {
        used[v] = 1;
        for (int i = 0; i < size; i++) {
            if (i == parent)
                continue;
            if (matrix[v][i] == 0)
                continue;
            if (used[i] == 0) {
                result.add(v);
                if (dfsCycle(result, used, v, i)){
                    //Ciclo encontrado
                    return true;
                } else {
                    result.remove(result.size() - 1);
                }
            } if (used[i] == 1) {
                result.add(v);
                //Ciclo encontrado
                ArrayList<Integer> cycle = new ArrayList<>();
                //Sao removidos os vertices do ciclo 
                for (int j = 0; j < result.size(); j++) {
                    if (result.get(j) == i) {
                        cycle.addAll(result.subList(j, result.size()));
                        result.clear();
                        result.addAll(cycle);
                        return true;
                    }
                }
                return true;
            }
        }
        used[v] = 2;
        return false;
    }

    public ArrayList<Integer> getCycle() {
        ArrayList<Integer> cycle = new ArrayList<>();
        boolean hasCycle = dfsCycle(cycle, new int[size], -1, 0);
        if (!hasCycle)
            return null;
        else {
            ArrayList<Integer> result = new ArrayList<>();
            for (int v: cycle)
                result.add(v);
            return result;
        }
    }

    // Procura por componentes conectados do grafo G - G', complementados por arestas de G,
    // um dos extremos dos quais pertence a um componente conectado e o outro de G'
    private void dfsSegments(int[] used, boolean[] laidVertexes, Grafo result, int v) {
        used[v] = 1;
        for (int i = 0; i < size; i++) {
            if (matrix[v][i] == 1) {
                result.addEdge(v, i);
                if (used[i] == 0 && !laidVertexes[i])
                    dfsSegments(used, laidVertexes, result, i);
            }
        }
    }

    private ArrayList<Grafo> getSegments(boolean[] laidVertexes, boolean[][] edges) {
        ArrayList<Grafo> segments = new ArrayList<>();
       // Procurar por segmentos com uma nervura
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (matrix[i][j] == 1 && !edges[i][j] && laidVertexes[i] && laidVertexes[j]) {
                    Grafo t = new Grafo(size);
                    t.addEdge(i, j);
                    segments.add(t);
                }
            }
        }
         // Procura por componentes conectados do grafo G - G', complementados por arestas de G,
         // um dos extremos dos quais pertence a um componente conectado e o outro de G'
        int used[] = new int[size];
        for (int i = 0; i < size; i++) {
            if (used[i] == 0 && !laidVertexes[i]) {
                Grafo res = new Grafo(size);
                dfsSegments(used, laidVertexes, res, i);
                segments.add(res);
            }
        }
        return segments;
    }

    //Procurar por um circuito no segmento selecionado, usando o algoritmo DFS
    private void dfsChain(int used[], boolean[] laidVertexes, ArrayList<Integer> chain, int v) {
        used[v] = 1;
        chain.add(v);
        for (int i = 0; i < size; i++) {
            if (matrix[v][i] == 1 && used[i] == 0) {
                if (!laidVertexes[i]) {
                    dfsChain(used, laidVertexes, chain, i);
                } else {
                    chain.add(i);
                }
                return;
            }
        }
    }

    private ArrayList<Integer> getChain(boolean[] laidVertexes) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (laidVertexes[i]) {
                boolean inGraph = false;
                for (int j = 0; j < size; j++) {
                    if (containsEdge(i, j))
                        inGraph = true;
                }
                if (inGraph) {
                    dfsChain(new int[size], laidVertexes, result, i);
                    break;
                }
            }
        }
        return result;
    }

    //Empilhamento, descrição da matriz de adjacência
    public static void layChain(boolean[][] result, ArrayList<Integer> chain, boolean cyclic) {
        for (int i = 0; i < chain.size() - 1; i++) {
            result[chain.get(i)][chain.get(i + 1)] = true;
            result[chain.get(i + 1)][chain.get(i)] = true;
        }
        if (cyclic) {
            result[chain.get(0)][chain.get(chain.size() - 1)] = true;
            result[chain.get(chain.size() - 1)][chain.get(0)] = true;
        }
    }

    //Verificando se este segmento está contido nesta face
    private boolean isFaceContainsSegment(final ArrayList<Integer> face, final Grafo segment, boolean[] laidVertexes) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (segment.containsEdge(i, j)) {
                    if ((laidVertexes[i] && !face.contains(i)) || (laidVertexes[j] && !face.contains(j))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Contamos o número de faces que contêm esse segmento
    private int[] calcNumOfFacesContainedSegments(ArrayList<ArrayList<Integer>> intFaces, ArrayList<Integer> extFace,
                                                  ArrayList<Grafo> segments, boolean[] laidVertexes,ArrayList<Integer>[] destFaces) {
        int[] count = new int[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            for (ArrayList<Integer> face : intFaces) {
                if (isFaceContainsSegment(face, segments.get(i), laidVertexes)) {
                    destFaces[i] = face;
                    count[i]++;
                }
            }
            if (isFaceContainsSegment(extFace, segments.get(i), laidVertexes)) {
                destFaces[i] = extFace;
                count[i]++;
            }
        }
        return count;
    }

      // Obter um empilhamento grafo plano
     // Retorna todas as faces do grafo planar colocado
     // Se isso nao for possível (o grafo nao eh plano), entao null
    public Faces getPlanarLaying() {
        //Se o grafo eh um unico vertice, entao retornamos duas faces
        if (size == 1) {
            ArrayList<ArrayList<Integer>> faces = new ArrayList<>();
            ArrayList<Integer> outerFace = new ArrayList<>();
            outerFace.add(0);
            faces.add(outerFace);
            faces.add((ArrayList<Integer>) outerFace.clone());
            return new Faces(faces, outerFace);
        }

       // Procurando por um loop, se ele nao existir, antes que o grafo nao corresponda as condicoes do algoritmo
      // (sem ciclos => tree => planar)
        ArrayList<Integer> c = getCycle();
        if(c.isEmpty()) {
            return null;
        }
       //Listas de Rosto
        ArrayList<ArrayList<Integer>> intFaces = new ArrayList<>();
        ArrayList<Integer> extFace = (ArrayList<Integer>) c.clone();
        intFaces.add(c);
        intFaces.add(extFace);

        //Matrizes de vertices e arestas ja empilhados, respectivamente
        boolean[] laidVertexes = new boolean[size];
        boolean[][] laidEdges = new boolean[size][size];
        for (int i : c) {
            laidVertexes[i] = true;
        }
        //Nos empilharemos o ciclo encontrado
        layChain(laidEdges, c, true);
          // O segundo passo do algoritmo:
         // selecione um conjunto de segmentos, conte o numero de faces envolventes,
         // alocando cadeias de segmentos, empilhando cadeias, adicionando novas faces
        while (true) {
            ArrayList<Grafo> segments = getSegments(laidVertexes, laidEdges);
            //Se nao houver segmentos, o grafo eh um ciclo encontrado => planar
            if (segments.size() == 0) {
                break;
            }
            //Uma matriz de faces em que os segmentos correspondentes com o numero minimo de calcNumOfFacesContainedSegments()
            ArrayList<Integer>[] destFaces = new ArrayList[segments.size()];
            int[] count = calcNumOfFacesContainedSegments(intFaces, extFace, segments,laidVertexes, destFaces);

            //Estamos procurando o numero minimo de calcNumOfFacesContainedSegments()
            int mi = 0;
            for (int i = 0; i < segments.size(); i++) {
                if (count[i] < count[mi])
                    mi = i;
            }
            //Se pelo menos um zero, o grafo nao eh planar
            if (count[mi] == 0) {
                return null;
            } else {
                 // Coloque o segmento selecionado
                // Selecione a cadeia entre dois vertices de contato
                ArrayList<Integer> chain = segments.get(mi).getChain(laidVertexes);
                //Marque os vertices da corrente como empilhados
                for (int i : chain) {
                    laidVertexes[i] = true;
                }
                //Nos colocamos as bordas da cadeia correspondente
                layChain(laidEdges, chain, false);

                //O lado do alvo, onde o segmento selecionado sera colocado
                ArrayList<Integer> face = destFaces[mi];
                //Novas faces geradas pela divisao da face do rosto pelo segmento selecionado
                ArrayList<Integer> face1 = new ArrayList<>();
                ArrayList<Integer> face2 = new ArrayList<>();
                //Estamos procurando os numeros dos vertices de contato da cadeia
                int contactFirst = 0, contactSecond = 0;
                for (int i = 0; i < face.size(); i++) {
                    if (face.get(i).equals(chain.get(0))) {
                        contactFirst = i;
                    }
                    if (face.get(i).equals(chain.get(chain.size() - 1))) {
                        contactSecond = i;
                    }
                }
                //Nos encontramos a cadeia reversa (uma corrente na direcao oposta)
                ArrayList<Integer> reverseChain = (ArrayList<Integer>) chain.clone();
                Collections.reverse(reverseChain);
                int faceSize = face.size();
                if (face != extFace) { //Se a face alvo nao for externa
                   //Colocamos uma corrente reta em uma das faces geradas,
                  //e inverter para o outro, dependendo dos numeros dos vertices de contato
                    if (contactFirst < contactSecond) {
                        face1.addAll(chain);
                        for (int i = (contactSecond + 1) % faceSize; i != contactFirst; i = (i + 1) % faceSize) {
                            face1.add(face.get(i));
                        }
                        face2.addAll(reverseChain);
                        for (int i = (contactFirst + 1) % faceSize; i != contactSecond; i = (i + 1) % faceSize) {
                            face2.add(face.get(i));
                        }
                    } else {
                        face1.addAll(reverseChain);
                        for (int i = (contactFirst + 1) % faceSize; i != contactSecond; i = (i + 1) % faceSize) {
                            face1.add(face.get(i));
                        }
                        face2.addAll(chain);
                        for (int i = (contactSecond + 1) % faceSize; i != contactFirst; i = (i + 1) % faceSize) {
                            face2.add(face.get(i));
                        }
                    }
                     // Exclua o rosto de destino (ele quebrou em dois novos)
                    // Adicione as faces geradas ao conjunto de faces internas
                    intFaces.remove(face);
                    intFaces.add(face1);
                    intFaces.add(face2);
                } else { //Se a face do alvo coincidisse com o exterior
                    //Tudo o mesmo, apenas uma das faces geradas eh uma nova face externa
                    ArrayList<Integer> newOuterFace = new ArrayList<>();
                    if (contactFirst < contactSecond) {
                        newOuterFace.addAll(chain);
                        for (int i = (contactSecond + 1) % faceSize; i != contactFirst; i = (i + 1) % faceSize) {
                            newOuterFace.add(face.get(i));
                        }
                        face2.addAll(chain);
                        for (int i = (contactSecond - 1 + faceSize) % faceSize; i != contactFirst; i = (i - 1 + faceSize) % faceSize) {
                            face2.add(face.get(i));
                        }
                    } else {
                        newOuterFace.addAll(reverseChain);
                        for (int i = (contactFirst + 1) % faceSize; i != contactSecond; i = (i + 1) % faceSize) {
                            newOuterFace.add(face.get(i));
                        }
                        face2.addAll(reverseChain);
                        for (int i = (contactFirst - 1 + faceSize) % faceSize; i != contactSecond; i = (i - 1 + faceSize) % faceSize) {
                            face2.add(face.get(i));
                        }
                    }
                    //Exclua os antigos e adicione novos
                    intFaces.remove(extFace);
                    intFaces.add(newOuterFace);
                    intFaces.add(face2);
                    extFace = newOuterFace;
                }
            }
        }
        return new Faces(intFaces, extFace);
    }
}