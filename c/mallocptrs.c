#include<stdio.h>

void main()
{
  int v = 5, *n;
  //*n = (int*) malloc(sizeof(int));
  *n = 5;
  printf("the value is %d \n", *n);
  //free(n);

  struct node * temp;
  temp -> data = 5;
  temp -> link = NULL;
  insert(root,temp);
}
