package com.schedule.tool;

import java.util.List;

import com.travel.pojo.MasterCount;
import com.travel.pojo.MasterData;

public class ServiceTool {

	public static String getMinPrice(List<MasterData> madList) {
		String price = "";
		try {
			if (!madList.isEmpty()) {
				price = madList.get(0).getPrice();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return price;
		}
		return price;
	}

	public static String getMinusPrice(MasterCount mc) {
		String price = "";
		mc.setDou_week_price(mc.getDou_week_price() == null ? "0" : mc
				.getDou_week_price());
		mc.setOne_month_price(mc.getOne_month_price() == null ? "0" : mc
				.getOne_month_price());
		mc.setThree_month_price(mc.getThree_month_price() == null ? "0" : mc
				.getThree_month_price());
		mc.setSix_month_price(mc.getSix_month_price() == null ? "0" : mc
				.getSix_month_price());
		Integer oneP = Integer.parseInt(mc.getDou_week_price());

		Integer twoP = Integer.parseInt(mc.getOne_month_price());
		Integer ThreeP = Integer.parseInt(mc.getThree_month_price());
		Integer fourP = Integer.parseInt(mc.getSix_month_price());
		if (0 != oneP) {
			price = oneP + "";
		}
		if (0 != twoP) {
			if (twoP < oneP) {
				price = twoP + "";
			}
		}
		if (0 != ThreeP) {
			if (ThreeP < twoP) {
				price = ThreeP + "";
			}
		}

		if (0 != fourP) {
			if (fourP < ThreeP) {
				price = fourP + "";
			}
		}

		return price;
	}

	// public static String getOneMc(){
	//
	// }
}
