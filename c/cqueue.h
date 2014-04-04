#ifndef _CQUEUE_H_
#define _CQUEUE_H_

#include<thread.h>
#include<stddef.h>
#include<stdio.h>
#include<memory.h>

typedef struct item
{
	tid_typ thread;
	struct item *next;
} node;

typedef struct que
{
	node *first;
	node *rear;
}queue;

tid_typ cDeque(queue**);
void cEnque(queue**, tid_typ);
int cDeleteque(queue**); /* returns 0 if false  */
int isEmpty(queue**); /* returns 0 if false */


#endif  
