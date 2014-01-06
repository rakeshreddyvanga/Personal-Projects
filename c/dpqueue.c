#include<stdio.h>
#include<stdlib.h>

typedef struct linkedlist
{
  int value;
  struct linkedlist *next;
} llst;

llst *first = NULL, *rear = NULL;

//pops from the lists and returns the header
int pop(llst **curr)
{
  int ret = -1;
  
  if(*curr == NULL)
      printf("Empty list returning -1\n");
  else
  {
    ret = (*curr)->value;
    printf("The poped element is %d\n", ret);
    *curr = (*curr)->next;
  }

  first = *curr;
  return ret;
}

//display elements of the list
void display(llst *head)
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
void push(llst **curr, llst *node)
{

  if(*curr == NULL)
  {
    *curr = node;
    first = node;
  }
  else
  {
       while((*curr)->next != NULL)
            curr = &((*curr)->next);

      (*curr)->next = node;
  }
}

int getfirst()
{
  if(first == NULL)
  {
    printf("empty list returning -1\n");
    return -1;
  }
  return first->value;

}

int getrear()
{
  if(rear == NULL)
    {
      printf("empty list returning -1\n");
      return -1;
    }
    return rear->value;
}
//main function
void main()
{
  int value;
  char res;
  llst *head = NULL;

  //introduction
  printf("Queue Library-- double pointers\n");

  do
  {// loop of push elements
    
    printf("Please enter the number\n");
    scanf("%d", &value);
    
    llst * node =  malloc(sizeof(llst));
    node->value = value;
    node->next = NULL;

    
    //push each element one at a time
    push(&head, node);
    rear = node;
    printf("Do you want continue?(y|n)\n");
    scanf(" %c",&res);
  } while (res == 'y' || res == 'Y');

  
  printf("The elements of the linked list are\n");
  display(head); // display the result

  printf("first = %d\n", getfirst());
  printf("rear = %d\n",getrear());

  printf("pop from the queue\n");
  pop(&head);

  printf("first = %d\n", getfirst());
  printf("rear = %d\n",getrear());

  printf("The elements of the linked list are\n");
  display(head); // display the result

  printf("first = %d\n", getfirst());
  printf("rear = %d\n",getrear());
  
  printf("pop from the queue\n");
  pop(&head);


  printf("The elements of the linked list are\n");
  display(head); // display the result

  printf("first = %d\n", getfirst());
  printf("rear = %d\n",getrear());
}
