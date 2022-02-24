package Proiect;

import java.util.List;
/**
 * Node class to handle nodes of the RedBlack Tree
 * @author Nevezi-Strango David
 *
 */
class Node { 
	  /**
	  * The key of the node
	  */
	  int data;
	  /**
	   * The parent Node
	   */
	  Node parent;
	  /**
	   * The left child Node
	   */
	  Node left;
	  /**
	   * The right child Node
	   */
	  Node right;
	  /**
	   * The task that is connected to the Node
	   */
	  Task task;
	  /**
	   * The color of the Node
	   */
	  int color;
	}
/**
 * RedBlack Tree class used for the P3 project. 
 * The reason of the usage of this data structure is because it has a good search time, insert and delete time.
 * It is overall better to use RedBlack Tree for search, insert and delete operations than to use any arrays(because of costly inserts and deletions) or linked lists(because of costly searches)
 * @author Nevezi-Strango David
 *
 */
public class RedBlackTree {
	  /**
	   * The root Node of the RedBlack Tree
	   */
	  private Node root;
	  /**
	   * The null Node of the RedBlack Tree
	   */
	  private Node TNULL;
	  /**
	   * Creating a null RedBlack tree
	   */
	  public RedBlackTree() {
		    TNULL = new Node();
		    TNULL.color = 0;
		    TNULL.left = null;
		    TNULL.right = null;
		    root = TNULL;
		  }
	  
		  /**
		   * Delete operation for the RedBlack Tree
		   * @param task The task that is intended to be deleted. As key, it takes the task's TaskID
		   */
		  public void deleteNode(Task task) {
			  	Node node = this.root;
			  	int key = task.getTaskid();
			    Node z = TNULL;
			    Node x, y;
			    while (node != TNULL) {
			    	if (node.data == key) {
			    		z = node;
			    	}

			    	if (node.data <= key) {
			    		node = node.right;
			    	} else {
			    		node = node.left;
			    	}
			    }

			    if (z == TNULL) {
			    	//System.out.println("Couldn't find key in the tree");
			    	return;
			    }

			    y = z;
			    int yOriginalColor = y.color;
			    if (z.left == TNULL) {
			    	x = z.right;
			    	rbTransplant(z, z.right);
			    } else if (z.right == TNULL) {
			    	x = z.left;
			    	rbTransplant(z, z.left);
			    } else {
			    	y = minimum(z.right);
			    	yOriginalColor = y.color;
			    	x = y.right;
			    	if (y.parent == z) {
			    		x.parent = y;
			      } else {
			    	  rbTransplant(y, y.right);
			    	  y.right = z.right;
			    	  y.right.parent = y;
			      }

			      rbTransplant(z, y);
			      y.left = z.left;
			      y.left.parent = y;
			      y.color = z.color;
			    }
			    if (yOriginalColor == 0) {
			    	fixDelete(x); 
			    }
			  }
		  /** 
		   * Private function to balance the tree after deletion of a node
		   * 
		   */
		  private void fixDelete(Node x) {
			    Node s;
			    while (x != root && x.color == 0) {
			    	if (x == x.parent.left) {
			    		s = x.parent.right;
			    		if (s.color == 1) {
			    			s.color = 0;
			    			x.parent.color = 1;
			    			leftRotate(x.parent);
			    			s = x.parent.right;
			    		}

			    		if (s.left.color == 0 && s.right.color == 0) {
			    			s.color = 1;
			    			x = x.parent;
			    		} else {
			    			if (s.right.color == 0) {
			    				s.left.color = 0;
			    				s.color = 1;
			    				rightRotate(s);
			    				s = x.parent.right;
			    			}

			    			s.color = x.parent.color;
			    			x.parent.color = 0;
			    			s.right.color = 0;
			    			leftRotate(x.parent);
			    			x = root;
			    		}
			    	} else {
			    		s = x.parent.left;
			    		if (s.color == 1) {
			    			s.color = 0;
			    			x.parent.color = 1;
			    			rightRotate(x.parent);
			    			s = x.parent.left;
			    		}

			    		if (s.right.color == 0 && s.right.color == 0) {
			    			s.color = 1;
			    			x = x.parent;
			    		} else {
			    			if (s.left.color == 0) {
			    				s.right.color = 0;
			    				s.color = 1;
			    				leftRotate(s);
			    				s = x.parent.left;
			    			}

			    			s.color = x.parent.color;
			    			x.parent.color = 0;
			    			s.left.color = 0;
			    			rightRotate(x.parent);
			    			x = root;
			    		}
			      }
			 }
			 x.color = 0;
		}
		  /**
		   * Transplant operation of 2 nodes
		   * @param u First node to be taken out
		   * @param v Second node to be connected to first node's parent
		   */
		  private void rbTransplant(Node u, Node v) {
			    if (u.parent == null) {
			      root = v;
			    } else if (u == u.parent.left) {
			      u.parent.left = v;
			    } else {
			      u.parent.right = v;
			    }
			    v.parent = u.parent;
			  }

		  /**
		   * Insert operation for the RedBlack Tree
		   * @param task The task that is intended to be inserted. As key, it takes the task's TaskID.
		   */
		  public void insert(Task task) {
			    Node node = new Node();
			    node.parent = null;
			    node.data = task.getTaskid();
			    node.task = task;
			    node.left = TNULL;
			    node.right = TNULL;
			    node.color = 1;
	
			    Node y = null;
			    Node x = this.root;
	
			    while (x != TNULL) {
			      y = x;
			      if (node.data < x.data) {
			        x = x.left;
			      } else {
			        x = x.right;
			      }
			    }
	
			    node.parent = y;
			    if (y == null) {
			      root = node;
			    } else if (node.data < y.data) {
			      y.left = node;
			    } else {
			      y.right = node;
			    }
	
			    if (node.parent == null) {
			      node.color = 0;
			      return;
			    }
	
			    if (node.parent.parent == null) {
			      return;
			    }
	
			    fixInsert(node);
		  }
		  
		  /** 
		   * Private function to balance the node after insertion
		   * 
		   */
		  private void fixInsert(Node k) {
			    Node u;
			    while (k.parent.color == 1) {
			      if (k.parent == k.parent.parent.right) {
			        u = k.parent.parent.left;
			        if (u.color == 1) {
			          u.color = 0;
			          k.parent.color = 0;
			          k.parent.parent.color = 1;
			          k = k.parent.parent;
			        } else {
			          if (k == k.parent.left) {
			            k = k.parent;
			            rightRotate(k);
			          }
			          k.parent.color = 0;
			          k.parent.parent.color = 1;
			          leftRotate(k.parent.parent);
			        }
			      } else {
			        u = k.parent.parent.right;

			        if (u.color == 1) {
			          u.color = 0;
			          k.parent.color = 0;
			          k.parent.parent.color = 1;
			          k = k.parent.parent;
			        } else {
			          if (k == k.parent.right) {
			            k = k.parent;
			            leftRotate(k);
			          }
			          k.parent.color = 0;
			          k.parent.parent.color = 1;
			          rightRotate(k.parent.parent);
			        }
			      }
			      if (k == root) {
			        break;
			      }
			    }
			    root.color = 0;
		  	  }
		
		  
		  /**
		   * Function to replace the task in case of modification
		   * @param task The Task that is in the modified form
		   * @return boolean Found whether the task has been found or not
		   */
		  public boolean replace(Task task) {
			  int key = task.getTaskid();
			  Node x = this.root;
			  boolean found = false;
			  while(x != TNULL && !found) {
				  if(x.data == key) {
					 x.task = task; 
					 found = true;
				  } else if(key < x.data){
					  x = x.left;
				  } else {
					  x = x.right;
				  }
			  }
			  return found;
		  }
		  /**
		   * Function to search for a given task
		   * @param task The task that is intended to be found
		   * @return the task if it has been found, otherwise TNULL, the null node for RedBlack Tree
		   */
		  public Task search(Task task) {
			  Node node = searchTreeRecursive(this.root, task.getTaskid());
			  return node != TNULL ? node.task : null;
		  }
		  /**
		   * Function to search if a given task exists in the RedBlack Tree
		   * @param k Key of the node that is intended to be found. It is equivalent to the task's TaskID
		   * @return true if the task/node has been found, false if not
		   */
		  public boolean searchIf(int k) {
			  return (searchTreeRecursive(this.root, k) != TNULL) ? true : false;
		  }
			  /**
			   *  Private function with recursive search operation of the tree
			   * @param node The current node to be inspected
			   * @param key The given key to be found
			   * @return the node if it has been found by key or TNULL, the null node of the RedBlack Tree
			   */
		  private Node searchTreeRecursive(Node node, int key) {
			    if (node == TNULL || key == node.data) {
			    	return node;
			    }

			    if (key < node.data) {
			    	return searchTreeRecursive(node.left, key);
			    }
			    return searchTreeRecursive(node.right, key);
		  }
		  /**
		   * InOrder operation of the RedBlack Tree. Used to extract all of the tasks into a JList container for the GUI
		   * @param taskList List of the tasks
		   */
		  public void inOrder(List<Task> taskList) {
			  inOrderRecursive(this.root,taskList);
		  }
		  
		/** 
		 * Private function of the recursive inOrder operation of the RedBlack Tree
		 * @param node The current node where the recursion is at 
		 * @param taskList The list of tasks that is used for the JList container for the GUI
		 */
		  private void inOrderRecursive(Node node,List<Task> taskList) {
			    if (node != TNULL) {
			      inOrderRecursive(node.left,taskList);
			      taskList.add(node.task);
			      inOrderRecursive(node.right,taskList);
			    }
			  }  
		    
		  /**
		   * Minimum search operation of the RedBlack Tree
		   * @param node The node from which the search is started
		   * @return the Node with the minimum key
		   */
		  public Node minimum(Node node) {
			    while (node.left != TNULL) {
			      node = node.left;
			    }
			    return node;
		  }
		  /**
		   * Maximum search operation of the RedBlack Tree
		   * @param node The node from which the search is started
		   * @return the Node with the maximum key
		   */
		  public Node maximum(Node node) {
			    while (node.right != TNULL) {
			      node = node.right;
			    }
			    return node;
		  }
		  /**
		   * Successor search operation of the RedBlack Tree
		   * @param x The node for which the successor is intended to be found
		   * @return The successor node of Node x
		   */
		  public Node successor(Node x) {
			    if (x.right != TNULL) {
			      return minimum(x.right);
			    }
	
			    Node y = x.parent;
			    while (y != TNULL && x == y.right) {
			      x = y;
			      y = y.parent;
			    }
			    return y;
		  }
		  /**
		   * Predecessor search operation of the RedBlack Tree
		   * @param x The node for which the predecessor is intended to be found
		   * @return The predecessor node of Node x
		   */
		  public Node predecessor(Node x) {
			    if (x.left != TNULL) {
			      return maximum(x.left);
			    }
	
			    Node y = x.parent;
			    while (y != TNULL && x == y.left) {
			      x = y;
			      y = y.parent;
			    }
	
			    return y;
		  }
		  /**
		   * Left Rotate operation of the RedBlack Tree
		   * @param x The node that is intended to be rotated
		   */
		  public void leftRotate(Node x) {
			    Node y = x.right;
			    x.right = y.left;
			    if (y.left != TNULL) {
			      y.left.parent = x;
			    }
			    y.parent = x.parent;
			    if (x.parent == null) {
			      this.root = y;
			    } else if (x == x.parent.left) {
			      x.parent.left = y;
			    } else {
			      x.parent.right = y;
			    }
			    y.left = x;
			    x.parent = y;
		  }
		  /**
		   * Right Rotate operation of the RedBlack Tree
		   * @param x The node that is intended to be rotated
		   */
		  public void rightRotate(Node x) {
			    Node y = x.left;
			    x.left = y.right;
			    if (y.right != TNULL) {
			      y.right.parent = x;
			    }
			    y.parent = x.parent;
			    if (x.parent == null) {
			      this.root = y;
			    } else if (x == x.parent.right) {
			      x.parent.right = y;
			    } else {
			      x.parent.left = y;
			    }
			    y.right = x;
			    x.parent = y;
		  }

		  
		  /**
		   * Getter function for the root of the RedBlack Tree
		   * @return The root Node
		   */
		  public Node getRoot() {
			  return this.root;
		  }
	}