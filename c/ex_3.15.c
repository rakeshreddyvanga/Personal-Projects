#include<stdio.h>
#include<sys/types.h>
#include<unistd.h>

int main(int argc, char *argv[])
{
  pid_t pid;
/*
  if(argc == 1)
  {
    printf("please supply the input number");
    return 0;
  }
  else if (argc == 2)
  {
    if(argv[1] < 0)
      {
        printf("please input a positive number");
        return 0;
      }      
  }
  else if(argc > 2)
  {
    printf("please input single number only");
    return 0;
  } 
  int input = argv[1]; */
  
  int input;
  printf("Enter the number:\n");
  scanf("%d",&input);
  if(input < 0){
  printf("enter positive number\n");
  return 0;
  }

  pid = fork();


  if(pid == 0) //child process
  { // generate the fibonacci series
      int p1 = 0,p2 = 1,temp = 0, count = 0;
      while(input >= count)
      {
        if(count == 0)
          printf("%d\t", count);

        else if(count == 1)
          printf("%d\t",count);

        else
        {
          printf("%d\t", p1+p2);
          temp = p1;
          p1 = p2;
          p2 = temp + p2;
        }
        count++;
      }
      printf("\n");
  }
  else if(pid > 0)
  { // parent process
    wait(NULL);
  }
}
