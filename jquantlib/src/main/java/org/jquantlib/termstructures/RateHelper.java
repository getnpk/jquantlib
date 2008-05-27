/*
 Copyright (C) 2007 Srinivas Hasti

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquantlib-dev@lists.sf.net>. The license is also available online at
 <http://jquantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 
 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the originating copyright notice follows below.
 */
package org.jquantlib.termstructures;

import java.util.List;

import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.util.Date;
import org.jquantlib.util.DefaultObservable;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;

/**
 * Base helper class for yield-curve bootstrapping
 * <p>
 * This class provides an abstraction for the instruments used to bootstrap a term structure. It is advised that a rate helper for
 * an instrument contains an instance of the actual instrument class to ensure consistency between the algorithms used during
 * bootstrapping and later instrument pricing. This is not yet fully enforced in the available rate helpers, though - only
 * SwapRateHelper and FixedCouponBondHelper contain their corresponding instrument for the time being.
 * 
 * @author Srinivas Hasti
 * @author Richard Gomes
 */
public abstract class RateHelper<T extends TermStructure> implements Observer, Observable {

	//
	// protected fields
	//

	protected Handle<Quote>	quote;
	protected T				termStructure;
	protected Date			earliestDate;
	protected Date			latestDate;

	//
	// public constructors
	//

	public RateHelper(final Handle<Quote> quote, final T termStructure, final Date earliestDate, final Date latestDate) {
		super();
		this.termStructure = termStructure; // FIXME: code review : do we need a dummy non-null TermStructure ???
		this.earliestDate = earliestDate;
		this.latestDate = latestDate;
		this.quote = quote;
		this.quote.addObserver(this);
	}

	public RateHelper(final Handle<Quote> quote) {
		this.termStructure = null; // FIXME: code review : do we need a dummy non-null TermStructure ???
		this.earliestDate = null;
		this.latestDate = null;
		this.quote = quote;
		this.quote.addObserver(this);
	}

	public RateHelper(final double quote) {
		this.termStructure = null; // FIXME: code review : do we need a dummy non-null TermStructure ???
		this.earliestDate = null;
		this.latestDate = null;
		this.quote = new Handle<Quote>(new SimpleQuote(quote));
	}
	
	protected RateHelper(){
		
	}

	//
	// public methods
	//

	/**
	 * The earliest date at which discounts are needed by the helper in order to provide a quote.
	 * 
	 * @return the earliest relevant date
	 */
	public final Date getEarliestDate() {
		return earliestDate;
	}

	/**
	 * The latest date at which discounts are needed by the helper in order to provide a quote. It does not necessarily equal the
	 * maturity of the underlying instrument.
	 * 
	 * @return the latest relevant date
	 */
	public final Date getLatestDate() {
		return latestDate;
	}

//XXX
//	public T getTermStructure() {
//		return termStructure;
//	}

	/**
	 * Sets the term structure to be used for pricing
	 * 
	 * @param termStructure
	 */
//	
// COMMENTS kept for JQuantLib developers only as they refer to original C++ code:
//	
//	 Being a pointer and not a shared_ptr, the term
//   structure is not guaranteed to remain allocated
//   for the whole life of the rate helper. It is
//   responsibility of the programmer to ensure that
//   the pointer remains valid. It is advised that
//   rate helpers be used only in term structure
//   constructors, setting the term structure to
//   <b>this</b>, i.e., the one being constructed.
//	
	public final void setTermStructure(final T termStructure) {
		if (termStructure == null) throw new NullPointerException("null term structure given"); // FIXME: message
		this.termStructure = termStructure;
	}

	public final double getQuoteError() {
		return quote.getLink().doubleValue() - getImpliedQuote();
	}

	public final double getQuoteValue() {
		return quote.getLink().doubleValue();
	}

//XXX	
//	public final boolean quoteIsValid() {
//		// quote_->isValid();
//		return true; //TODO
//	}

	public double getReferenceQuote() /* @ReadOnly */ {
		return quote.getLink().doubleValue();
	}
	

	
// TODO: code review :: how this method is used?
//	virtual DiscountFactor discountGuess() const {
//            return Null<Real>();
//        }
	
	
	//
	// abstract methods
	//

	public abstract double getImpliedQuote();

	//
	// implements Observer
	//

	@Override
	public void update(final Observable o, final Object arg) {
		this.notifyObservers(arg); // FIXME: maybe all calls to notifyObservers should forward "arg" in entire JQuantLib ???
	}

	/**
	 * Implements multiple inheritance via delegate pattern to an inner class
	 * 
	 * @see Observable
	 */
	private final Observable	delegatedObservable	= new DefaultObservable(this);

	@Override
	public final void addObserver(final Observer observer) {
		delegatedObservable.addObserver(observer);
	}

	@Override
	public final int countObservers() {
		return delegatedObservable.countObservers();
	}

	@Override
	public final void deleteObserver(final Observer observer) {
		delegatedObservable.deleteObserver(observer);
	}

	@Override
	public final void notifyObservers() {
		delegatedObservable.notifyObservers();
	}

	@Override
	public final void notifyObservers(final Object arg) {
		delegatedObservable.notifyObservers(arg);
	}

	@Override
	public final void deleteObservers() {
		delegatedObservable.deleteObservers();
	}

	@Override
	public final List<Observer> getObservers() {
		return delegatedObservable.getObservers();
	}

}
