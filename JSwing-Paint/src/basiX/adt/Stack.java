/**
 * <p>Materialien zu den zentralen
 * Abiturpruefungen im Fach Informatik ab 2012 in 
 * Nordrhein-Westfalen.</p>
 * <p>Klasse Stack</p>
 * <p>Objekte der Klasse Stack (Keller, Stapel) verwalten beliebige
 * Objekte nach dem Last-In-First-Out-Prinzip, d.h. das zuletzt
 * abgelegte Objekt wird als erstes wieder entnommen.</p>
 * 
 * <p>NW-Arbeitsgruppe: Materialentwicklung zum Zentralabitur im Fach Informatik</p>
 * 
 * @version 2010-10-20
 */
package basiX.adt; 


public class Stack {
    private Node head;

    // Node
    private class Node {
        private Object content = null;
        private Node nextNode = null;

        public Node(Object pObject) {
            content = pObject;
            nextNode = null;
        }

        public void setNext(Node pNext) {
            nextNode = pNext;
        }

        public Node getNext() {
            return nextNode;
        }

        public Object getContent() {
            return content;
        }
    } // Ende Node

    // Stack
    /**
     * Ein leerer Stapel wird erzeugt.
     */   
    public Stack() { 
         head = null;
    }

    /**
     * Die Anfrage liefert den Wert true, wenn der Stapel 
     * keine Objekte enthaelt, sonst liefert sie den Wert false.
     * @return true, falls der Stapel leer ist, sonst false.
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Das Objekt pObject wird oben auf den Stapel gelegt. 
     * Falls pObject gleich null ist, bleibt der Stapel unveraendert.
     * @param pObject ist das einzufuegende Objekt.
     */
    public void push(Object pObject) {
        if (pObject != null) {
            Node lNode = new Node(pObject);
            lNode.setNext(head);
            head=lNode;
        }
    }

    /**
     * Das zuletzt eingefuegte Objekt wird von dem Stapel entfernt. 
     * Falls der Stapel leer ist, bleibt er unveraendert.
     */
    public void pop() {
        if (!isEmpty())
            head = head.getNext();
    }

    /**
     * Die Anfrage liefert das oberste Stapelobjekt. 
     * Der Stapel bleibt unveraendert. 
     * Falls der Stapel leer ist, wird null zurueck gegeben.
     * @return Inhaltsobjekt
     */     
    public Object top() {
        if (!this.isEmpty())
            return head.getContent();
        else
            return null;
    }

}
