#include<stdio.h>

void swap (int *, int *);

void main() {
  int a = 5,b = 10;
  printf("swapping\n");
  swap(&a,&b);
  printf("swapped %d and %d\n",a,b);
}

 void swap(int *pa ,int *pb)  
{
  
  int t;
  t = *pa;
  *pa = *pb;
  *pb = t;
}
