package com.product.jsonUtils;

import java.lang.ref.SoftReference;

public class CountdownUtil {
	private int day = 8, hour = 8, minute = 8, second = 8;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public CountdownUtil(int day, int hour, int minute, int second) {
		super();
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;

	}

	public void jishi() {
		new SoftReference<Thread>(new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {

					if (second > 0) {
						second -= 1;
					} else {
						if (minute > 0) {
							minute -= 1;
							second += 59;
						} else {
							if (hour > 0) {
								hour -= 1;
								minute += 59;
								second += 59;
							} else {
								if (day > 0) {

									day -= 1;
									hour += 23;
									minute += 59;
									second += 59;
								} else {
									day = 8;
									hour = 8;
									minute = 8;
									second = 7;
								}
							}
						}
					}
					// h.sendEmptyMessage(1);
					try {
						Thread.sleep(990);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
	}

}
