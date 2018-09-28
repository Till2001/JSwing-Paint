/**
 * <p>Materialien zu den zentralen
 * Abiturpruefungen im Fach Informatik ab 2012 in 
 * Nordrhein-Westfalen.</p>
 * <p>Klasse Queue</p>
 * <p>Objekte der Klasse Queue (Warteschlange) verwalten beliebige
 * Objekte nach dem First-In-First-Out-Prinzip, d.h. das zuerst
 * abgelegte Objekt wird als erstes wieder entnommen.</p>
 * 
 * <p>NW-Arbeitsgruppe: Materialentwicklung zum Zentralabitur 
 * im Fach Informatik</p>
 * 
 * @version 2010-10-20
 */
package basiX.adt;
 
public class Queue
{
    private Node head, tail;

    /**
     * Ein Knoten besteht aus einem Inhaltsobjekt 
     * und einem Nachfolgeknoten.
     */
    private class Node
    {
        private Object content;
        private Node nextNode;

        /**
         * Es wird ein neuer Knoten erzeugt.
         * 
         * @param pContect das Inhaltsobjekt
         * @param pNext der Nachfolgeknoten
         */
        public Node(Object pContent, Node pNext)
        { content = pContent;
            nextNode = pNext;
        }

        /**
         * Das Inhaltsobjekt wird ge&auml;ndert.
         * 
         * @param pContent das neue Inhaltsobjekt
         */
        public void setContent(Object pContent)
        { 
            content = pContent;
        }

        /**
         * Das Inhaltsobjekt wird zur&uuml;ckgegeben.
         * 
         * @return das Inhaltsobjekt
         */
        public Object content()
        {
            return content;
        }

        /**
         * Der Nachfolgeknoten wird ge&auml;dert.
         * 
         * @param pNext der neue Nachfolgeknoten
         */
        public void setNext(Node pNext)
        { 
            nextNode = pNext;
        }

        /**
         * Der Nachfolgeknoten wird zur&uuml;ckgegeben.
         * 
         * @return der Nachfolgeknoten
         */
        public Node next()
        {
            return nextNode;
        }
    }

    /**
     * Eine leere Schlange wird erzeugt.
     */
    public Queue() 
    {
        head = null;
        tail = null;
    }

    /**
     * Die Anfrage liefert den Wert true, wenn die Schlange keine Objekte
     * enthaelt, sonst liefert sie den Wert false.
     * 
     * @return true, falls die Schlange leer ist, sonst false
     */
    public boolean isEmpty(){
        return head == null;
    }

    /**
     * Das Objekt pObjekt wird an die Schlange angehaengt.
     * Falls pObject gleich null ist, bleibt die Schlange
     * unveraendert.
     * 
     * @param pObject das anzuhaengende Objekt
     */
    public void enqueue(Object pObject) 
    { 
        if (pObject != null){
            Node lNewNode = new Node(pObject, null);
            if (this.isEmpty()){
                head = lNewNode;
                tail = lNewNode;
            }
            else{
                tail.setNext(lNewNode);
                tail = lNewNode;
            }
        }  
    }

    /**
     * Das erste Objekt wird aus der Schlange entfernt.
     * Falls die Schlange leer ist, wird sie nicht 
     * veraendert.
     */
    public void dequeue() 
    {
        if (!this.isEmpty()){
            head = head.next();
        }
    }

    /**
     * Die Anfrage liefert das erste Objekt der Schlange.
     * Die Schlange bleibt unveraendert. Falls die 
     * Schlange leer ist, wird null zur&uuml;ck gegeben.
     * 
     * @return das erste Objekt der Schlange oder null, falls 
     * die Schlange leer ist.
     */
    public Object front(){
        if (this.isEmpty())
        {
            return null;
        }
        else{
            return head.content();
        }
    }

}

