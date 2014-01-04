#include<stdio.h>

struct point {
  int x;
  int y;
} origin;

void main() {
  struct point *pt;
  pt = &origin;
  pt->x = 23;
  pt->y = 33;
  printf("x coordinate -- %d \n y coordinate %d \n", pt->x,pt->y);
}
