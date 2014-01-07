#include<stdio.h>
#include<stdlib.h>

typedef struct linkedlist
{ 
  int value;
  struct linkedlist *next;
} llst;

llst *front = NULL, *rear = NULL;

//pops from the lists and prints the first element
int dequeue()
{ 
  if(front == NULL)
    printf("Empty List\n");

  llst *temp = front;
  front = front->next;

  int ret = temp->value;

  if(temp == rear)
    rear = NULL;

  free(temp);
  return ret;
}

//display elements of the list
void display()
{ 
  llst * curr = front;

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
void enqueue(int value)
{

    llst  *node =  malloc(sizeof(llst));
    node->value = value;
    node->next = NULL;

    if(front == NULL)
      front = node;
    
    if(rear == NULL)
        rear = node;
    else
      rear->next = node;
      rear = node;
} 

int getfirst()
{
  if(front == NULL)
  {
    printf("empty list returning -1\n");
    return -1;
  }
  return front->value;

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

  //introduction
  printf("Queue Library\n");

  do
  {// loop of push elements
    
    printf("Please enter the number\n");
    scanf("%d", &value);
    
    //push each element one at a time
    enqueue(value);

    printf("Do you want continue?(y|n)\n");
    scanf(" %c",&res);

   } while (res == 'y' || res == 'Y');

  
  printf("The elements of the linked list are\n");
  display(); // display the result

  printf("first = %d\n", getfirst());
  printf("rear = %d\n",getrear());

  printf("pop from the queue %d\n", dequeue());

  printf("first = %d\n", getfirst());
  printf("rear = %d\n",getrear());

  printf("The elements of the linked list are\n");
  display(); // display the result

  printf("first = %d\n", getfirst());
  printf("rear = %d\n",getrear());
  
  printf("pop from the queue %d\n", dequeue());


  printf("The elements of the linked list are\n");
  display(); // display the result

  printf("first = %d\n", getfirst());
  printf("rear = %d\n",getrear());

}
