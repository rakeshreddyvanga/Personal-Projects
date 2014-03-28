#include<sys/types.h>
#include<stdio.h>
#include<unistd.h>

int value = 5;

int main ()
{
  pid_t pid;
  
  pid = fork();

  if(pid == 0) /* child process */
  {
    value += 15;
    printf("Child process - value: = %d\n", value);
    return 0;
  }
  else if(pid > 0) { /* parent process */
    wait(NULL);
    printf("Parent: value = %d\n" , value);
    return 0;
  }
}
