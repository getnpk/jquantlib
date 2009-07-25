package org.jquantlib.cashflow;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.math.Array;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Schedule;

public class IborLeg {
    // ! helper class building a sequence of capped/floored ibor-rate coupons
    private Schedule schedule_;
    private IborIndex index_;
    private Array notionals_;
    private DayCounter paymentDayCounter_;
    private BusinessDayConvention paymentAdjustment_;
    private Array fixingDays_;
    private Array gearings_;
    private Array spreads_;
    private Array caps_, floors_;
    private boolean inArrears_, zeroPayments_;

    public IborLeg(final Schedule schedule, final IborIndex index) {
        schedule_ = (schedule);
        index_ = (index);
        paymentAdjustment_ = BusinessDayConvention.FOLLOWING;
        inArrears_ = (false);
        zeroPayments_ = (false);
    }

    public final IborLeg withNotionals(/* Real */double notional) {
        notionals_ = new Array(new double[] { notional });// std::vector<Real>(1,notional);
        return this;
    }

    public final IborLeg withNotionals(Array notionals) {
        notionals_ = notionals;
        return this;
    }

    public final IborLeg withPaymentDayCounter(final DayCounter dayCounter) {
        paymentDayCounter_ = dayCounter;
        return this;
    }

    public final IborLeg withPaymentAdjustment(BusinessDayConvention convention) {
        paymentAdjustment_ = convention;
        return this;
    }

    public final IborLeg withFixingDays(/* Natural */double fixingDays) {
        fixingDays_ = new Array(new double[] { fixingDays });// std::vector<Natural>(1,fixingDays);
        return this;
    }

    public final IborLeg withFixingDays(final Array fixingDays) {
        fixingDays_ = fixingDays;
        return this;
    }

    public IborLeg withGearings(/* Real */double gearing) {
        gearings_ = new Array(new double[] { gearing });
        return this;
    }

    public IborLeg withGearings(Array gearings) {
        gearings_ = gearings;
        return this;
    }

    public IborLeg withSpreads(/* Spread */double spread) {
        spreads_ = new Array(new double[] { spread });
        return this;
    }

    public IborLeg withSpreads(Array spreads) {
        spreads_ = spreads;
        return this;
    }

    public IborLeg withCaps(/* @Rate */double cap) {
        caps_ = new Array(1).fill(cap);
        return this;
    }

    public IborLeg withCaps(final Array caps) {
        caps_ = caps;
        return this;
    }

    public IborLeg withFloors(/* @Rate */double floor) {
        floors_ = new Array(1).fill(floor);
        return this;
    }

    public IborLeg withFloors(final Array floors) {
        floors_ = floors;
        return this;
    }

    public IborLeg inArrears(boolean flag) {
        inArrears_ = flag;
        return this;
    }

    public IborLeg withZeroPayments(boolean flag) {
        zeroPayments_ = flag;
        return this;
    }

    public List<CashFlow> Leg() {

        List<CashFlow> cashflows = new ArrayList<CashFlow>();
        if (true)
            throw new UnsupportedOperationException("Floating Leg has to be translated first");
        // new FloatingLeg<IborIndex, IborCoupon, CappedFlooredIborCoupon>(
        // notionals_, schedule_, index_, paymentDayCounter_,
        // paymentAdjustment_, fixingDays_, gearings_, spreads_,
        // caps_, floors_, inArrears_, zeroPayments_);
        //
        // if (caps_.empty() && floors_.empty() && !inArrears_)
        // setCouponPricer(cashflows,
        // boost::shared_ptr<FloatingRateCouponPricer>(
        // new BlackIborCouponPricer));
        return cashflows;
    }

}
