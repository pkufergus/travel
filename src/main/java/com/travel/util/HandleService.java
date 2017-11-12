package com.travel.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.pojo.MasterData;
import com.travel.pojo.Menu;
import com.travel.pojo.Price;
import com.travel.pojo.PriceNew;

public class HandleService {

	private static Logger logger = LoggerFactory.getLogger(HandleService.class);

	public static Object handlePriceList(List<Menu> mList,
			List<MasterData> list1, List<MasterData> list2,
			List<MasterData> list3, List<MasterData> list4, String fromD1,
			String fromD2, String fromD3, String fromD4, String updateTime) {
		List<Price> priceList = new ArrayList<Price>();
		for (Menu menu : mList) {
			Price price = new Price();
			price.setFromP(menu.getSrcPlace());
			price.setToP(menu.getDestPlace());
			price.setMonthF(fromD1.split("\\-")[1] + "月");
			price.setMonthS(fromD2.split("\\-")[1] + "月");
			price.setMonthT(fromD3.split("\\-")[1] + "月");
			price.setMonthFo(fromD4.split("\\-")[1] + "月");
			price.setUpdateTime(updateTime.split(" ")[0]);

			for (MasterData md1 : list1) {
				logger.info("md1Price===" + md1.getPrice());
				if (md1.getDepartureCity().equals(menu.getSrcCode())
						&& md1.getArrivalCity().equals(menu.getDestCode())) {
					price.setMonthFprice(md1.getPrice().split("\\.")[0]);
				}
			}

			for (MasterData md2 : list2) {
				if (md2.getDepartureCity().equals(menu.getSrcCode())
						&& md2.getArrivalCity().equals(menu.getDestCode())) {
					price.setMonthSprice(md2.getPrice().split("\\.")[0]);
				}
			}

			for (MasterData md3 : list3) {
				if (md3.getDepartureCity().equals(menu.getSrcCode())
						&& md3.getArrivalCity().equals(menu.getDestCode())) {
					price.setMonthTprice(md3.getPrice().split("\\.")[0]);
				}
			}

			for (MasterData md4 : list4) {
				if (md4.getDepartureCity().equals(menu.getSrcCode())
						&& md4.getArrivalCity().equals(menu.getDestCode())) {
					price.setMonthFoprice(md4.getPrice().split("\\.")[0]);
				}
			}

			Float minPrice = 0f;
			try {
				minPrice = Float.parseFloat(price.getMonthFprice());
				if (minPrice > Float.parseFloat(price.getMonthSprice())) {
					minPrice = Float.parseFloat(price.getMonthSprice());
				}

				if (minPrice > Float.parseFloat(price.getMonthTprice())) {
					minPrice = Float.parseFloat(price.getMonthTprice());
				}

				if (minPrice > Float.parseFloat(price.getMonthFoprice())) {
					minPrice = Float.parseFloat(price.getMonthFoprice());
				}
				price.setMinPrice(minPrice.toString().split("\\.")[0]);
			} catch (Exception e) {
				e.printStackTrace();
				minPrice = 0f;
			}

			if ("USA".equals(menu.getCountry())) {
				price.setPriceType("USD");
			} else if ("CA".equals(menu.getCountry())) {
				price.setPriceType("CAD");
			}

			price.setHref("http://www.e-traveltochina.com/"
					+ menu.getHref().split("\\/")[1]);

			priceList.add(price);

		}
		return priceList;
	}

	public static String getMinPrice(List<PriceNew> priceNewList) {
		String price = "0";
		try {
			for (PriceNew pn : priceNewList) {
				if ("0".equals(price)) {
					if (!"暂无票价".equals(pn.getOneP())) {
						price = pn.getOneP();
					}
					if (!"暂无票价".equals(pn.getTwoP())) {
						if (Integer.parseInt(pn.getTwoP()) < Integer
								.parseInt(price)) {
							price = pn.getTwoP();
						}
					}
					if (!"暂无票价".equals(pn.getThreeP())) {
						if (Integer.parseInt(pn.getThreeP()) < Integer
								.parseInt(price)) {
							price = pn.getThreeP();
						}
					}
					if (!"暂无票价".equals(pn.getFourP())) {
						if (Integer.parseInt(pn.getFourP()) < Integer
								.parseInt(price)) {
							price = pn.getFourP();
						}
					}
					
					
					
					
				} else {
					if (!"暂无票价".equals(pn)) {
						if (Integer.parseInt(pn.getOneP()) < Integer
								.parseInt(price)) {
							price = pn.getOneP();
						}
					}
					if (!"暂无票价".equals(pn)) {
						if (Integer.parseInt(pn.getTwoP()) < Integer
								.parseInt(price)) {
							price = pn.getTwoP();
						}
					}
					if (!"暂无票价".equals(pn)) {

						if (Integer.parseInt(pn.getThreeP()) < Integer
								.parseInt(price)) {
							price = pn.getThreeP();
						}
					}
					if (!"暂无票价".equals(pn)) {
						if (Integer.parseInt(pn.getFourP()) < Integer
								.parseInt(price)) {
							price = pn.getFourP();
						}
					}
					
					
					
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return price;
		}
		return price;
	}

	public static void main(String[] args) {
		String str = "1000.41";
		System.out.println(str.split("\\.")[0]);
	}
}
