using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DFS
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

        public LinkedList insertNode(LinkedList rootNode , LinkedList newNode)
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

        private void dfsTraversal(int index)
        {
            Console.WriteLine(Graph[index].Data+ " ");
            Visited[index] = true;

            for (int i = 0; i < Visited.Length; i++)
            {
                if (isAdjacent(Graph[index], Graph[i]) == true && Visited[i] == false)
                {
                    dfsTraversal(i);
                }
            }
        }

        public void callDFS()
        {
            for (int i = 0; i < Visited.Length; i++)
            {
                if (Visited[i] == false)
                {
                    dfsTraversal(i);
                }
            }
        }
    }
}
