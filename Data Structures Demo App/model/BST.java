package model;

import java.util.Date;

/**
 * Very basic implementation of a Binary Search Tree.
 * <p>
 * This implementation is specifically targeted for use by 
 * the DowJonesDataSearchTask class. It contains nodes of 
 * data consisting of date/double key/value pairs.
 * 
 * @author Andrew
 */
public class BST {
    private BSTNode root;

    /**
     * Add a key/value pair to the tree if it isn't already added
     * 
     * @param key
     * @param value
     */
    public void put(Date key, Double value) {
        if (root == null) {
            root = new BSTNode(key, value);
        }
        else {
            root.put(key, value);
        }
    }

    /**
     * Retrieve value using key
     * 
     * @param key
     * @return Double
     */
    public Double get(Date key) {
        return root == null ? null : root.get(key);
    }
}