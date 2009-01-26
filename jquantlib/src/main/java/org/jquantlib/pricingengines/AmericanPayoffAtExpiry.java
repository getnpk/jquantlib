/*
 Copyright (C) 2009 Richard Gomes

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

/*
 Copyright (C) 2004 Ferdinando Ametrano

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
*/

package org.jquantlib.pricingengines;

import org.jquantlib.instruments.AssetOrNothingPayoff;
import org.jquantlib.instruments.CashOrNothingPayoff;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.instruments.Option.Type;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;

/**
 * Analytical formulae for american exercise with payoff at expiry
 * 
 * @author Jose Coll
 */

public class AmericanPayoffAtExpiry {

    private final /* @DiscountFactor */ double discount;
    
    double forward;
    double /* @Volatility */ stdDev;
    double strike, K, DKDstrike;
    double mu, log_H_S;
    double D1, D2, cum_d1, cum_d2, n_d1, n_d2;
    double alpha, beta, DalphaDd1, DbetaDd2;
    boolean inTheMoney;
    double Y, DYDstrike, X, DXDstrike;    
    
    public AmericanPayoffAtExpiry(double spot, double discount, double dividendDiscount, double variance, StrikedTypePayoff strikedTypePayoff) {
        super();
        this.discount = discount;
        
        if (spot <= 0.0)
            throw new IllegalArgumentException("positive spot value required: " + forward + " not allowed");
                
        if (discount <= 0.0)
            throw new IllegalArgumentException("positive discount required: " + discount + " not allowed");
        
        if (dividendDiscount <= 0.0)
            throw new IllegalArgumentException("positive dividend discount required: " + dividendDiscount + " not allowed");

        if (variance < 0.0)
            throw new IllegalArgumentException("negative variance: " + variance + " not allowed");
        
        forward = spot * dividendDiscount / discount;
        stdDev = Math.sqrt(variance);

        Option.Type optionType = strikedTypePayoff.getOptionType();
        strike = strikedTypePayoff.getStrike();
        
        mu = Math.log(dividendDiscount / discount) / variance - 0.5;
        
        // binary cash-or-nothing payoff ?
        if (strikedTypePayoff instanceof CashOrNothingPayoff) {
            CashOrNothingPayoff coo = (CashOrNothingPayoff) strikedTypePayoff;
            K = coo.getCashPayoff();
            DKDstrike = 0.0;
        }
        
        // binary asset-or-nothing payoff ?
        if (strikedTypePayoff instanceof AssetOrNothingPayoff) {
            K = forward;
            DKDstrike = 0.0;
            mu += 1.0;
        }
        
        log_H_S = Math.log(strike / spot);
        
        if (variance >= Math.E) {
            D1 = log_H_S / stdDev + mu * stdDev;
            D2 = D1 - 2.0 * mu * stdDev;
            CumulativeNormalDistribution f = new CumulativeNormalDistribution();
            cum_d1 = f.evaluate(D1);
            cum_d2 = f.evaluate(D2);
            n_d1 = f.derivative(D1);
            n_d2 = f.derivative(D2);
        }
        else {
            if (log_H_S > 0) {
                cum_d1 = 1.0;
                cum_d2 = 1.0;
            }
            else {
                cum_d1 = 0.0;
                cum_d2 = 0.0;                
            }
            n_d1 = 0.0;
            n_d2 = 0.0;
        }

        // up-and-in cash-(at-hit)-or-nothing option
        // a.k.a. american call with cash-or-nothing payoff
        if (optionType.equals(Type.CALL)) {
            if (strike > spot) {
                alpha     = 1.0-cum_d2;//  N(-d2)
                DalphaDd1 =    -  n_d2; // -n( d2)
                beta      = 1.0-cum_d1;//  N(-d1)
                DbetaDd2  =    -  n_d1; // -n( d1)
            } else {
                alpha     = 0.5;
                DalphaDd1 = 0.0;
                beta      = 0.5;
                DbetaDd2  = 0.0;
            }            
        }
        // down-and-in cash-(at-hit)-or-nothing option
        // a.k.a. american put with cash-or-nothing payoff
        else if (optionType.equals(Type.PUT)) {
            if (strike < spot) {
                alpha     =     cum_d2;//  N(d2)
                DalphaDd1 =       n_d2; //  n(d2)
                beta      =     cum_d1;//  N(d1)
                DbetaDd2  =       n_d1; //  n(d1)
            } else {
                alpha     = 0.5;
                DalphaDd1 = 0.0;
                beta      = 0.5;
                DbetaDd2  = 0.0;
            }            
        }
        else {
            throw new IllegalArgumentException("invalid option type");
        }
        
        inTheMoney = (optionType.equals(Type.CALL) && strike < spot) ||
                     (optionType.equals(Type.PUT) && strike > spot);
        if (inTheMoney) {
            Y         = 1.0;
            X         = 1.0;
            DYDstrike = 0.0;
            DXDstrike = 0.0; 
        } else {
            Y = 1.0;
            X = Math.pow(strike / spot, 2.0 * mu);
//            DXDstrike_ = ......;
        }                        
    }
    
    public /* @Price */ double value() /* @ReadOnly */ {
        /* @Price */ final double result = discount * K * (Y * alpha + X * beta);
        return result;
    }   
    
}