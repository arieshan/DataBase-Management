import static org.junit.Assert.assertEquals;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * BPlusTree Class Assumptions: 1. No duplicate keys inserted 2. Order D:
 * D<=number of keys in a node <=2*D 3. All keys are non-negative
 */
public class BPlusTree<K extends Comparable<K>, T> {

	public Node<K,T> root;
	public static final int D = 2;

	private int findK(Node<K,T> node,K key){
		int i=0;
		for(;i<node.keys.size();i++){
			K k= (K) node.keys.get(i);
			if(k.compareTo(key)>0){
				break;
			}
		}
		
		return i;
	}
	
	private Node<K,T> findChild(IndexNode<K, T> index,K key){
		
		return index.children.get(findK(index,key));
	}
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private T search(Node<K,T> node,K key){
		if(node==null){
			return null;
		}
		
		if(node.isLeafNode){
			int i=node.keys.indexOf(key);
			if(i==-1){
				return null;
			}else{
				LeafNode leaf=(LeafNode) node;
				return (T) leaf.values.get(i);
			}
		}else{
			IndexNode indexNode=(IndexNode) node;
			
			
			Node<K,T> c=findChild(indexNode, key);
			return search(c,key);
			
		}
		
		
	}
	
	
	/**
	 * TODO Search the value for a specific key
	 *
	 * @param key
	 * @return value
	 */
	public T search(K key) {
		return search(root, key);
	}

	
	private Entry<K, Node<K,T>> insert(Node<K,T> node,K key,T value){
		if(node.isLeafNode){
			LeafNode leaf=(LeafNode) node;
			leaf.insertSorted(key, value);
			if(leaf.isOverflowed()){
				return splitLeafNode(leaf);
			}else{
				return null;
			}
		}else{
			IndexNode index=(IndexNode) node;
			Node<K,T> c=findChild(index, key);
			Entry<K, Node<K,T>> entry=insert(c, key, value);
			if(entry==null){
				return null;
			}
			int i=findK(index, entry.getKey());
			index.insertSorted(entry, i);
			
			if(index.isOverflowed()){
				return splitIndexNode(index);
				
				
				
			}else{
				return null;
			}
			
		}
		
	}
	
	
	
	/**
	 * TODO Insert a key/value pair into the BPlusTree
	 *
	 * @param key
	 * @param value
	 */
	public void insert(K key, T value) {
		if(root==null){
			root=new LeafNode<K, T>(key, value);
		}
		
		Entry<K, Node<K,T>> entry=insert(root, key, value);
		if(entry!=null){
			root=new IndexNode<K, T>(entry.getKey(), root, entry.getValue());
		}

	}

	/**
	 * TODO Split a leaf node and return the new right node and the splitting
	 * key as an Entry<slitingKey, RightNode>
	 *
	 * @param leaf
	 * @return the key/node pair as an Entry
	 */
	public Entry<K, Node<K,T>> splitLeafNode(LeafNode<K,T> leaf) {

        LeafNode<K,T> n2=new LeafNode<K, T>(leaf.keys.subList(D, 2*D+1), leaf.values.subList(D, 2*D+1));
		
        n2.previousLeaf=leaf;
        n2.nextLeaf=leaf.nextLeaf;
        leaf.nextLeaf=n2;
        if(n2.nextLeaf!=null)
        	n2.nextLeaf.previousLeaf=leaf;
        
		Entry<K, Node<K,T>> entry=new MyEntry(leaf.keys.get(D), n2);
		
		leaf.keys.removeAll(leaf.keys.subList(D, 2*D+1));
		leaf.values.removeAll(leaf.values.subList(D, 2*D+1));
		
		return entry;
	}

	
	private class MyEntry implements Entry{
		
		public MyEntry(K k, Node<K, T> v) {
			super();
			this.k = k;
			this.v = v;
		}

		K k;
		Node<K,T> v;
		
		@Override
		public Object getKey() {
			// TODO Auto-generated method stub
			return k;
		}

		@Override
		public Object getValue() {
			// TODO Auto-generated method stub
			return v;
		}

		@Override
		public Object setValue(Object value) {
			// TODO Auto-generated method stub
			v=(Node<K, T>) value;
			return v;
		}
	}
	
	
	/**
	 * TODO split an indexNode and return the new right node and the splitting
	 * key as an Entry<slitingKey, RightNode>
	 *
	 * @param index
	 * @return new key/node pair as an Entry
	 */
	public Entry<K, Node<K,T>> splitIndexNode(IndexNode<K,T> index) {
		
		
		
		IndexNode<K,T> n2=new IndexNode<K, T>(index.keys.subList(D+1, 2*D+1), index.children.subList(D+1, 2*D+2));
		
		Entry<K, Node<K,T>> entry=new MyEntry(index.keys.get(D), n2);
		
		index.keys.removeAll(index.keys.subList(D, 2*D+1));
		index.children.removeAll(index.children.subList(D+1, 2*D+2));
		
		return entry;
	}

	/**
	 * TODO Delete a key/value pair from this B+Tree
	 *
	 * @param key
	 */
	public void delete(K key) {
		if(root==null){
			return;
		}
		
		if(root.isLeafNode){
			LeafNode leaf=(LeafNode) root;
			int i=leaf.keys.indexOf(key);
			if(i!=-1){
				leaf.keys.remove(i);
				leaf.values.remove(i);
			}
		}else{
			IndexNode index=(IndexNode) root;
		    int i=findK(index, key);
		    int t=delete(index,i,key);
		    
		    if(t!=-1){
		    	index.keys.remove(t);
		    	index.children.remove(t+1);
		    }
		    
		    if(index.keys.size()==0){
		    	root=(Node<K, T>) index.children.get(0);
		    }
		
		}

	}
	
	
	
	private int delete(IndexNode<K,T> parent,int n,K key){
		
		Node<K,T> node=parent.children.get(n);
		if(node.isLeafNode){
			LeafNode<K,T> leaf=(LeafNode<K, T>) node;
			
			int i=leaf.keys.indexOf(key);
			if(i!=-1){
				leaf.keys.remove(i);
				leaf.values.remove(i);
			}else{
				return -1;
			}
			
			if(leaf.isUnderflowed()){
				
				LeafNode<K,T> left;
				LeafNode<K,T> right;
				if(n!=0){
					left=(LeafNode<K, T>) parent.children.get(n-1);;
					right=leaf;
				}else{
					left=leaf;
					right=(LeafNode<K, T>) parent.children.get(n+1);
				}
				
				return handleLeafNodeUnderflow(left, right, parent);
				
			}else{
				return -1;
			}
			
		}else{
			IndexNode<K, T> index=(IndexNode<K, T>) node;
			int i=findK(index, key);
			int t=delete(index, i, key);
			if(t==-1){
				return -1;
			}else{
				index.keys.remove(t);
				index.children.remove(t+1);
				
				if(index.isUnderflowed()){
					IndexNode<K,T> left;
					IndexNode<K,T> right;
					if(n!=0){
						left=(IndexNode<K, T>) parent.children.get(n-1);;
						right=index;
					}else{
						left=index;
						right=(IndexNode<K, T>) parent.children.get(n+1);
					}
					
					return handleIndexNodeUnderflow(left, right, parent);
					
					
				}else{
					return -1;
				}
				
			}
			
		}
		
		
	}

	/**
	 * TODO Handle LeafNode Underflow (merge or redistribution)
	 *
	 * @param left
	 *            : the smaller node
	 * @param right
	 *            : the bigger node
	 * @param parent
	 *            : their parent index node
	 * @return the splitkey position in parent if merged so that parent can
	 *         delete the splitkey later on. -1 otherwise
	 */
	public int handleLeafNodeUnderflow(LeafNode<K,T> left, LeafNode<K,T> right,
			IndexNode<K,T> parent) {
		int n=left.keys.size()+right.keys.size();
		if(n>2*D){
			List<K> allKeys=new ArrayList<K>();
			List<T> allValues=new ArrayList<T>();
			allKeys.addAll(left.keys);
			allKeys.addAll(right.keys);
			
			allValues.addAll(left.values);
			allValues.addAll(right.values);
			
			int l=n/2;
		
			left.keys=new ArrayList<K>(allKeys.subList(0, l));
			right.keys=new ArrayList<K>(allKeys.subList(l, n));
			
			left.values=new ArrayList<T>(allValues.subList(0, l));
			right.values=new ArrayList<T>(allValues.subList(l, n));
			
			int i=parent.children.indexOf(right);
			i=i-1;
			
			parent.keys.set(i, right.keys.get(0));
			
			return -1;
			
		}else{
			
			left.keys.addAll(right.keys);
			left.values.addAll(right.values);
			
			int i=parent.children.indexOf(right);
			i=i-1;
			
			left.nextLeaf=right.nextLeaf;
			
			if(right.nextLeaf!=null)
				right.nextLeaf.previousLeaf=left;
			
			right.previousLeaf=null;
			right.nextLeaf=null;
			
			right.keys.clear();
			right.values.clear();
			
			return i;
			
		}
		
		
		
	

	}

	/**
	 * TODO Handle IndexNode Underflow (merge or redistribution)
	 *
	 * @param left
	 *            : the smaller node
	 * @param right
	 *            : the bigger node
	 * @param parent
	 *            : their parent index node
	 * @return the splitkey position in parent if merged so that parent can
	 *         delete the splitkey later on. -1 otherwise
	 */
	public int handleIndexNodeUnderflow(IndexNode<K,T> leftIndex,
			IndexNode<K,T> rightIndex, IndexNode<K,T> parent) {
		int n=leftIndex.keys.size()+rightIndex.keys.size();
		if(n>=2*D){
			
			int i=parent.children.indexOf(rightIndex);
			i=i-1;
			
			List<K> allKeys=new ArrayList<K>();
			List<Node<K,T>> allChildren=new ArrayList<Node<K,T>>();
			allKeys.addAll(leftIndex.keys);
			allKeys.add(parent.keys.get(i));
			allKeys.addAll(rightIndex.keys);
			
			allChildren.addAll(leftIndex.children);
			allChildren.addAll(rightIndex.children);
			int l=n/2;
			int r=n-l;
			
			leftIndex.keys=new ArrayList<K>(allKeys.subList(0, l));
			rightIndex.keys=new ArrayList<K>(allKeys.subList(l+1, n+1));
			
			leftIndex.children=new ArrayList<Node<K,T>>(allChildren.subList(0, l+1));
			rightIndex.children=new ArrayList<Node<K,T>>(allChildren.subList(l+1, n+2));
			
			
			
			parent.keys.set(i, allKeys.get(l));
			
			return -1;
			
		}else{
			int i=parent.children.indexOf(rightIndex);
			i=i-1;
			leftIndex.keys.add(parent.keys.get(i));
			leftIndex.keys.addAll(rightIndex.keys);
			leftIndex.children.addAll(rightIndex.children);
						
			rightIndex.keys.clear();
			rightIndex.children.clear();
			
			return i;
			
		}
		
		
		
	}

}
