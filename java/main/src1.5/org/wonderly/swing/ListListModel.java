package org.wonderly.swing;

import javax.swing.*;
import java.util.*;
import java.util.Collections;
import java.util.List;

/**
<pre>
Copyright (c) 1997-2006, Gregg Wonderly
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimer in the documentation and/or other materials provided
      with the distribution.
    * The name of the author may not be used to endorse or promote
      products derived from this software without specific prior
      written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
OF THE POSSIBILITY OF SUCH DAMAGE.
</pre>
 *  This is an {@link AbstractListModel} subclass that also implements
 *  the {@link List} interface.  This allows the program to employ
 *  {@link Collections} provided operations such as <code>sort(List)</code>
 *  on the model to manage the ordering of the elements.  It also includes
 *  a {@link #setContents(Vector)} method that will allow the list contents
 *  to be replaced instantaneously so that large list manipulations don't
 *  show the user a bunch of consecutive insert and delete operations
 *  that make the display flash.
 * <p>
 * <b>This class is not thread safe, and is intended to only ever be use from the
 * EDT</b>.
 *  @author Gregg Wonderly <a href="mailto:gregg.wonderly@pobox.com">gregg.wonderly@pobox.com</a>
 *  @see CircularListModel
 */
public class ListListModel<T> extends AbstractListModel 
	implements List<T>,ComboBoxModel {

	protected List<T> delegate = new ArrayList<T>();

	public int getSize() {
		return delegate.size();
	}
	
	public ListListModel( List<T> conts ) {
		super();
		delegate = conts;
	}
	
	public ListListModel() {
	}

	public T getElementAt(int index) {
		if( index >= delegate.size() )
			return null;
		return delegate.get(index);
	}

	public void copyInto(T anArray[]) {
		delegate = Arrays.asList(anArray);
	}

	public void trimToSize() {
		delegate = new ArrayList<T>(delegate);
	}

	public void ensureCapacity(int minCapacity) {
            while( delegate.size() < minCapacity ) {
                delegate.add( null );
            }
	}

	public void setSize(int newSize) {
		int oldSize = delegate.size();
		ArrayList<T> n = new ArrayList<T>();
        
		for( int i = 0; i < newSize; ++i ) {
			n.add( delegate.get(i) );
		}
		if (oldSize > newSize) {
			fireIntervalRemoved(this, newSize, oldSize-1);
		}
		else if (oldSize < newSize) {
			ensureCapacity(newSize);
			fireIntervalAdded(this, oldSize, newSize-1);
		}
	}
	
	public Object getSelectedItem() {
		return comboSel;
	}

	Object comboSel;
	public void setSelectedItem( Object obj ) {
		comboSel = obj;
	}

	public void setContents( List<T> v ) {
		delegate = v;
		fireContentsChanged( this, 0, v.size()-1);
	}

	public List<T> getContents() {
		return delegate;
	}

	public int capacity() {
		return delegate.size();
	}

	public int size() {
		return delegate.size();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public Enumeration<T> elements() {
		return new Vector<T>(delegate).elements();
	}

	public boolean contains(Object elem) {
		return delegate.contains(elem);
	}

	public int indexOf(Object elem) {
		return delegate.indexOf(elem);
	}

	public int indexOf(T elem, int index) {
		for( int i = index;i < delegate.size(); ++i ) {
			if( delegate.get(i) == elem ) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(Object elem) {
		return delegate.lastIndexOf(elem);
	}

	public int lastIndexOf(T elem, int index) {
		for( int i = delegate.size()-1;i >= index; --i ) {
			if( delegate.get(i) == elem ) {
				return i;
			}
		}
		return -1;
	}

	public T elementAt(int index) {
		return delegate.get(index);
	}

	public T firstElement() {
		return delegate.get(0);
	}

	public T lastElement() {
		if( delegate.isEmpty() )
			return null;
		return delegate.get(delegate.size()-1);
	}

	public void setElementAt(T obj, int index) {
		delegate.set(index, obj);
		fireContentsChanged(this, index, index);
	}

	public void removeElementAt(int index) {
		delegate.remove(index);
		fireIntervalRemoved(this, index, index);
	}

	public void insertElementAt(T obj, int index) {
		if( delegate.size() > 0 ) {
			delegate.add( delegate.get( delegate.size()-1));
		}
		for( int i = delegate.size()-2; i > index; --i ) {
			delegate.set( i, delegate.get(i-1) );
		}
		delegate.set( index, obj );
		fireIntervalAdded(this, index, index);
	}

	public void addElement(T obj) {
		int index = delegate.size();
		delegate.add(obj);
		fireIntervalAdded(this, index, index);
	}

	public boolean removeElement(T obj) {
		int index = indexOf(obj);
		boolean rv = delegate.remove(obj);
		if (index > 0) {
			fireIntervalRemoved(this, index, index);
		}
		return rv;
	}


	public void removeAllElements() {
		int index1 = delegate.size()-1;
		delegate.clear();
		if (index1 >= 0) {
			fireIntervalRemoved(this, 0, index1);
		}
	}

	public synchronized Object[] toArray() {
		Object[] rv = new Object[delegate.size()];
		return delegate.toArray(rv);
	}

	public T get(int index) {
		return delegate.get(index);
	}

	public T set(int index, T element) {
		T rv = delegate.get(index);
		delegate.set(index, element);
		fireContentsChanged(this, index, index);
		return rv;
	}

	public void add(int index, T element) {
		insertElementAt(element, index);
		fireIntervalAdded(this, index, index);
	}

	public T remove(int index) {
		T rv = delegate.get(index);
		delegate.remove(index);
		fireIntervalRemoved(this, index, index);
		return rv;
	}

	public void clear() {
		int index1 = delegate.size()-1;
		delegate.clear();
		if (index1 >= 0) {
			fireIntervalRemoved(this, 0, index1);
		}
	}

	public void removeRange(int fromIndex, int toIndex) {
		for(int i = toIndex; i >= fromIndex; i--) {
			delegate.remove(i);
		}
		fireIntervalRemoved(this, fromIndex, toIndex);
	}

	public @Override String toString() {
		return delegate.toString();
	}

	public boolean add(T o) {
		int index = delegate.size();
		boolean ret = delegate.add( o );
		fireIntervalAdded(this, index, index);
		return ret;
	}

	public boolean addAll(Collection<? extends T> c) {
		int index = delegate.size();
		boolean ret = delegate.addAll( c );
		fireIntervalAdded(this, index, delegate.size() );
		return ret;
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		boolean ret = delegate.addAll( index, c );
		fireIntervalAdded(this, index, index+c.size() );
		return ret;
	}

	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	public @Override boolean equals(Object o) {
		if( o instanceof List == false )
			return false;
		return delegate.equals(o);
	}

	public @Override int hashCode() {
		return delegate.hashCode();
	}

	public Iterator<T> iterator() {
		return delegate.iterator();
	}

	public ListIterator<T> listIterator() {
		return delegate.listIterator();
	}

	public ListIterator<T> listIterator(int index) {
		return delegate.listIterator( index );
	}

	public boolean remove(Object o) {
		int index = indexOf(o);
		boolean ret = delegate.remove(o);
		fireIntervalRemoved( this, index, index);
		return ret;
	}
	public boolean removeAll(Collection<?> c) {
		boolean ret = delegate.removeAll(c);
		fireContentsChanged( this, 0, size() );
		return ret;
	}

	public boolean retainAll(Collection<?> c) {
		boolean ret = delegate.retainAll(c);
		fireContentsChanged( this, 0, size() );
		return ret;
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return delegate.subList( fromIndex, toIndex );
	}

	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}
}