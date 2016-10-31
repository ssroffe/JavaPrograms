//Seth Roffe

public class PerfectNumber {

    public static boolean isPerfectNumber(int number) {
	int sum = 1;
	int maxD = (int)Math.sqrt(number);
	
	for (int i = 2; i <= maxD; i++) {

	    if ((number % i) == 0) {
		sum += i;
		int d = number / i;
		if (d != i) {
		    sum += d;
		}
	    }
	}

	if (sum == (2*number)) {
	    return true;
	}
	else {
	    return false;
	}
    }
}
