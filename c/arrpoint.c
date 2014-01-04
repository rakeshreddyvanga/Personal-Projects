#include<stdio.h>

void main(void) {

  char a[6];
  int i;
  for(i=0;i<6;i++){
  scanf(" %c", &a[i]);
  }

  for(i=0;i<6;i++){
  printf("%c \n",a[i]);
  }
}
