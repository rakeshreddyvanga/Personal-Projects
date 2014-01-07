#include<stdio.h>
#include<stdlib.h>

typedef struct node
{
  int value;
  struct node *left;
  struct node *right;
} Node;

// Inserting a node
Node *insert(Node *root, int value)
{
  Node *node = malloc(sizeof(Node));
  node->value = value;
  node->left = NULL;
  node->right = NULL;

  if(root == NULL)
      return node;

  Node *prev, *curr = root;

  while(curr != NULL)
  {
    prev = curr;
    if(value > curr->value)
        curr = curr->right;
    else
      curr = curr->left;
  }

  if(prev->value > value)
      prev->left = node;
  else
    prev->right = node;

    return root;
}

//searching a node
int search(Node *root, int value)
{
  if(root == NULL)
  {
    printf("element not found\n");
    return -1;
  }

  if(root->value == value)
  {
    printf("Element found\n");
    return 1;
  }

  if(root->value > value)
      search(root->left, value);
  else
      search(root->right, value);
}

//left root right
void inorder(Node *root)
{

  if(root == NULL)
      return;

  inorder(root->left);
  printf("%d\t",root->value);
  inorder(root->right);
  
}

//root left right
void preorder(Node *root)
{

  if(root == NULL)
      return;

  printf("%d\t",root->value);
  preorder(root->left);
  preorder(root->right);
}

//left right root
void postorder(Node *root)
{ 

  if(root == NULL)
      return;

  postorder(root->left);
  postorder(root->right);
  printf("%d\t",root->value);
}

// delete a node
Node *delete(Node *root, int value)
{
  Node *temp;

  if(root == NULL)
  {
    printf("Element not found\n");
      return root;
  }

  if(value > root->value)
  {
    root->right = delete(root->right, value);
    return root;
  }

  if(value < root->value)
  {
    root->left = delete(root->left, value);
    return root;
  }

  if(root->left == NULL && root->right == NULL)
  {
      temp = root;
      free(temp);
      return NULL;
  }

  if(root->left = NULL)
  {
    temp = root;
    root = root->right;
    free(temp);
    return root;
  }

  if(root->right = NULL)
  {
    temp = root;
    root = root->left;
    free(temp);
    return root;
  }

  temp = root->left;
  
  while(temp->right != NULL)
    temp = temp->right;
  
  root->value = temp->value;
  root->left = delete(root->left,temp->value);

  return root;

}

// main function to operate on the tree
void main(void)
{
  int value, optn;
  char res;
  Node *root = NULL;
  do
  {
    printf("Enter the element:");
    scanf("%d", &value);

    root = insert(root, value);

    printf("Do you want to continue?(y/n)\n");
    scanf(" %c", &res);

  }while (res == 'y' || res == 'Y');

  while(1)
  {
    printf("Please select an option: \n1.insert\n2.delete \n3.inorder\n4.preorder \n5.postorder\n6.search\n7.exit\n");
    scanf("%d",&optn);
      switch(optn)
      {
        case 1: 
        printf("Enter the value\n");
        scanf("%d",&value);
        root = insert(root, value);
        break;
        case 2: 
        printf("Enter the value to be deleted\n");
        scanf("%d", &value);
        root = delete(root, value);
        break;
        case 3: 
        inorder(root);
        printf("\n");
        break;
        case 4: 
        preorder(root);
        printf("\n");
        break;
        case 5:  
        postorder(root);
        printf("\n");
        break;
        case 6: 
        printf("enter the value to be searched\n");
        scanf("%d", &value);
        search(root,value);
        break;
        case 7: 
        optn = 7;
        break;
        default:
        printf("please enter a valid option\n");
        break;
      }
      if(optn == 7)
        break;
    }
}


