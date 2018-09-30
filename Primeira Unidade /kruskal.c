#include <stdio.h>
#include <stdlib.h>

int i,j,k,a,b,u,v,n,ne=1;
int min,mincusto=0,custo[9][9],pai[9];

int busca(int i){
	while(pai[i])
	i=pai[i];
	return i;
}

int uni(int i,int j){
	if(i!=j){
	        pai[j]=i;
	        return 1;
	}
	return 0;
}

int main(void){
	printf("\nEntre com o numero de vertices: ");
	scanf("%d",&n);
	printf("\nEntre com o custo da matriz de adjacencia: \n");
	for(i=1;i<=n;i++){
		for(j=1;j<=n;j++){
			scanf("%d",&custo[i][j]);
			if(custo[i][j]==0)
				custo[i][j]=999;
		}
	}
	printf("As arestas de custo minimo sao...\n");
	while(ne < n){
		for(i=1,min=999;i<=n;i++){
			for(j=1;j <= n;j++){
				if(custo[i][j] < min)
				{
					min=custo[i][j];
					a=u=i;
					b=v=j;
				}
			}
		}
		u=busca(u);
		v=busca(v);
		if(uni(u,v)){
			printf("[%d]: Aresta (%d,%d) = %d\n",ne++,a,b,min);
			mincusto +=min;
		}
		custo[a][b]=custo[b][a]=999;
	}
	printf("\n\tCusto minimo = %d\n", mincusto);
}
