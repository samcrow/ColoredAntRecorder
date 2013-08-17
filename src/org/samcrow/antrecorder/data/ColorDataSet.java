package org.samcrow.antrecorder.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Manages a set of ant colors
 * @author Sam Crow
 *
 */
public class ColorDataSet implements Serializable, Iterable<ColorDataSet.Color> {

	private static final long serialVersionUID = 4164306125804952751L;

	private final Color[] colors = new Color[3];
	
	private transient final List<ColorChangeListener> listeners = new LinkedList<ColorChangeListener>();
	
	public ColorDataSet() {
		
	}
	
	public void addColor(Color newColor) {
		//Find the first color that is not set
		for(int i = 0; i < 3; i++) {
			if(colors[i] == null) {
				colors[i] = newColor;
				notifyListeners();
				return;
			}
		}
		//If all are set, do nothing
	}
	
	/**
	 * Returns the colors stored by this data set, and clears it for future use
	 * @return
	 */
	public Color[] getAndClear() {
		Color[] copy = Arrays.copyOf(colors, 3);
		clear();
		return copy;
	}
	
	/**
	 * Returns the colors stored by this data set
	 * @return
	 */
	public Color[] get() {
		return Arrays.copyOf(colors, 3);
	}
	
	/**
	 * Resets each value of {@link #colors} to null.
	 */
	public void clear() {
		for(int i = 0; i < 3; i++) {
			colors[i] = null;
		}
		notifyListeners();
	}
	
	/**
	 * 
	 * @return true if all three colors in this set are set to a value other than null, otherwise false
	 */
	public boolean isFull() {
		for(Color color : colors) {
			if(color == null) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Adds a listener to be notified when one of the colors changes
	 * @param listener
	 */
	public void addListener(ColorChangeListener listener) {
		listeners.add(listener);
	}
	
	private void notifyListeners() {
		for(ColorChangeListener listener : listeners) {
			listener.colorsChanged(colors[0], colors[1], colors[2]);
		}
	}
	

	
	/**
	 * Defined ant colors
	 *
	 */
	public static enum Color {
		Green,
		Blue,
		/** Either white, yellow, or silver */
		WhiteYellowSilver,
		Pink,
		Orange,
		Red,
	}
	
	public interface ColorChangeListener {
		/**
		 * Called when any of the colors stored by this set changes
		 * @param color1
		 * @param color2
		 * @param color3
		 */
		public void colorsChanged(Color color1, Color color2, Color color3);
	}

	/**
	 * Returns an iterator over the three colors in this set, from head to tail
	 */
	@Override
	public Iterator<Color> iterator() {
		return new Iterator<Color>() {
			int index = 0;
			@Override
			public boolean hasNext() {
				return index < 3;
			}

			@Override
			public Color next() {
				try {
					if(index == 0) {
						return colors[0];
					}
					else if(index == 1) {
						return colors[1];
					}
					else if(index == 2) {
						return colors[2];
					}
					else {
						throw new NoSuchElementException();
					}
				} finally {
					index++;
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
}
