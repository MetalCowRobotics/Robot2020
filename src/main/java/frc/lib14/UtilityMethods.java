package frc.lib14;

public class UtilityMethods {
	public static double copySign(double source, double target) {
		if (0 < source)
			return Math.abs(target);
		else
			return -1 * Math.abs(target);
	}

	// determine if value is between the lower and upper inclusive of the endpoint
	public static boolean between(double value, double lower, double upper) {
		return value >= lower && value <= upper;
	}

	public static double deadZoneCalculation(double input) {
		double deadZone = 0.15;
		if (input <= deadZone && input >= -deadZone) {
			return 0;
		} else {
			return input;
		}
	}

	public static double deadZoneCalculation(double input, double deadZone) {
		if (input <= deadZone && input >= -deadZone) {
			return 0;
		} else {
			return input;
		}
	}

	public static double round(double number, int precision) {
		return (double) Math.round(number * precision) / precision;
	}

	public static double absMax(double input, double maxValue) {
		double localmaxValue = Math.abs(maxValue);

		if (input > 0) {
			return Math.max(input, localmaxValue);
		} else {
			return Math.min(input, -localmaxValue);
		}
	}

	public static double absMin(double input, double minValue) {
		double localminValue = Math.abs(minValue);

		if (input > 0) {
			return Math.min(input, localminValue);
		} else {
			return Math.max(input, -localminValue);
		}
	}
/*
	public static double map(double in, double in_min, double in_max, double out_min, double out_max) {
		if (in_min <= in_max){
			if(in <= in_min) {
				return out_min;
			} else if (in > in_max){
				return out_max;
			} else {
				return (in - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
			}
		} else if (in > in_min){
			return out_min;
		} else if (in > in_min) {
			return out_min;
		} else if(in < in_max) {
			return out_max;
		} else {
			double x = ((in-in_min) / (in_max - in_min))*(out_max - out_min) + out_min;
			return (in - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
		}
	}

	public static double limit(double input, double lowerBound, double upperBound) {
		if (input < lowerBound) {
			return lowerBound;
		}
		if (input > upperBound) {
			return upperBound;
		}
		return input;
	}
*/
}
