
FLAG = -O3 -m64 -Wall

Ajusta.o: ./auxiliar/Ajusta.cpp ./auxiliar/Ajusta.h ./auxiliar/HeapBinario.h ./auxiliar/VariaveisGlobais.h ./auxiliar/Grafo.h 
	g++ $(FLAG) -c ./auxiliar/Ajusta.cpp -o Ajusta.o

HeapBinario.o: ./auxiliar/HeapBinario.h ./auxiliar/HeapBinario.cpp ./auxiliar/VariaveisGlobais.h
	g++ $(FLAG) -c ./auxiliar/HeapBinario.cpp -o HeapBinario.o

Grafo.o: ./auxiliar/Grafo.h ./auxiliar/Grafo.cpp
	g++ $(FLAG) -c ./auxiliar/Grafo.cpp -o Grafo.o

Main.o: Main.cpp ./auxiliar/Dijkstra.h ./auxiliar/Ajusta.h ./auxiliar/Grafo.h ChinesePostman.h
	g++ $(FLAG) -c Main.cpp -o Main.o

teste: Ajusta.o HeapBinario.o Grafo.o Main.o 
	g++ $(FLAG) Ajusta.o HeapBinario.o Grafo.o Main.o -o teste
	@echo "Os arquivos acima foram usados como ajuda para implementar o algoritmo. Devido a dificuldade dele, encontrei solucoes para o problema que pudessem auxiliarem na implementacao da solucao da questao."

