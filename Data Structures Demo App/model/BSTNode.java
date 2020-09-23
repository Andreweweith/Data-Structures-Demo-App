package model;

import java.util.Date;

/**
 * Node class for the basic recursive implementation of BST.
 * <p>
 * Each node consists of a key/value pair that contains
 * a date and a double, respectively.
 * 
 * @author Andrew
 */
public class BSTNode {
    private Date key;
    private Double value;
    private BSTNode left, right;

    /**
     * Construct a node for the BST
     * 
     * @param key
     * @param value
     */
    public BSTNode(Date key, Double value ) {
        this.key = key;
        this.value = value;
    }

    /**
     * if key not found in BST then it is added. If key already exists then that node's value
     * is updated.
     * 
     * @param key
     * @param value
     */
    public void put(Date key, Double value) {
        if (key.compareTo(this.key) < 0) {             
            if (left != null)             
            {                 
                left.put(key, value);             
            }             
            else             
            {                 
                left = new BSTNode(key, value);             
            }         
        }         
        else if (key.compareTo(this.key) > 0) {
            if (right != null)
            {
                right.put(key, value);
            }
            else
            {
                right = new BSTNode(key, value);
            }
        }
        else {
            //update this one
            this.value = value;
        }
    }

    /**
     * find Node with given key and return it's value
     * 
     * @param key
     * @return Double
     */
    public Double get(Date key) {
        if (this.key.equals(key)) {
            return value;
        }

        if (key.compareTo(this.key) < 0) {
            return left == null ? null : left.get(key);
        }
        else {
            return right == null ? null : right.get(key);
        }
    }
}