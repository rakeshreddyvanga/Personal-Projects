using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BFS
{
    class LinkedList
    {
          private int data;

        public LinkedList(int data)
        {
            this.data = data;
            this.nextNode = null;
        }

        public int Data
        {
            get { return data; }
            set { data = value; }
        }
        private LinkedList nextNode;

        internal LinkedList NextNode
        {
            get { return nextNode; }
            set { nextNode = value; }
        }

        public Boolean add(int data)
        {
            if (this == null)
            {
                throw new NullReferenceException();
            }
            LinkedList currentNode = this.NextNode;
            while (true)
            {
                if (currentNode == null)
                {
                    currentNode = new LinkedList(data);
                    return true;
                }
                else
                {
                    currentNode = currentNode.NextNode;
                }
            }
        }

    }

 }

