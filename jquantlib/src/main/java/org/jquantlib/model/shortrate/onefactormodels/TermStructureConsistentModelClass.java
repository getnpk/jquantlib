/*
Copyright (C) 2008 Praneet Tiwari

This source code is release under the BSD License.

This file is part of JQuantLib, a free-software/open-source library
for financial quantitative analysts and developers - http://jquantlib.org/

JQuantLib is free software: you can redistribute it and/or modify it
under the terms of the JQuantLib license.  You should have received a
copy of the license along with this program; if not, please email
<jquant-devel@lists.sourceforge.net>. The license is also available online at
<http://www.jquantlib.org/index.php/LICENSE.TXT>.

This program is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE.  See the license for more details.

JQuantLib is based on QuantLib. http://quantlib.org/
When applicable, the original copyright notice follows this notice.
 */
package org.jquantlib.model.shortrate.onefactormodels;

import java.util.List;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;

/**
 * 
 * @author Praneet Tiwari
 */
// ! Term-structure consistent model class
/*
 * ! This is a base class for models that can reprice exactly any discount bond.
 * 
 * \ingroup shortrate
 */
public class TermStructureConsistentModelClass implements Observable {

    public TermStructureConsistentModelClass(final Handle<YieldTermStructure> termStructure) {
        termStructure_ = (termStructure);
    }

    public final Handle<YieldTermStructure> termStructure() {
        return termStructure_;
    }

    private Handle<YieldTermStructure> termStructure_;

    // cosmetic methods, what to do?
    @Override
    public void addObserver(Observer observer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int countObservers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Observer> getObservers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteObserver(Observer observer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteObservers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyObservers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyObservers(Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
