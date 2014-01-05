#include<stdio.h>
#include<stdlib.h>

typedef struct linkedlist
{
  int value;
  struct linkedlist * next;
} llst;

//insert an element into the list
llst *insert(llst * head, llst * node)
{
  llst * curr = head;

  if(head == NULL)
      return node;

  while(curr->next != NULL)
      curr = curr->next;

  curr->next = node;

  return head;

}

//delete an element from the list
llst *delete(llst * head, int value)
{
  llst *prev, *curr = head;

  while(curr != NULL)
  {
    if(curr->value == value)
    {
      if(curr == head)
      {
        head = curr->next;
        free(curr);
        return head;
      }
      else
      {
        prev->next = curr->next;
        free(curr);
        return head;
      }
    }
    else
      {
         prev = curr;
         curr = curr->next;
      }
  }

  printf("Element not found\n");
  return head;
}

//to insert at begining of the list
llst *push(llst *head, int value)
{
  llst *rhead = malloc(sizeof(struct linkedlist));
  rhead->value = value;
  rhead->next = head;
  return rhead;
}

//delete the last element
llst *pop(llst *head)
{
  llst *prev, *curr = head;

  if(head == NULL)
    return head;


  while(1)
  {
    if(curr->next == NULL)
      {
        prev->next = NULL;
        free(curr);
        return head;
      }
    else
    {
      prev = curr;
      curr = curr->next;
    }
  }
}

//get the i-th element
int get(llst *head, int pos)
{
  int index = 0;
  llst *curr = head;

  while(index != pos && curr != NULL)
  {
    curr = curr->next;
    index += 1;
  }

  if(curr == NULL)
  {
    printf("Given position number is out of bounds. Printing -1\n");
    return -1;
  }
  else
    return curr->value;  
}

//reverse of the given list
llst *reverse(llst *head)
{
  llst *curr;

  if(head == NULL)
    return head;
  
  curr = head;
  head = head->next;
  curr->next = NULL;

  head = reverse(head);

  if(head == NULL)
    return curr;
  else
  {
    head = insert(head, curr);
    return head;
  }
  
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

//main function
void main()
{
  int value;
  char res;
  llst *head = NULL, *shead = NULL;

  //introduction
  printf("Linked List Library\n");

 /*

  printf("Enter the element to be pushed\n");
  scanf("%d", &value);

  head = push(head, value);
  
  */

  do
  {// loop for insert elements
    
    printf("Please enter the number\n");
    scanf("%d", &value);
    
    llst * node = malloc(sizeof(struct linkedlist));
    node->value = value;
    node->next = NULL;
    
    //insert each element one at a time
    head = insert(head, node);

    printf("Do you want continue?(y|n)\n");
    scanf(" %c",&res);
  } while (res == 'y' || res == 'Y');

  
  printf("The elements of the linked list are\n");
  display(head); // display the result

  /*
  printf("Enter the value to be deleted from the list\n");
  scanf("%d", &value);
  
  //delete a value from the list
  head = delete(head, value);
  
  printf("The elements of the linked list are\n");
  display(head);

  printf("Enter the element to be pushed\n");
  scanf("%d", &value);
  
  // insert at the begining
  head = push(head, value);

  printf("The elements of the linked list are\n");
  display(head);

  printf("Deleting the last element of the list\n");
  head = pop(head); // deleting the last element

  printf("The elements of the linked list are\n");
  display(head);

  printf("The reverse of the list:\n");
  head = reverse(head); // reverse of the list
  display(head);
  */ 

  // input for second list
  printf("Do you want to join this to another list?(y/n)\n");
  scanf(" %c",&res);
  
  while(res == 'y' || res == 'Y')
  {
    printf("Please enter the number\n");
    scanf("%d", &value);

    llst *node = malloc(sizeof(struct linkedlist));
    node->value = value;
    node->next = NULL;

    shead = insert(shead, node);

    printf("Do you want to continue?(y/n)\n");
    scanf(" %c",&res);
  }
  
  printf("The elements of the list are:\n");
  head = insert(head, shead);// joining of two lists.
  display(head);

  int pos;
  printf("Enter the position of the element (starting from 0)\n");
  scanf("%d", &pos);

  // get i-th element
  printf("The element at %d position is %d\n", pos, get(head,pos));
}
