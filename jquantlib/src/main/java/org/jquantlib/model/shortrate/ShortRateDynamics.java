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
package org.jquantlib.model.shortrate;

import org.jquantlib.lang.annotation.Rate;
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.processes.StochasticProcess1D;

/**
 * 
 * @author Praneet Tiwari
 */
public abstract class ShortRateDynamics {

    private StochasticProcess1D process_;

    public ShortRateDynamics(final StochasticProcess1D process) {
        process_ = process;
    }

    // ! Compute state variable from short rate
    public abstract Double /* @Real */variable(Double /* @Time */t, Double /* @Rate */r);

    // ! Compute short rate from state variable
    public abstract Double /* @Rate */shortRate(Double /* @Time */t, Double /* @Real */variable);

    // ! Returns the risk-neutral dynamics of the state variable
    public StochasticProcess1D process() {
        return process_;
    }
}
