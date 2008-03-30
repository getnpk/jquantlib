package org.jquantlib.math;

/**
 * @author <Richard Gomes>
 */
final strictfp public class Constants {

	private Constants() {
		//deliberate
	}

	/** 1/sqrt(2) = 0.707... */
	public final static double M_SQRT_2 = 0.7071067811865475244008443621048490392848359376887;
	
	public final static double M_SQRT2PI = 2.50662827463100050242;
	public final static double M_1_SQRTPI = 0.564189583547756286948;
	public final static double M_SQRTPI = 1.77245385090551602792981;
	public final static double M_1_SQRT2PI = M_SQRT_2*M_1_SQRTPI;
	public final static double QL_EPSILON = Math.ulp(1.0);//typically about 2.2e-16
	public final static double QL_MAX_REAL = Double.MAX_VALUE;//typically about 1.8e+308
	//FIXME check this constant Double.MIN_VALUE??
	public final static double QL_MIN_POSITIVE_REAL = Double.MIN_NORMAL;//typically about 2.22E-308 

}