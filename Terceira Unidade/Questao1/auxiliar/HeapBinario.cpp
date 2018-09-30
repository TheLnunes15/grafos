#include "HeapBinario.h"

void BinaryHeap::Clear(){
	key.clear();
	pos.clear();
	satellite.clear();
}

void BinaryHeap::Insert(double k, int s){

	if(s >= (int)pos.size()){
		pos.resize(s+1, -1);
		key.resize(s+1);
		satellite.resize(s+2);
	}
	else if(pos[s] != -1)	{
		throw "Erro: o satélite ja esta no heap";
	}

	int i;
	for(i = ++size; i/2 > 0 && GREATER(key[satellite[i/2]], k); i /= 2){
		satellite[i] = satellite[i/2];
		pos[satellite[i]] = i;
	}
	satellite[i] = s;
	pos[s] = i;
	key[s] = k;
}

int BinaryHeap::Size(){
	return size;
}

int BinaryHeap::DeleteMin(){
	if(size == 0)
		throw "Erro: heap vazio";

	int min = satellite[1], slast = satellite[size--];

	int child, i;
	for(i = 1, child = 2; child  <= size; i = child, child *= 2){
		if(child < size && GREATER(key[satellite[child]], key[satellite[child+1]]))
			child++;

		if(GREATER(key[slast], key[satellite[child]])){
			satellite[i] = satellite[child];
			pos[satellite[child]] = i;
		}
		else
			break;
	}
	satellite[i] = slast;
	pos[slast] = i;

	pos[min] = -1;

	return min;
}

void BinaryHeap::ChangeKey(double k, int s){
	Remove(s);
	Insert(k, s);
}

void BinaryHeap::Remove(int s){
	int i;
	for(i = pos[s]; i/2 > 0; i /= 2){
		satellite[i] = satellite[i/2];
		pos[satellite[i]] = i;
	}
	satellite[1] = s;
	pos[s] = 1;

	DeleteMin();
}

