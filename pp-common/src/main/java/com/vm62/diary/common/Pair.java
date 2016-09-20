package com.vm62.diary.common;

import java.io.Serializable;

/**
 * Wrapper for the pair of two objects.
 * @param <O1> class of object1.
 * @param <O2> class of object2.
 */
public class Pair<O1, O2> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static <O1, O2> Pair<O1, O2> create(O1 o1, O2 o2) {
        return new Pair<O1, O2>(o1, o2);
    }

    private O1 object1;
    private O2 object2;

    public Pair() {
    }

    public Pair(O1 object1, O2 object2) {
        super();
        this.object1 = object1;
        this.object2 = object2;
    }

    public O1 getObject1() {
        return object1;
    }

    public void setObject1(O1 object1) {
        this.object1 = object1;
    }

    public O2 getObject2() {
        return object2;
    }

    public void setObject2(O2 object2) {
        this.object2 = object2;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((object1 == null) ? 0 : object1.hashCode());
        result = prime * result + ((object2 == null) ? 0 : object2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Pair other = (Pair) obj;
        if (object1 == null) {
            if (other.object1 != null) {
                return false;
            }
        } else if (!object1.equals(other.object1)) {
            return false;
        }
        if (object2 == null) {
            if (other.object2 != null) {
                return false;
            }
        } else if (!object2.equals(other.object2)) {
            return false;
        }
        return true;
    }
}
