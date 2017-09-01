

/**
 *	TreeMap implemented as Binary Search Tree
 */
import tree.BinaryTree;
import java.util.TreeSet;
/**
 *	TreeMap implemented as Binary Search Tree
 */
public class MyTreeMap<K extends Comparable<K>,V> implements MyMap<K,V> 
{
	private BinaryTree<Element> map;
	java.util.Set<K> keys;  // to return keys in order
	private int size;
	
        
	public boolean containsKey(K key)
        {   
            Element e = new Element(key, null);
            if(search(e, map) != null)
                return true;
            
            return false;
        }
	public V put(K key, V value)
        {
            if(!containsKey(key))
                size++;
            
            Element e = new Element(key, value);
            
            Element val = insert(e);
            
            if(val != null)
            {
                return val.value;
            }
		return null;
            
        }
	public V get(K key)
        {
            if (this.containsKey(key)) 
            {
                return search(new Element(key,null), map).value;
            }
            else 
                return null;
        }
	public V remove(K key)
        {
                V value = null;
                Element e = new Element(key, value);
                
                if(search(e, map) != null)
                {
                    value = search(e, map).value;
                    e = new Element(key, value);
                    delete(map, e, null);
                    size--;
                    return value;
                }
                return null;
        }
	public int size()
        {
            return size;
        }
	public int height()
        {
            return height(map);
        }
	public String toString()
        {
            return map.toString();
        }
	public java.util.Set<K> keySet()
        {
                keys = new TreeSet();
                inorder(map);
		return keys;
        }

     //To change body of generated methods, choose Tools | Templates.

// Element class

private class Element
{
	K key; V value;
	public Element(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
                
	public int compareTo(Element that)
        {
            return this.key.compareTo(that.key);
        }
	public String toString()
        {
           return key.toString();
        }
	
}// private methods implementing BST operations search, insert, delete, inorder
// reference: Wikipedia article on Binary Search Tree
//
	private Element search(Element element, BinaryTree<Element> tree)
        {  
            
            if(tree == null)
                return null;
            
            int num = element.compareTo(tree.getRoot());
   
            if(num < 0)
                return search(element, tree.getLeft());
            if(num > 0) 
                return search(element, tree.getRight());
            else
                return tree.getRoot();
        }
	private Element insert(Element element)
        {

            if(map == null)
            {
                map = new BinaryTree<>(element);
                return null;
            }
            else
                return insert(element, map);
                
        }
	private Element insert(Element element, BinaryTree<Element> tree)
        {
            if(element.compareTo(tree.getRoot()) == 0)
            {
                Element r = tree.getRoot();
                tree.setRoot(element);
                return r;
            }
            else if(element.compareTo(tree.getRoot()) < 0)
            {
                if(tree.getLeft() == null)
                {
                    tree.setLeft(new BinaryTree<>(element));
                    return null;
                }
                    
                else
                    return insert(element, tree.getLeft());
            }
            else 
            {
                if(tree.getRight() == null)
                {
                    tree.setRight(new BinaryTree<>(element));
                    return null;
                }
                else
                    return insert(element,tree.getRight());

            }
            
        }
	private Element delete(BinaryTree<Element> tree, Element element, BinaryTree<Element> parent)
        {
            Element r = tree.getRoot();

            if(tree == null)
                return null;
            if(element.compareTo(tree.getRoot()) < 0)
                return delete(tree.getLeft(), element, tree);
            if(element.compareTo(tree.getRoot()) >0)
                return delete(tree.getRight(), element, tree);
            else
            {
                if(tree.isLeaf())
                {
                    if(parent.getLeft() == tree)
                        parent.setLeft(null);
                    if(parent.getRight() == tree)
                        parent.setRight(null);
                }
                
                if(tree.getLeft() != null && tree.getRight() == null)
                {
                    promote(null, parent, tree.getLeft());
                }
                else if(tree.getRight() != null && tree.getLeft() == null)
                {
                    promote(null, parent, tree.getRight());
                    
                }
                else
                {
                    
                    BinaryTree<Element> temp = tree.getRight();
                    
                    if(temp != null)
                    {
                    while(temp.getLeft() != null)
                    {
                        temp = temp.getLeft();
                    }
                    
                    Element sucessor = temp.getRoot();
                    
                    delete(tree, sucessor, parent);
                    
                    tree.setRoot(sucessor);
                    }
                }  
            } 
            return r;
        }   
        
// make newChild the appropriate (left or right) child of parent, if parent exists
	private void promote(BinaryTree<Element> tree, BinaryTree<Element> parent, BinaryTree<Element> newChild)
        {
		if (newChild.getRoot().compareTo(parent.getRoot()) < -1) 
                {
			parent.setLeft(newChild);
		}
		else 
                {
			parent.setRight(newChild);
		}
        }
	private void inorder(BinaryTree<Element> tree)
        {
            if(tree == null)
                return;
            inorder(tree.getLeft());
            keys.add(tree.getRoot().key);
            inorder(tree.getRight());
        }
	private int height(BinaryTree<Element> tree)
	{
            if(tree == null)
                return -1;
            return 1+ (Math.max(height(tree.getLeft()), height(tree.getRight())));
            
        }
        
}






