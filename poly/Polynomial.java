package poly;

import java.io.IOException;
import java.nio.file.FileSystemAlreadyExistsException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}

	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float c;
		int d;
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		Node first = null;
		
		//in case one is null
		if(poly1==null && poly2==null){
			return null;
		}else if(poly1==null && poly2!=null){
			while(ptr2!=null){
				c=ptr2.term.coeff;
				d=ptr2.term.degree;
				
				Node temp = new Node(c, d, first);
				first=temp;

				ptr2=ptr2.next;
			}
		}else if(poly1!=null && poly2==null){
			while(ptr1!=null){
				c=ptr1.term.coeff;
				d=ptr1.term.degree;
				
				Node temp = new Node(c, d, first);
				first=temp;

				ptr1=ptr1.next;
			}
		}else{
			while(ptr1!=null && ptr2!=null){
				if(ptr1.term.degree==ptr2.term.degree){
					c=(ptr1.term.coeff)+(ptr2.term.coeff);
					d=ptr1.term.degree;
	
					if(c!=0){
						Node temp = new Node(c, d, first);
						first=temp;
					}
					
					ptr1=ptr1.next;
					ptr2=ptr2.next;
				}else if(ptr1.term.degree>ptr2.term.degree){
					c=ptr2.term.coeff;
					d=ptr2.term.degree;
	
					if(c!=0){
						Node temp = new Node(c, d, first);
						first=temp;
					}
		
					ptr2=ptr2.next;
				}else if(ptr2.term.degree>ptr1.term.degree){
					c=ptr1.term.coeff;
					d=ptr1.term.degree;
	
					if(c!=0){
						Node temp = new Node(c, d, first);
						first=temp;
					}
	
					ptr1=ptr1.next;
				}
			}
	
			//one is empty now
			if(ptr1==null && ptr2!=null){
				while(ptr2!=null){
					c=ptr2.term.coeff;
					d=ptr2.term.degree;
		
					if(c!=0){
						Node temp = new Node(c, d, first);
						first=temp;
					}
		
					ptr2=ptr2.next;
				}
				
			}else if(ptr1!=null && ptr2==null){
				while(ptr1!=null){
					c=ptr1.term.coeff;
					d=ptr1.term.degree;
		
					if(c!=0){
						Node temp = new Node(c, d, first);
						first=temp;
					}
		
					ptr1=ptr1.next;		
				}
				
			}
		}
		
		if(first!=null){
			Node result = null;
			Node ptr = first;
			do{
				Node t = new Node(ptr.term.coeff, ptr.term.degree, result);
				result=t;
				ptr=ptr.next;
			}while(ptr!=null);

			return result;	
		}else{
			return null;
		} 
		
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float c;
		int d;
		Node sub = null;

		//either is null
		if(poly1==null || poly2==null){
			return null;
		}

		//basic multiply
		for(Node ptr1=poly1; ptr1!=null; ptr1=ptr1.next){
			for(Node ptr2=poly2; ptr2!=null; ptr2=ptr2.next){
				c=(ptr1.term.coeff)*(ptr2.term.coeff);
				d=(ptr1.term.degree)+(ptr2.term.degree);

				if(c!=0){
					Node temp = new Node(c, d, sub);
					sub=temp;
				}
			}
		}
		
		sub=reverse(sub);
		Node r = likeTerms(sub, poly1, poly2);
		
		return r;
	}

	private static int findLength(Node head){
		int count=0;
		Node ptr = head;
        while (ptr != null)
        {
            count++;
            ptr = ptr.next;
        }
        return count;
	}

	private static Node reverse(Node head){
		
		Node result = null;
		Node ptr = head;
		do{
			Node t = new Node(ptr.term.coeff, ptr.term.degree, result);
			result=t;
			ptr=ptr.next;
		}while(ptr!=null);

		return result;
	}

	private static Node likeTerms(Node head,Node poly1,Node poly2){
		int size1=findLength(poly1), size2=findLength(poly2);
		Node front;//front of poly
		Node p1, p2;//two factors

		//first set
		Node c=head;
		int count=0;
		while(count<size2-1){
			c=c.next;
			count++;
		}
		p1=head;
		front=c.next;
		c.next=null;
		
		//rest of the poly
		int x=1;
		while(x<size1){
			Node ptr=front;
			for(int i = 0; i<size2-1; i++){
				ptr=ptr.next;
			}
			p2=front;
			front=ptr.next;//new front
			ptr.next=null;//new set

			p1=add(p1, p2);
			x++;
		}

		return p1;

	}

	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float total = 0;
		float c;
		float d;
		Node ptr = poly;

		if(poly==null){
			return 0;
		}

		while(ptr!=null){
			c=ptr.term.coeff;
			d=(float) ptr.term.degree;

			total = total + (((float) Math.pow(x, d)) * c);

			ptr=ptr.next;
		}


		return total;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
