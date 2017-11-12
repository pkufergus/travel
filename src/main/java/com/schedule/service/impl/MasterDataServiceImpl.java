package com.schedule.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schedule.dao.MasterDataDao;
import com.schedule.service.MasterDataService;
import com.schedule.tool.ServiceTool;
import com.travel.pojo.City;
import com.travel.pojo.MasterCount;
import com.travel.pojo.MasterData;
import com.travel.pojo.Menu;
import com.travel.util.DateUtil;

@Service
public class MasterDataServiceImpl implements MasterDataService {
	private static Logger logger = LoggerFactory.getLogger(MasterDataServiceImpl.class);
	@Autowired
	private MasterDataDao maDao;

	@Override
	public void collectMasterDataCount() {
		// List<MasterCount> mdList = new ArrayList<MasterCount>();
		List<Menu> mList = maDao.getMenusNotChina();
		List<City> cList = maDao.getCityName();
		
		/**
		 * 添加中文名字
		 */
		for (Menu menu : mList) {
			for (City city : cList) {
				if (menu.getSrcCode().equals(city.getCityCode())) {
					menu.setSrcPlace(city.getChineseName());
				}
				if (menu.getDestCode().equals(city.getCityCode())) {
					menu.setDestPlace(city.getChineseName());
				}
			}
		}
		String currentDateTime = DateUtil.getCurrentDate();
		logger.info(System.currentTimeMillis()+"==开始更新=currentDateTime=="+currentDateTime);
		for (Menu menu : mList) {
			String priceType = "";
			if ("USA".equals(menu.getCountry())) {
				priceType = "USD";
			} else if ("CA".equals(menu.getCountry())) {
				priceType = "CAD";
			} else {
				priceType = "USD";
			}
			List<MasterData> upList = maDao.getUpdateTime();
			String updateTime = "";
			if (!upList.isEmpty()) {
				try {
					updateTime = upList.get(0).getUpdatedDate().split(" ")[0];
				} catch (Exception e) {
					e.printStackTrace();
					updateTime = "";
				}
			}

			/***********************************************************************************/
			MasterCount oneMc = new MasterCount();
			oneMc.setFromCode(menu.getSrcCode());
			oneMc.setFromP(menu.getSrcPlace());
			oneMc.setToCode(menu.getDestCode());
			oneMc.setToP(menu.getDestPlace());
			oneMc.setUrl(menu.getHref());
			oneMc.setYear(DateUtil.currentYear(1));
			oneMc.setMonth(DateUtil.nextMonth(1));
			oneMc.setUpdate_time(updateTime);
			oneMc.setCountry(menu.getCountry());
			oneMc.setPrice_type(priceType);
			oneMc.setNewUpdateTime(currentDateTime);
			/**
			 * 两周最小票价
			 */
			String fromD1W = DateUtil.getNextMonthFirstDay(1);// 下个月1号
			String toD1W = DateUtil.getNextMonthLastDay(1);// 下月31号
			String fromD1SW = DateUtil.getNextMonthFirstDay(1, 14);// 下个月1号加14
			String toD1SW = DateUtil.getNextMonthLastDay(1, 14);// 下月31号加14

			List<MasterData> madListW = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD1W, toD1W,
					fromD1SW, toD1SW, 7, 14);

			oneMc.setDou_week_price(ServiceTool.getMinPrice(madListW));

			/**
			 * 一个月最小票价
			 */
			String fromD1M = DateUtil.getNextMonthFirstDay(1);// 下个月1号
			String toD1M = DateUtil.getNextMonthLastDay(1);// 下月31号
			String fromD1SM = DateUtil.getNextMonthFirstDay(2);// 下个月1号
			String toD1SM = DateUtil.getNextMonthLastDay(2);// 下月31号

			List<MasterData> madListM = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD1M, toD1M,
					fromD1SM, toD1SM, 14, 31);
			oneMc.setOne_month_price(ServiceTool.getMinPrice(madListM));

			/**
			 * 三个月最小票价
			 */
			String fromD1T = DateUtil.getNextMonthFirstDay(1);// 下个月1号
			String toD1T = DateUtil.getNextMonthLastDay(1);// 下月31号
			String fromD1ST = DateUtil.getNextMonthFirstDay(4);// 下个月1号
			String toD1ST = DateUtil.getNextMonthLastDay(4);// 下月31号
			List<MasterData> madListT = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD1T, toD1T,
					fromD1ST, toD1ST, 31, 93);
			oneMc.setThree_month_price(ServiceTool.getMinPrice(madListT));
			/**
			 * 六个月最小票价
			 */

			String fromD1S = DateUtil.getNextMonthFirstDay(1);// 下个月1号
			String toD1S = DateUtil.getNextMonthLastDay(1);// 下月31号
			String fromD1SS = DateUtil.getNextMonthFirstDay(7);// 下个月1号
			String toD1SS = DateUtil.getNextMonthLastDay(7);// 下月31号
			List<MasterData> madListS = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD1S, toD1S,
					fromD1SS, toD1SS, 93, 186);
			oneMc.setSix_month_price(ServiceTool.getMinPrice(madListS));

			oneMc.setMin_price(ServiceTool.getMinusPrice(oneMc));
			List<MasterCount> l1 = maDao.getCountByYearMonthCode(oneMc);
			if (l1.isEmpty()) {
				maDao.saveMasterCount(oneMc);
			} else {
				maDao.updateMasterCount(oneMc);
			}

			/***********************************************************************************/
			MasterCount twoMc = new MasterCount();
			twoMc.setFromCode(menu.getSrcCode());
			twoMc.setFromP(menu.getSrcPlace());
			twoMc.setToCode(menu.getDestCode());
			twoMc.setToP(menu.getDestPlace());
			twoMc.setUrl(menu.getHref());
			twoMc.setYear(DateUtil.currentYear(1));
			twoMc.setMonth(DateUtil.nextMonth(2));
			twoMc.setUpdate_time(updateTime);
			twoMc.setCountry(menu.getCountry());
			twoMc.setPrice_type(priceType);
			twoMc.setNewUpdateTime(currentDateTime);
			/**
			 * 两周最小票价
			 */
			String fromD2W = DateUtil.getNextMonthFirstDay(2);// 下个月1号
			String toD2W = DateUtil.getNextMonthLastDay(2);// 下月31号
			String fromD2SW = DateUtil.getNextMonthFirstDay(2, 14);// 下个月1号加14
			String toD2SW = DateUtil.getNextMonthLastDay(2, 14);// 下月31号加14

			List<MasterData> madListW2 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD2W, toD2W,
					fromD2SW, toD2SW, 7, 14);

			twoMc.setDou_week_price(ServiceTool.getMinPrice(madListW2));

			/**
			 * 一个月最小票价
			 */
			String fromD2M = DateUtil.getNextMonthFirstDay(2);// 下个月1号
			String toD2M = DateUtil.getNextMonthLastDay(2);// 下月31号
			String fromD2SM = DateUtil.getNextMonthFirstDay(3);// 下个月1号
			String toD2SM = DateUtil.getNextMonthLastDay(3);// 下月31号

			List<MasterData> madListM2 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD2M, toD2M,
					fromD2SM, toD2SM, 14, 31);
			twoMc.setOne_month_price(ServiceTool.getMinPrice(madListM2));

			/**
			 * 三个月最小票价
			 */
			String fromD2T = DateUtil.getNextMonthFirstDay(2);// 下个月1号
			String toD2T = DateUtil.getNextMonthLastDay(2);// 下月31号
			String fromD2ST = DateUtil.getNextMonthFirstDay(5);// 下个月1号
			String toD2ST = DateUtil.getNextMonthLastDay(5);// 下月31号
			List<MasterData> madListT2 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD2T, toD2T,
					fromD2ST, toD2ST, 31, 93);
			twoMc.setThree_month_price(ServiceTool.getMinPrice(madListT2));
			/**
			 * 六个月最小票价
			 */

			String fromD2S = DateUtil.getNextMonthFirstDay(2);// 下个月1号
			String toD2S = DateUtil.getNextMonthLastDay(2);// 下月31号
			String fromD2SS = DateUtil.getNextMonthFirstDay(8);// 下个月1号
			String toD2SS = DateUtil.getNextMonthLastDay(8);// 下月31号
			List<MasterData> madListS2 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD2S, toD2S,
					fromD2SS, toD2SS, 93, 186);
			twoMc.setSix_month_price(ServiceTool.getMinPrice(madListS2));

			twoMc.setMin_price(ServiceTool.getMinusPrice(twoMc));
			
			List<MasterCount> l2 = maDao.getCountByYearMonthCode(twoMc);
			if (l2.isEmpty()) {
				maDao.saveMasterCount(twoMc);
			} else {
				maDao.updateMasterCount(twoMc);
			}

			/***********************************************************************************/
			MasterCount thrMc = new MasterCount();
			thrMc.setFromCode(menu.getSrcCode());
			thrMc.setFromP(menu.getSrcPlace());
			thrMc.setToCode(menu.getDestCode());
			thrMc.setToP(menu.getDestPlace());
			thrMc.setUrl(menu.getHref());
			thrMc.setYear(DateUtil.currentYear(1));
			thrMc.setMonth(DateUtil.nextMonth(3));
			thrMc.setUpdate_time(updateTime);
			thrMc.setCountry(menu.getCountry());
			thrMc.setPrice_type(priceType);
			thrMc.setNewUpdateTime(currentDateTime);
			/**
			 * 两周最小票价
			 */
			String fromD3W = DateUtil.getNextMonthFirstDay(3);// 下个月1号
			String toD3W = DateUtil.getNextMonthLastDay(3);// 下月31号
			String fromD3SW = DateUtil.getNextMonthFirstDay(3, 14);// 下个月1号加14
			String toD3SW = DateUtil.getNextMonthLastDay(3, 14);// 下月31号加14

			List<MasterData> madListW3 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD3W, toD3W,
					fromD3SW, toD3SW, 7, 14);

			thrMc.setDou_week_price(ServiceTool.getMinPrice(madListW3));

			/**
			 * 一个月最小票价
			 */
			String fromD3M = DateUtil.getNextMonthFirstDay(3);// 下个月1号
			String toD3M = DateUtil.getNextMonthLastDay(3);// 下月31号
			String fromD3SM = DateUtil.getNextMonthFirstDay(4);// 下个月1号
			String toD3SM = DateUtil.getNextMonthLastDay(4);// 下月31号

			List<MasterData> madListM3 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD3M, toD3M,
					fromD3SM, toD3SM, 14, 31);
			thrMc.setOne_month_price(ServiceTool.getMinPrice(madListM3));

			/**
			 * 三个月最小票价
			 */
			String fromD3T = DateUtil.getNextMonthFirstDay(3);// 下个月1号
			String toD3T = DateUtil.getNextMonthLastDay(3);// 下月31号
			String fromD3ST = DateUtil.getNextMonthFirstDay(6);// 下个月1号
			String toD3ST = DateUtil.getNextMonthLastDay(6);// 下月31号
			List<MasterData> madListT3 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD3T, toD3T,
					fromD3ST, toD3ST, 31, 93);
			thrMc.setThree_month_price(ServiceTool.getMinPrice(madListT3));
			/**
			 * 六个月最小票价
			 */

			String fromD3S = DateUtil.getNextMonthFirstDay(3);// 下个月1号
			String toD3S = DateUtil.getNextMonthLastDay(3);// 下月31号
			String fromD3SS = DateUtil.getNextMonthFirstDay(9);// 下个月1号
			String toD3SS = DateUtil.getNextMonthLastDay(9);// 下月31号
			List<MasterData> madListS3 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD3S, toD3S,
					fromD3SS, toD3SS, 93, 186);
			thrMc.setSix_month_price(ServiceTool.getMinPrice(madListS3));
			thrMc.setMin_price(ServiceTool.getMinusPrice(thrMc));
			List<MasterCount> l3 = maDao.getCountByYearMonthCode(thrMc);
			if (l3.isEmpty()) {
				maDao.saveMasterCount(thrMc);
			} else {
				maDao.updateMasterCount(thrMc);
			}

			/***********************************************************************************/
			MasterCount fourMc = new MasterCount();
			fourMc.setFromCode(menu.getSrcCode());
			fourMc.setFromP(menu.getSrcPlace());
			fourMc.setToCode(menu.getDestCode());
			fourMc.setToP(menu.getDestPlace());
			fourMc.setUrl(menu.getHref());
			fourMc.setYear(DateUtil.currentYear(1));
			fourMc.setMonth(DateUtil.nextMonth(4));
			fourMc.setUpdate_time(updateTime);
			fourMc.setCountry(menu.getCountry());
			fourMc.setPrice_type(priceType);
			fourMc.setNewUpdateTime(currentDateTime);
			/**
			 * 两周最小票价
			 */
			String fromD4W = DateUtil.getNextMonthFirstDay(4);// 下个月1号
			String toD4W = DateUtil.getNextMonthLastDay(4);// 下月31号
			String fromD4SW = DateUtil.getNextMonthFirstDay(4, 14);// 下个月1号加14
			String toD4SW = DateUtil.getNextMonthLastDay(4, 14);// 下月31号加14

			List<MasterData> madListW4 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD4W, toD4W,
					fromD4SW, toD4SW, 7, 14);

			fourMc.setDou_week_price(ServiceTool.getMinPrice(madListW4));

			/**
			 * 一个月最小票价
			 */
			String fromD4M = DateUtil.getNextMonthFirstDay(4);// 下个月1号
			String toD4M = DateUtil.getNextMonthLastDay(4);// 下月31号
			String fromD4SM = DateUtil.getNextMonthFirstDay(5);// 下个月1号
			String toD4SM = DateUtil.getNextMonthLastDay(5);// 下月31号

			List<MasterData> madListM4 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD4M, toD4M,
					fromD4SM, toD4SM, 14, 31);
			fourMc.setOne_month_price(ServiceTool.getMinPrice(madListM4));

			/**
			 * 三个月最小票价
			 */
			String fromD4T = DateUtil.getNextMonthFirstDay(4);// 下个月1号
			String toD4T = DateUtil.getNextMonthLastDay(4);// 下月31号
			String fromD4ST = DateUtil.getNextMonthFirstDay(7);// 下个月1号
			String toD4ST = DateUtil.getNextMonthLastDay(7);// 下月31号
			List<MasterData> madListT4 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD4T, toD4T,
					fromD4ST, toD4ST, 31, 93);
			fourMc.setThree_month_price(ServiceTool.getMinPrice(madListT4));
			/**
			 * 六个月最小票价
			 */

			String fromD4S = DateUtil.getNextMonthFirstDay(4);// 下个月1号
			String toD4S = DateUtil.getNextMonthLastDay(4);// 下月31号
			String fromD4SS = DateUtil.getNextMonthFirstDay(10);// 下个月1号
			String toD4SS = DateUtil.getNextMonthLastDay(10);// 下月31号
			List<MasterData> madListS4 = maDao.getMasterDataList(
					menu.getSrcCode(), menu.getDestCode(), fromD4S, toD4S,
					fromD4SS, toD4SS, 93, 186);
			fourMc.setSix_month_price(ServiceTool.getMinPrice(madListS4));

			fourMc.setMin_price(ServiceTool.getMinusPrice(fourMc));
			List<MasterCount> l4 = maDao.getCountByYearMonthCode(fourMc);
			if (l4.isEmpty()) {
				maDao.saveMasterCount(fourMc);
			} else {
				maDao.updateMasterCount(fourMc);
			}

		}

	}

}
