/*
Copyright (C) 2009 Ueli Hofstetter

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

package org.jquantlib.math.statistics;

import org.jquantlib.QL;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;

//TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
//FIXME: the class hierarchy is wrong :: mimic inheritence using delegate pattern (similar to TimeSeries?)
public class GenericSequenceStatistics /* TODO: implements SequenceStatistics */ {

    private static final String unsufficient_sample_weight = "sampleWeight=0, unsufficient";
    private static final String unsufficient_sample_number = "sample number <=1, unsufficient";
    private static final String null_dimension = "null dimension";
    private static final String sample_size_mismatch = "sample size mismatch";

    protected int dimension_;

    protected Statistics[] stats_;
    private double[] results_;
    private Matrix quadraticSum_;

    public GenericSequenceStatistics() {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        dimension_ = 0;
    };

    public GenericSequenceStatistics(final int dimension) {
        reset(dimension_);
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        dimension_ = 0;
    }

    public void add(final double [] data){
        add(data, 1.0);
    }

    public void add(final double [] data,
            final double  weight) {
        if (dimension_ == 0)
            // stat wasn't initialized yet
            reset(data.length);

        if(data.length != dimension_)
            throw new IllegalArgumentException(sample_size_mismatch);


        //TODO: implement this one
        /*
       quadraticSum_ += weight * outerProduct(begin, end,
                                              begin, end);*/

        for (int i=0; i<dimension_; i++)
            stats_[i].add(data[i], weight);

        throw new UnsupportedOperationException("work in progress");
    }

    public int size() {
        return dimension_;
    }

    // start void method macro ....
    public double[] mean() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].mean();
        return results_;
    }

    public double[] variance() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].variance();
        return results_;
    }

    public double[] standardDeviation() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].standardDeviation();
        return results_;
    }

    public double[] downsideVariance() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].downsideVariance();
        return results_;
    }

    public double[] downsideDeviation() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].downsideDeviation();
        return results_;
    }

    public double[] semiVariance() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].semiVariance();
        return results_;
    }

    public double[] semiDeviation() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].semiDeviation();
        return results_;
    }

    public double[] errorEstimate() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].errorEstimate();
        return results_;
    }

    public double[] skewness() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].skewness();
        return results_;
    }

    public double[] kurtosis() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].kurtosis();
        return results_;
    }

    public double[] min() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].min();
        return results_;
    }

    public double[] max() {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].max();
        return results_;
    }

    // start single argument method macros

    public double[] gaussianPercentile(final double gaussianPercentile) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].gaussianPercentile(gaussianPercentile);
        return results_;
    }

    public double[] gaussianPotentialUpside(final double gaussianPotentialUpside) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].gaussianPotentialUpside(gaussianPotentialUpside);
        return results_;
    }

    public double[] gaussianValueAtRisk(final double gaussianValueAtRisk) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].gaussianPercentile(gaussianValueAtRisk);
        return results_;
    }

    public double[] gaussianExpectedShortfall(final double gaussianExpectedShortfall) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].gaussianExpectedShortfall(gaussianExpectedShortfall);
        return results_;
    }

    public double[] gaussianShortfall(final double gaussianShortfall) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].gaussianShortfall(gaussianShortfall);
        return results_;
    }

    public double[] gaussianAverageShortfall(final double gaussianAverageShortfall) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].gaussianPercentile(gaussianAverageShortfall);
        return results_;
    }

    public double[] percentile(final double percentile) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].gaussianPercentile(percentile);
        return results_;
    }

    public double[] potentialUpside(final double potentialUpside) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].potentialUpside(potentialUpside);
        return results_;
    }

    public double[] valueAtRisk(final double valueAtRisk) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].valueAtRisk(valueAtRisk);
        return results_;
    }

    public double[] expectedShortfall(final double expectedShortfall) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].expectedShortfall(expectedShortfall);
        return results_;
    }

    public double[] regret(final double regret) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].regret(regret);
        return results_;
    }

    public double[] shortfall(final double shortfall) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].gaussianPercentile(shortfall);
        return results_;
    }

    public double[] averageShortfall(final double averageShortfall) {
        for (int i = 0; i < dimension_; i++)
            results_[i] = stats_[i].gaussianPercentile(averageShortfall);
        return results_;
    }

    public Matrix correlation() {
        final Matrix correlation = null; //TODO: code review ::  covariance();
        final Array variances = correlation.diagonal();
        for (int i = 0; i < dimension_; i++)
            for (int j = 0; j < dimension_; j++)
                if (i == j) {
                    if (variances.get(i) == 0.0)
                        correlation.set(i, j, 1.0);
                    else
                        correlation.set(i, j, correlation.get(i, j) * 1.0 / Math.sqrt(variances.get(i) * variances.get(j)));
                } else if (variances.get(i) == 0.0 && variances.get(j) == 0)
                    correlation.set(i, j, 1.0);
                else if (variances.get(i) == 0.0 || variances.get(j) == 0.0)
                    correlation.set(i, j, 1.0);
                else
                    correlation.set(i, j, correlation.get(i, j) * 1.0 / Math.sqrt(variances.get(i) * variances.get(j)));

        return correlation;
    }

    public Matrix covariance() {
        final double sampleWeight = weightSum();
        QL.require(sampleWeight > 0.0 , unsufficient_sample_weight); // QA:[RG]::verified

        final double sampleNumber = samples();
        QL.require(sampleNumber > 1.0 , unsufficient_sample_number); // QA:[RG]::verified

        final Array m = null;// TODO: code review :: mean();
        final double inv = 1.0 / sampleWeight;

        // TODO: code review :: please verify against QL/C++ code
        throw new UnsupportedOperationException("work in progress");
        //        final Matrix result = quadraticSum_.mul(inv);
        //        result.subAssign(result.outerProduct(m));
        //        result.mulAssign(sampleNumber / (sampleNumber - 1.0));
        //        return result;
    }

    public void reset(int dimension) {
        if (dimension == 0)
            dimension = dimension_; // keep the current one
        if (dimension <= 0)
            throw new IllegalArgumentException(null_dimension);
        if (dimension == dimension_)
            for (int i = 0; i < dimension_; i++)
                stats_[i].reset();
        else {
            dimension_ = dimension;
            stats_ = new Statistics[dimension];
            results_ = new double[dimension];
        }
        quadraticSum_ = new Matrix(dimension_, dimension_);
    }

    public double weightSum() {
        return (stats_.length == 0) ? 0.0 : stats_[0].weightSum();
    }

    public int samples() {
        return (stats_.length == 0) ? 0 : stats_[0].samples();
    }

}
