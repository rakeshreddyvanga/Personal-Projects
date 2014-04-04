#include "cqueue.h"

void cEnque(queue **q, tid_typ thr)
{
	node *newNode = malloc(sizeof(node));
	newNode->thread = thr;
	newNode->next = NULL;
	
	if(*q == NULL)
	{
		*q = malloc(sizeof(queue));
		(*q)->first = newNode;
		(*q)->rear = newNode;
	}	
	else
	{
		if((*q)->first == NULL)
		{
		  (*q)->first = newNode;
		  (*q)->rear = newNode;
		}
		else
		{
		  (*q)->rear->next = newNode;
		  (*q)->rear = (*q)->rear->next;
		}
	}
}

tid_typ cDeque(queue **q)
{
	if(*q == NULL) 
	{
		printf("The queue is empty. Please initialize the queue\n\r");
		return -1;
	}
	
	if((*q)->first == NULL)
	{
		printf("Queue is empty\n\r");
		return -1;
	}
			
	node *dequeNode;
	dequeNode = (*q)->first;
	(*q)->first = (*q)->first->next;
	
	if( (*q)->first == NULL)
		(*q)->rear == NULL;
	
	tid_typ thr = dequeNode->thread;
	free(dequeNode);
	return thr;
}

int cDeleteque(queue **q)
{
	node *delNode;
	if(*q == NULL)
		return 1;
	
	while((*q)->first != NULL )
	{
		delNode = (*q)->first;
		(*q)->first = (*q)->first->next;
		free(delNode);
	}	
	free(*q);
	(*q) = NULL;
	return 1;
}

int isEmpty(queue **q)
{
	if(*q == NULL)
		return 1;
	if((*q)->first == NULL || (*q)->rear == NULL)
		return 1;
	return 0;
}
