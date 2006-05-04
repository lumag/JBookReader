#define property(TYPE, NAME)						\
	/** the NAME property */					\
	private TYPE my ## NAME;					\
	/** value NAME changed listeners */				\
	private List<ValueListener<TYPE>> my ## NAME ## Listeners =	\
			new LinkedList<ValueListener<TYPE>>();		\
	/** Returns the value. *//** @return the value. */			\
	public TYPE get ## NAME() {					\
		return this.my ## NAME;					\
	}								\
	/** Sets new value. *//** @param value new value */			\
	public void set ## NAME(TYPE value) {				\
		TYPE oldValue = this.my ## NAME;			\
		this.my ## NAME = value;				\
		if (!value.equals(oldValue)) {				\
			ValueEvent<TYPE> event =			\
				new ValueEvent<TYPE>(this, value);	\
			for (ValueListener<TYPE> listener:		\
					this.my ## NAME ## Listeners) {	\
				listener.valueChanged(event);		\
			}						\
		}							\
	}								\
	/** Adds new value changed event listener.*//** @param listener new listener. */	\
	public void add ## NAME ## Listener(ValueListener<TYPE> listener) {	\
		this.my ## NAME ## Listeners.add(listener);		\
	}								\
	/** Removes value changed event listener.*//** @param listener new listener. */	\
	public void remove ## NAME ## Listener(ValueListener<TYPE> listener) {	\
		this.my ## NAME ## Listeners.remove(listener);		\
	}

