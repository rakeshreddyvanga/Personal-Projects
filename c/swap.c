#include<stdio.h>

void swap (int *, int *);

void main() {
  int a = 5,b = 10;
  printf("a is: %d b : %d\nswapping\n", a ,b);
  swap(&a,&b);
  printf("swapped %d and %d\n",a,b);
}

 void swap(int *pa ,int *pb)  
{
  
 /* int t;
  t = *pa;
  *pa = *pb;
  *pb = t;*/

  int *t;
  t = pa;
  pa = pb;
  pb = t;
}
