#include<sys/types.h>
#include<stdio.h>
#include<unistd.h>
#include<sys/shm.h>
#include<sys/stat.h>
#include<stdlib.h>

#define MAX 10

typedef struct {
    long fib_seq[MAX];
    int seq_size;
} shared_data;

int main()
{
  int input, segment_id,i;
  shared_data *shared_memory = malloc(sizeof(shared_data));
  pid_t pid;
  printf("enter number:\n");
  scanf("%d",&input);

  if(input < 0 && input > MAX)
  {
    printf("Invalid Input");
    return 0;
  }
  
  segment_id = shmget(IPC_PRIVATE, sizeof(shared_data), S_IRUSR | S_IWUSR);

  shared_memory = (shared_data *) shmat(segment_id, NULL, 0);
  shared_memory->seq_size = input;
  
  pid = fork();

  if(pid == 0) 
    {
      int p1 = 0,p2 = 1,temp = 0, count = 0;
      while(input >= count)
      {
        if(count == 0 || count == 1)
          shared_memory->fib_seq[count] = count;
        else
        {
          shared_memory->fib_seq[count] = p1+p2;
          temp = p1;
          p1 = p2;
          p2 = temp + p2;
        }
        count++;
      }
      shmdt(shared_memory);
    }
    else if(pid > 0)
    {
      wait(NULL);
      for(i = 0; i< shared_memory->seq_size; i++)
      {
        printf("%lu\t",shared_memory->fib_seq[i]);
      }
      printf("\n");
      shmdt(shared_memory);
    }
      shmctl(segment_id, IPC_RMID,NULL);
  return 0;
}
