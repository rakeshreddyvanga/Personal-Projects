#include<stdio.h>
#include<string.h>

void main(void)
{
  char *a = "palindrome", *b;
  int i,len = strlen(a);
  b = &a[len-1];
  for(i = len-1; i>=0 ; i--)
  {
    printf("%c",a[i]);
    printf("\n");
  }
}
