#include<stdio.h>
#include<stdlib.h>
#define MAX 7
#define INFINITE 1000

int allselected(int *selected)
{
  int i=0;
  for(i = 0; i<MAX; i++)
    if(selected[i] == 0)
        return 0;

  return 1;
}

void shortpath(int cost[][MAX], int *distance)
{
  int i,newdist,smalldist, selected[MAX]={0}, curr = 0, dc, k;

  for(i=0;i<MAX;i++)
      distance[i] = INFINITE;

  //initial assignments for node 0 which is ths source
  selected[curr] = 1;
  distance[curr]= 0;

  while(!allselected(selected))
  {
    smalldist = INFINITE;
    dc = distance[curr];

    for(i=0;i<MAX;i++)
    {
      if(selected[i] == 0)
      {
        newdist = dc + cost[curr][i];
        if(newdist < distance[i])
            distance[i] = newdist;
        if(smalldist > distance[i])
        {
            smalldist = distance[i];
            k = i;
        }
      }
    }

    curr = k;
    selected[k] = 1;
  }
}

void main(void)
{
  int distance[MAX]={0}, i;
   int cost[MAX][MAX]= {{INFINITE,2,4,7,INFINITE,5,INFINITE}, 
                      {2,INFINITE,INFINITE,6,3,INFINITE,8},
                      {4,INFINITE,INFINITE,INFINITE,INFINITE,6,INFINITE},
                      {7,6,INFINITE,INFINITE,INFINITE,1,6}, 
                      {INFINITE,3,INFINITE,INFINITE,INFINITE,INFINITE,7},
                      {5,INFINITE,6,1,INFINITE,INFINITE,6}, 
                       {INFINITE,8,INFINITE,6,7,6,INFINITE}};

  shortpath(cost,distance);

  printf("The shortest path to each vertex from the source is\n");
  for(i=0;i<MAX;i++)
    printf("%d\t",distance[i]);
  
  printf("\n");
}

