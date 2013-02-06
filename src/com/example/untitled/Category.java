package com.example.untitled;

import android.graphics.Color;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Category {

    public static class Element implements Comparable {
        public static final boolean DEFAULT_ENABLING = true;

        public char letter;
        public boolean enabled = DEFAULT_ENABLING;

        public Element(char letter) {
            this.letter = letter;
        }

        public Element(char letter, boolean enabled) {
            this.letter = letter;
            this.enabled = enabled;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Element) {
                return (((Element) o).letter - this.letter) * -1;
            } else {
                return -1;
            }
        }
    }

    private final String mName;
    private final int mColor;
    private final TreeSet<Element> mElements = new TreeSet<Element>();

    public static final int DEFAULT_COLOR = Color.BLACK;

    public Category(String name, int color) {
        mName = name;
        mColor = color;
    }

    public String getName() {
        return mName;
    }

    public int getColor() {
        return mColor;
    }

    public boolean containsElement(char letter) {
        return mElements.contains(new Element(letter));
    }

    public void addElement(Element element) {
        mElements.add(element);
    }

    public Set<Element> elements() {
        return Collections.unmodifiableSet(mElements);
    }
}
