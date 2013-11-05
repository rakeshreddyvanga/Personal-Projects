using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BFS
{
    class Tree
    {
        LinkedList[] graph;

        Boolean[] visited;

        public Boolean[] Visited
        {
            get { return visited; }
            set { visited = value; }
        }


        internal LinkedList[] Graph
        {
            get { return graph; }
            set { graph = value; }
        }

        public LinkedList insertNode(LinkedList rootNode, LinkedList newNode)
        {
            LinkedList currentNode = rootNode;
            if (rootNode == null)
            {
                return newNode;
            }
            while (currentNode.NextNode != null)
            {
                currentNode = currentNode.NextNode;
            }
            currentNode.NextNode = newNode;

            return rootNode;
        }

        public void displayTree()
        {
            Console.WriteLine("Displaying the adjacency list of the given graph:");
            for (int i = 0; i < graph.Length; i++)
            {
                LinkedList currentNode = Graph[i];
                while (currentNode != null)
                {
                    Console.Write(currentNode.Data + "\t");
                    currentNode = currentNode.NextNode;
                }
                Console.WriteLine();
            }
        }

        public Boolean isAdjacent(LinkedList sourceNode, LinkedList destinationNode)
        {
            LinkedList currentNode = sourceNode;
            while (currentNode != null)
            {
                if (currentNode.Data == destinationNode.Data)
                {
                    return true;
                }
                else
                {
                    currentNode = currentNode.NextNode;
                }
            }
            return false;
        }

        public LinkedList returnNode(int data)
        {
            foreach (LinkedList item in Graph)
            {
                if (item.Data == data)
                    return item;
            }

            return null;
        }

        public void callBFS()
        {
            Queue<LinkedList> queue = new Queue<LinkedList>();
            queue.Enqueue(Graph[0]);
            while (queue.Count != 0)
            {
                LinkedList currentNode = queue.Dequeue();
                Visited[currentNode.Data-1] = true;
                Console.WriteLine(currentNode.Data);
                currentNode = currentNode.NextNode;
                while (currentNode != null)
                {
                    if (Visited[currentNode.Data-1] == false)
                    {
                        queue.Enqueue(returnNode(currentNode.Data));
                    }
                    currentNode = currentNode.NextNode;
                }

            }
        }

       
    }
}
