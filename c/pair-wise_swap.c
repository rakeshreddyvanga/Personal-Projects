#include<stdio.h>
#include<stdlib.h>

typedef struct linkedlist
{
  int value;
  struct linkedlist *next;
} llst;

//display elements of the list
void display(llst *head)
{
  llst *curr = head;

  if(curr == NULL)
    printf("Empty list\n");
  else
  {
  printf("%d",curr->value);
  while(curr->next != NULL)
  {
      curr = curr->next;
      printf(" -> %d",curr->value);
  }
  printf("\n");
  }
}

//pushes an element to the list and returns the head
void push(llst **curr, int value)
{

    llst * node =  malloc(sizeof(llst));
    node->value = value;
    node->next = NULL;

  if(*curr == NULL)
       *curr = node;
  else
  {
       while((*curr)->next != NULL)
            curr = &(*curr)->next;

      (*curr)->next = node;
  }
}

llst *swap_complex(llst *curr)
{
  if(curr == NULL)
    return NULL;

  if(curr->next == NULL)
      return curr;
  llst *temp, *ncurr, *rtemp;
   temp =  malloc(sizeof(llst));
  *temp = *curr;
  ncurr = curr->next;
  *curr = *ncurr;
  *ncurr = *temp;
  free(temp);
  rtemp = curr->next;
  curr->next = ncurr;
  ncurr->next = rtemp;
  ncurr->next = swap_complex(ncurr->next); 

  return curr;
}

llst *swap(llst *curr)
{
  if(curr == NULL || curr->next == NULL)
      return curr;

  llst *ncurr, *rcurr;

  rcurr = curr->next->next;

  ncurr = curr->next;
  ncurr->next = curr;
  curr->next = swap(rcurr);
  
  return ncurr;
}

void main()
{
  int value;
  char res;
  llst *head = NULL;
  do
  {// loop of push elements
    
    printf("Please enter the number\n");
    scanf("%d", &value);

    //push each element one at a time
    push(&head, value);

    printf("Do you want continue?(y|n)\n");
    scanf(" %c",&res);
  } while (res == 'y' || res == 'Y');

  
  printf("The elements of the linked list are\n");
  display(head); // display the result

  head = swap(head);

  printf("The elements of the linked list are\n");
  display(head); // display the result

}
