/*
 Copyright (C) 2007 Richard Gomes

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
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
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2007 StatPro Italia srl

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

import org.jquantlib.math.randomnumbers.RandomNumberGenerator;
import org.jquantlib.math.statistics.Statistics;
import org.jquantlib.methods.montecarlo.Variate;

/**
 *
 * Base class for Monte Carlo engines
 * <p>
 * Eventually this class might offer greeks methods. Deriving a class from McSimulation gives an easy way to write a Monte Carlo
 * engine.
 *
 * @ee McVanillaEngine
 *
 * @author Richard Gomes
 */
public class MCSimulation<MC extends Variate, RNG extends RandomNumberGenerator, S extends Statistics> {



}



//template <template <class> class MC, class RNG, class S = Statistics>
//class McSimulation {
//  public:
//    typedef typename MonteCarloModel<MC,RNG,S>::path_generator_type
//        path_generator_type;
//    typedef typename MonteCarloModel<MC,RNG,S>::path_pricer_type
//        path_pricer_type;
//    typedef typename MonteCarloModel<MC,RNG,S>::stats_type
//        stats_type;
//    typedef typename MonteCarloModel<MC,RNG,S>::result_type result_type;
//
//    virtual ~McSimulation() {}
//    //! add samples until the required absolute tolerance is reached
//    result_type value(Real tolerance,
//                      Size maxSamples = QL_MAX_INTEGER,
//                      Size minSamples = 1023) const;
//    //! simulate a fixed number of samples
//    result_type valueWithSamples(Size samples) const;
//    //! error estimated using the samples simulated so far
//    result_type errorEstimate() const;
//    //! access to the sample accumulator for richer statistics
//    const stats_type& sampleAccumulator(void) const;
//    //! basic calculate method provided to inherited pricing engines
//    void calculate(Real requiredTolerance,
//                   Size requiredSamples,
//                   Size maxSamples) const;
//  protected:
//    McSimulation(bool antitheticVariate,
//                 bool controlVariate)
//    : antitheticVariate_(antitheticVariate),
//      controlVariate_(controlVariate) {}
//    virtual boost::shared_ptr<path_pricer_type> pathPricer() const = 0;
//    virtual boost::shared_ptr<path_generator_type> pathGenerator()
//                                                               const = 0;
//    virtual TimeGrid timeGrid() const = 0;
//    virtual boost::shared_ptr<path_pricer_type> controlPathPricer() const {
//        return boost::shared_ptr<path_pricer_type>();
//    }
//    virtual boost::shared_ptr<PricingEngine> controlPricingEngine() const {
//        return boost::shared_ptr<PricingEngine>();
//    }
//    virtual result_type controlVariateValue() const {
//        return Null<result_type>();
//    }
//    template <class Sequence>
//    static Real maxError(const Sequence& sequence) {
//        return *std::max_element(sequence.begin(), sequence.end());
//    }
//    static Real maxError(Real error) {
//        return error;
//    }
//
//    mutable boost::shared_ptr<MonteCarloModel<MC,RNG,S> > mcModel_;
//    bool antitheticVariate_, controlVariate_;
//};
//
//
//// inline definitions
//template <template <class> class MC, class RNG, class S>
//inline typename McSimulation<MC,RNG,S>::result_type
//    McSimulation<MC,RNG,S>::value(Real tolerance,
//                                          Size maxSamples,
//                                          Size minSamples) const {
//    Size sampleNumber =
//        mcModel_->sampleAccumulator().samples();
//    if (sampleNumber<minSamples) {
//        mcModel_->addSamples(minSamples-sampleNumber);
//        sampleNumber = mcModel_->sampleAccumulator().samples();
//    }
//
//    Size nextBatch;
//    Real order;
//    result_type error(mcModel_->sampleAccumulator().errorEstimate());
//    while (maxError(error) > tolerance) {
//        QL_REQUIRE(sampleNumber<maxSamples,
//                   "max number of samples (" << maxSamples
//                   << ") reached, while error (" << error
//                   << ") is still above tolerance (" << tolerance << ")");
//
//        // conservative estimate of how many samples are needed
//        order = maxError(error*error)/tolerance/tolerance;
//        nextBatch =
//            Size(std::max<Real>(sampleNumber*order*0.8-sampleNumber,
//                                minSamples));
//
//        // do not exceed maxSamples
//        nextBatch = std::min(nextBatch, maxSamples-sampleNumber);
//        sampleNumber += nextBatch;
//        mcModel_->addSamples(nextBatch);
//        error = result_type(mcModel_->sampleAccumulator().errorEstimate());
//    }
//
//    return result_type(mcModel_->sampleAccumulator().mean());
//}
//
//
//template <template <class> class MC, class RNG, class S>
//inline typename McSimulation<MC,RNG,S>::result_type
//    McSimulation<MC,RNG,S>::valueWithSamples(Size samples) const {
//
//    Size sampleNumber = mcModel_->sampleAccumulator().samples();
//
//    QL_REQUIRE(samples>=sampleNumber,
//               "number of already simulated samples (" << sampleNumber
//               << ") greater than requested samples (" << samples << ")");
//
//    mcModel_->addSamples(samples-sampleNumber);
//
//    return result_type(mcModel_->sampleAccumulator().mean());
//}
//
//
//template <template <class> class MC, class RNG, class S>
//inline void McSimulation<MC,RNG,S>::calculate(Real requiredTolerance,
//                                              Size requiredSamples,
//                                              Size maxSamples) const {
//
//    QL_REQUIRE(requiredTolerance != Null<Real>() ||
//               requiredSamples != Null<Size>(),
//               "neither tolerance nor number of samples set");
//
//    //! Initialize the one-factor Monte Carlo
//    if (this->controlVariate_) {
//
//        result_type controlVariateValue = this->controlVariateValue();
//        QL_REQUIRE(controlVariateValue != Null<result_type>(),
//                   "engine does not provide "
//                   "control-variation price");
//
//        boost::shared_ptr<path_pricer_type> controlPP =
//            this->controlPathPricer();
//        QL_REQUIRE(controlPP,
//                   "engine does not provide "
//                   "control-variation path pricer");
//
//        this->mcModel_ =
//            boost::shared_ptr<MonteCarloModel<MC,RNG,S> >(
//                new MonteCarloModel<MC,RNG,S>(
//                       pathGenerator(), this->pathPricer(), stats_type(),
//                       this->antitheticVariate_, controlPP,
//                       controlVariateValue));
//    } else {
//        this->mcModel_ =
//            boost::shared_ptr<MonteCarloModel<MC,RNG,S> >(
//                new MonteCarloModel<MC,RNG,S>(
//                       pathGenerator(), this->pathPricer(), S(),
//                       this->antitheticVariate_));
//    }
//
//    if (requiredTolerance != Null<Real>()) {
//        if (maxSamples != Null<Size>())
//            this->value(requiredTolerance, maxSamples);
//        else
//            this->value(requiredTolerance);
//    } else {
//        this->valueWithSamples(requiredSamples);
//    }
//
//}
//
//template <template <class> class MC, class RNG, class S>
//inline typename McSimulation<MC,RNG,S>::result_type
//    McSimulation<MC,RNG,S>::errorEstimate() const {
//    return mcModel_->sampleAccumulator().errorEstimate();
//}
//
//template <template <class> class MC, class RNG, class S>
//inline const typename McSimulation<MC,RNG,S>::stats_type&
//McSimulation<MC,RNG,S>::sampleAccumulator() const {
//    return mcModel_->sampleAccumulator();
//}
