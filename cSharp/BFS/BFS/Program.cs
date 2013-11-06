using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BFS
{
    class Program
    {
        static void Main(string[] args)
        {

            Tree treeOperations = new Tree();

            int noofNodes = 0;
            LinkedList newNode;
            LinkedList rootNode = null;
            Console.WriteLine("Enter the number of nodes");
            noofNodes = Convert.ToInt16(Console.ReadLine());
            treeOperations.Graph = new LinkedList[noofNodes];
            treeOperations.Visited = new Boolean[noofNodes];
            for (int i = 0; i < noofNodes; i++)
            {
                treeOperations.Visited[i] = false;
            }

            Console.WriteLine("Please start numbering the nodes from 1");
            for (int i = 0; i < noofNodes; i++)
            {
                Console.WriteLine("Enter the " + (i + 1) + " node value");
                newNode = new LinkedList(Convert.ToInt16(Console.ReadLine()));
                rootNode = treeOperations.insertNode(rootNode, newNode);
                Console.WriteLine("You are entering the mode to" +
                "give inputs for the adjacent nodes for node " + (i + 1));
                while (true)
                {
                    String choice = "";
                    Console.WriteLine("Is there an adjacent node ? Y : N");
                    choice = Console.ReadLine().ToString();
                    if (choice.Equals("n") || choice.Equals("N"))
                    {
                        break;
                    }
                    else if (choice.Equals("y") || choice.Equals("Y"))
                    {
                        Console.WriteLine("Enter the adjacent value");
                        newNode = new LinkedList(Convert.ToInt16(Console.ReadLine()));
                        rootNode = treeOperations.insertNode(rootNode, newNode);
                    }
                    else
                        break;
                }

                treeOperations.Graph[i] = rootNode;
                rootNode = null;

            }
            treeOperations.displayTree();
            Console.WriteLine("The DFS traversal is");
            treeOperations.callBFS();
            Console.ReadLine();
        }
    }
}
