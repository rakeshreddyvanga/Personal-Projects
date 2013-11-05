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

    }

 }

