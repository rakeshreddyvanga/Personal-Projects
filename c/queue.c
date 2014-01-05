#include<stdio.h>
#include<stdlib.h>

typedef struct linkedlist
{
  int value;
  struct linkedlist *next;
} llst;

//pops from the lists and returns the header
llst *pop(llst *head)
{
  llst *curr = head;
  if(curr == NULL)
    {
      printf("Empty list\n");
      return head;
    }

  printf("The poped element is %d\n", curr->value);
  head = head->next;
  free(curr);
  return head;

}

//display elements of the list
void display(llst * head)
{
  llst * curr = head;

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
llst *push(llst * head, llst * node)
{
  llst * curr = head;

  if(head == NULL)
      return node;

  while(curr->next != NULL)
      curr = curr->next;

  curr->next = node;

  return head;

}

//main function
void main()
{
  int value;
  char res;
  llst *head = NULL;

  //introduction
  printf("Queue Library\n");

  do
  {// loop of push elements
    
    printf("Please enter the number\n");
    scanf("%d", &value);
    
    llst * node = malloc(sizeof(struct linkedlist));
    node->value = value;
    node->next = NULL;
    
    //push each element one at a time
    head = push(head, node);

    printf("Do you want continue?(y|n)\n");
    scanf(" %c",&res);
  } while (res == 'y' || res == 'Y');

  
  printf("The elements of the linked list are\n");
  display(head); // display the result

  printf("pop from the queue\n");
  head = pop(head);


  printf("The elements of the linked list are\n");
  display(head); // display the result

  
  printf("pop from the queue\n");
  head = pop(head);


  printf("The elements of the linked list are\n");
  display(head); // display the result
}
