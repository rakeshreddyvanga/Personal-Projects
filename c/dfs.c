#include<stdio.h>
#include<stdlib.h>
#define MAX 10

struct node 
{
  int data;
  struct node * link;
};
typedef struct node * NODE;

int visited[MAX];

NODE insert(NODE root, NODE temp)
{
  NODE curr = root;
 
  if(root == NULL)
    return temp;

  while(curr->link != NULL)
      curr = curr->link;

  curr->link = temp;

  return root;
}

int isadjacent(NODE graph[], int src, int dest)
{
  NODE curr = graph[src];

  while(curr != NULL)
  {
    if(curr->data == dest)
        return 1;
    else
      curr = curr->link;
  }

  return 0;
}

void display(int n, NODE graph[])
{
  int i;
  NODE curr;

  for(i=0;i<n;i++)
  {
      curr = graph[i];
      printf("%d",curr->data);

      while(curr->link != NULL)
      {
        curr = curr->link;
        printf("-> %d", curr->data);
      }
      printf("\n");
  }
}

void dfs(int n, NODE graph[], int src)
{
  int i;

  visited[src] = 1;
  printf("%d\n" , src);

  for(i=0;i<n;i++)
  {
    if(isadjacent(graph, src, i) == 1 && visited[i] == 0)
        dfs(n, graph, i);
  }

}

void calldfs(int n, NODE graph[])
{
  int i;

  for(i=0;i<n;i++)
  {
    if(visited[i] == 0)
      dfs(n,graph,i);
  }
}

void main(void)
{
  int n,i,index;
  NODE root, temp;
  NODE graph[MAX]; 
  char res;
  printf("Enter the number of node in the graph max of 10\n");
  scanf("%d",&n);

  for(i=0;i<n;i++){
    root = NULL;
    printf("Adjacent node details\n");
    while(1){
      printf("Are there adjacent nodes for the node: %d \n", i);
      scanf(" %c",&res);

      if(res == 'n' || res == 'N')
          break;

      
      printf("Enter the value of the adjacent node:\n");
      scanf("%d", &index);

      NODE temp = malloc(sizeof(struct node));
      temp->data = index;
      temp->link = NULL;

      root = insert(root, temp);
    }
    
    //printf(" root data = %d for i = %d",root->data, i);
    graph[i] = root;
  }

  printf("The adjacency list is:\n");
  display(n, graph);

  for(i=0;i<n;i++)
    visited[i] = 0;

  printf("The DFS traversal is \n");
  calldfs(n, graph);

}
