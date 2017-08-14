


This program realized the search, insert and delete functionality of B+ trees. Specifically, B+ trees can be established by Insert. In the process of inserting index node and leaf node, the node will split when the number of entries of the node reaches 2*D, so as to maintain the balance. In the process of deleting an entry, let the nodes merge first and then redistribute the entries if the number of entries of a node bellows D, so as to maintain the properties of B+ trees.

Functions:
1. Newly added a function 
private int findK(Node<K,T> node,K key)
given a search key value and let it return the index of K, or return -1 if non-exist. 

2. Newly added a function
private Node<K,T> findChild(IndexNode<K, T> index,K key)
call it to find children of the index node.

3. Newly realize the Entry interface, named MyEntry, 
private class MyEntry implements Entry
in order to facilitate the manipulation of key and value. 

4. Search function
a. public T search(K key)
Search begins at the root based on the search key value. Pass in the root and search key value.
Call private member function:
private T search(Node<K,T> node,K key)

b. private T search(Node<K,T> node,K key)
Starts at passed in node, use the following schema for searching:
If the passed in node is null, return null.
If the passed-in node is a leaf node, return null if its key value does not exist, otherwise, return its key value.
If the passed-in node is an index node, find its children and call private T search() recursively.

5. Insert function
a. public void insert(K key, T value)
Passed-in value is key and value.
Pre-judge if the tree is null, newly add a root if null, and let its type be LeafNode in order to guarantee the validity of inserting begins at a null tree.
Newly construct an object Entry to receive the return value of private Entry<K, Node<K,T>> insert(Node<K,T> node , K key,  T value) , i.e., the result after B+ tree insertion. Construct index nodes for root and push root to the top of the tree.

b. private Entry<K, Node<K,T>> insert(Node<K,T> node,K key,T value)
Make choices on the passed-in node:
If it is a LeafNode, call member insert function for LeafNode, insert and then see if it is overflowed. If it is overflowed, call the splitLeafNode(LeafNode<K,T> leaf) function and return its return value. Otherwise, return null.
If it is an IndexNode, find its child c, call insert(Node<K,T> node,K key,T value) function recursively and store its return value at Entry.
If Entry is null, return null. Otherwise, find the corresponding index i of passed-in key and insert (entry, i) into IndexNode.
If IndexNode is overflowed, call splitIndexNode(IndexNode<K,T> index) function and return its return value.
Otherwise, return null.

c. public Entry<K, Node<K,T>> splitLeafNode(LeafNode<K,T> leaf)
Store the Dth to the (2*D+1)th key and value of the passed-in LeafNode into newly construct LeafNode n2.
Let the LeafNode before n2 point to leaf, and the next leaf of n2 point to the next leaf of leaf.
Then let the next leaf of leaf point to n2 and delete the Dth to the (2*D+1)th key and value of the original leaf.
Use the minimum key value of n2, together with n2, to construct a new object etnry of MyEntry and return.

d. public Entry<K, Node<K,T>> splitIndexNode(IndexNode<K,T> index) 
Use the key locating at D+1 of the IndexNode and child pointers from D+1 to 2*D+2 to construct new IndexNode n2.
Delete key from D to 2*D+1 of the original IndexNode, as well as the child pointers from D+1 to 2*D+2.
Use key locating at D, together with n2, to construct a new object entry of MyEntry and return.

6. delete function
a. public void delete(K key)
If root is null, do not execute.
If root is not null:
If root is LeafNode, find the index i of passed-in key. If i equals to -1, which implies there is no key in the tree, do not execute. Otherwise, delete key and value of i.
If root is IndexNode, find i of key in the IndexNode, call delete(IndexNode<K,T> parent,int n,K key) function. Delete the IndexNode with entry value key, store the return value in t. 
If t does not equal to -1, which implies the index i of key has been found, delete it in the index, as well as its child pointers.
See if root is null after deletion, if it is, let the child node be the root.

b. private int delete(IndexNode<K,T> parent,int n,K key)
Store the child of parent at location n into node. If it is the LeafNode, find the corresponding index i of it, delete it and see if it is underflowed.
If underflow, make choices on whether the left sibling or the right sibling should be chosen to do the balance operation with n. Call handleLeafNodeUnderflow() to do balance operation on LeafNode.
If it is an IndexNode, find index i of key in the IndexNode. Call this function recursively and store return value in t. If t does not equals to -1, delete t at this location in keys of index, and let the child pointer of index delete the pointer at location (t+1).
If index is underflowed after deletion, make choices on whether the left sibling or the right sibling should be chosen to do the balance operation with n. Call handleLeafNodeUnderflow() to do balance operation.

c. public int handleLeafNodeUnderflow(LeafNode<K,T> left, LeafNode<K,T> right, IndexNode<K,T> parent)
Use n to store the number of the sum of left and right siblings of n. If n is larger than 2*D, first use two variables allkeys and allvalues to merge the passed in left and right children and then split and redistribute them into ordered left and right children. Find the entry of father node which points to the right child.
Replace the corresponding entry with the smallest entry of the new right child. Otherwise, just merge left and right child without split and return the index of the one before the smallest entry of right child, i-1.

d. public int handleIndexNodeUnderflow(IndexNode<K,T> leftIndex, IndexNode<K,T> rightIndex, IndexNode<K,T> parent)
Use n to store the number of the sum of left and right siblings of n. If n is larger than 2*D, first find the index i of the first right child the father points to, replace it with i-1, and then merge and redistribute the left and right children as well as the key at location i of father node. Push the split key of father over left child.
If n is not larger than 2*D, merge left and right children, store them in left child and let the right child be null.

At this time, there is no bug found in our code.

