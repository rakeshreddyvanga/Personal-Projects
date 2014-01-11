#include<stdio.h>
#include<stdlib.h>
#define nINF -1000

/*
n -> indicates heap size
asize -> represents the arraysize
*/

int n = 9, asize = 9;

/*
Priority Queue methods
*/
void max_heap_insert(int*, int);
int heap_maximun(int*);
int heap_extract_max(int*);
void heap_increase_key(int*, int,int);
//*****************************//
/*
Heap methods
*/
void build_max_heap(int*);
void max_heapify(int*,int);
//****************************//

/*
Helper methods
*/
void exchange(int*,int*);
void display(int*);
int parent(int);
int left(int);
int right(int);
//***************************//

//getParent(index)
int parent(int index)
{
  return index/2;
}

//getLeft(index)
int getleft(int index)
{
  return 2*index;
}

//getRight(index)
int getright(int index)
{
  return 2*index + 1;
}
// maximum element from the heap
int heap_maximum(int *a)
{
  return a[0];
}

//extracting the max node from the heap

int heap_extract_max(int *a)
{
  int max;
  if(n < 0)
  {
    printf("heap underflow");
    return -1;
  }
  
  max = a[0];
  a[0] = a[n-1];
  //exchange(&a[0],&a[n]);
  n--;
  asize--;
  max_heapify(a,0);
  //return a[asize];
  return max;
}

void heap_increase_key(int *a, int index, int newkey)
{
  int i;
  if(a[index] > newkey)
    printf("Invalid operation.\nNew key is smaller than original key\n");
  
  a[index] = newkey;

  while (i>0 && a[parent(index)] < a[index])
  {
    exchange(&a[parent(index)], &a[index]);
    index = parent(index);
  }

}

//insert a key into the heap
void max_heap_insert(int *a, int key)
{
  n++;
  asize++;
  a[n] = nINF;
  heap_increase_key(a, n-1, key);
}

//method to swap to elements
void exchange(int *x , int *y)
{
  int temp;
  temp = *x;
  *x = *y;
  *y = temp;
}


//sort elements of the heap
void heap_sort(int *a)
{
  int i;
  build_max_heap(a);
  for(i=asize-1;i >= 1;i--)
  {
    exchange(&a[0], &a[i]);
    n = n-1;
    max_heapify(a,0);
  }
}

/* Move the element from the given index to the 
appropriate position to satify max-heap property.
*/
void max_heapify(int *a, int index)
{
  int left = getleft(index),ilargest;
  int right = getright(index);

  if(left <= n-1 && a[index] < a[left])
    ilargest = left;
  else
    ilargest = index;

  if(right <= n-1 && a[right] > a[ilargest])
    ilargest = right;

  if(ilargest != index)
  {
    exchange(&a[index], &a[ilargest]);
    max_heapify(a, ilargest);
  }
}

//build a max heap for the input array
void build_max_heap(int *a)
{
  int i;

  for(i = asize/2-1; i >= 0; i = i-1)
    max_heapify(a,i);
}

//display the elements
void display(int *a)
{
  int i;
  for(i=0;i<asize;i++)
    printf("%d\t", a[i]);
  printf("\n");
}

void main(void)
{
  int i,newkey;
  
  /*
  printf("Enter no of elements:\n");
  scanf("%d",&n);
  asize = n;
  */
  
  int a[9] = {4,1,3,2,16,9,76,10,14};
  /*printf("Enter the elements for the array\n");

  for(i=0;i<n;i++)
  {
    scanf("%d",&a[i]);
  }
  */

  build_max_heap(a);
  
  // building a max heap of the given array
  printf("The heapified elements are:\n");
  display(a);

  printf("The maximum of the heap is: %d\n",heap_maximum(a));
  
  printf("Extracting the maximum = %d\n",heap_extract_max(a));
  
  printf("The elements of the heap are\n");
  display(a);
  
  printf("Please enter the index(0 1...) and key value to be increased\n");
  scanf("%d%d",&i,&newkey);
  heap_increase_key(a,i,newkey);
  display(a);
  
  printf("Please enter the new key value to be inserted\n");
  scanf("%d",&newkey);
  max_heap_insert(a, newkey);
  display(a);

  /*heap_sort(a);
  printf("The sorted elements are:\n");
  display(a); */
  


}
