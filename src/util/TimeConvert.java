package util;

import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

public class TimeConvert {
	
	public static double CivilT2SimT(LocalDateTime st, LocalDateTime ed) {
		return Seconds.secondsBetween(st, ed).getSeconds();
	}
	
	public static LocalDateTime SimT2CivilT(LocalDateTime st, double sim_t) {
		return st.plusSeconds((int) sim_t);
	}

}
