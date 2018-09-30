#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define MAX(x, y) (((x) > (y)) ? (x) : (y))
#define MIN(x, y) (((x) < (y)) ? (x) : (y))

int numNos = 8, inicioNo = 0;
double **dist;

int vetorEq(double const *x, double const *y, size_t n, double eps){
    int i = 0;
    for (i = 0; i < n; i++)
        if (fabs(x[i] - y[i]) > eps)
            return 0;
    return 1;
}

int main(void){
    int i = 0, j = 0, conv = 0, *arestas;
    double *distTo, *distOld;

    dist = calloc(numNos, sizeof(double*));
    for (i = 0; i < numNos; i++)
        dist[i] = calloc(numNos, sizeof(double));

    /* Construindo a matriz de distancia */
    for (i = 0; i < numNos; i++){
        for (j = 0; j < numNos; j++){
            if (i == j)
                dist[i][j] = 0.0;
            else
                dist[i][j] = INFINITY;
        }
    };

    dist[0][1] = 5;
    dist[0][7] = 8;
    dist[0][4] = 9;

    dist[1][3] = 15;
    dist[1][2] = 12;
    dist[1][7] = 4;

    dist[4][7] = 5;
    dist[4][5] = 4;
    dist[4][6] = 20;

    dist[7][2] = 7;
    dist[7][5] = 6;

    dist[5][2] = 1;
    dist[5][6] = 13;

    dist[2][3] = 3;
    dist[2][6] = 11;

    dist[3][6] = 9;

    /* alocando memoria */
    distTo = calloc(numNos, sizeof(double));
    distOld = calloc(numNos, sizeof(double));
    arestas = calloc(numNos, sizeof(int));
    for (i = 0; i < numNos; i++){
        distTo[i] = dist[inicioNo][i];
        arestas[i] = inicioNo;
    }

    /* iteracoes do Bellman-Ford */
    while (conv == 0){
        memcpy(distOld, distTo, sizeof(double)*numNos);
        for (i = 0; i < numNos; i++){
            for (j = 0; j < numNos; j++){
                double tmp = dist[i][j] + distTo[i];
                if (tmp < distTo[j]){
                    distTo[j] = tmp;
                    arestas[j] = i;
                }
            }
        }
        conv = vetorEq(distOld, distTo, numNos, 1e-9);
    }

    /* Imprima os resultados: */
    printf("V\tDistancia\tArestas:\n");
    for (i = 0; i < numNos; i++)
        printf("%i\t%.1f\t\t%i-->%i\n", i, distTo[i], i, arestas[i],i);

    free(distTo);
    free(distOld);
    free(arestas);
    for (i = 0; i < numNos; i++)
        free(dist[i]);
    free(dist);
}
