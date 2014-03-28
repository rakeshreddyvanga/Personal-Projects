#include<stdio.h>
#include<stdlib.h>
#include<string.h>

void main(void)
{
  int i = 0,j = 0, count=0;
  char *s = "palindromep", a;
  while(s[i] != '\0')
  {
    a = s[i];
    while(s[j] != '\0')
    {
        if(a == s[j])
            count++;

        j++;
    }
    j = 0;
    i++;
  }
  if(count == strlen(s))
      printf("The word has unique characters\n");
  else
    printf("The word has repeated characters\n");
}
