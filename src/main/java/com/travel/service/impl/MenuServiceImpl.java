package com.travel.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.CityDao;
import com.travel.dao.CommentDao;
import com.travel.dao.MenuDao;
import com.travel.file.CreateFile;
import com.travel.pojo.City;
import com.travel.pojo.Comment;
import com.travel.pojo.Menu;
import com.travel.pojo.User;
import com.travel.service.MenuService;
import com.travel.util.ConstantUtil;
import com.travel.util.PhpFileContent;

@Service
public class MenuServiceImpl implements MenuService {
	private static Logger logger = LoggerFactory
			.getLogger(MenuServiceImpl.class);
	@Autowired
	private MenuDao mDao;
	@Autowired
	private CityDao cDao;
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<Menu> getMenuList(Integer pageNow, Integer pageSize) {
		List<Menu> mList = mDao.getMenuList(pageNow, pageSize);
		logger.info("mList==="+mList);
		try {
			 
				if(mList.size() == 1){
					Menu menu = mList.get(0);
					 if(menu.getId() == null){
						 return null;
					 }
				}
				for (int i = 0; i < mList.size(); i++) {
					Menu menu = mList.get(i);
					//logger.info(menu.getDescription() + "====");
					if (menu.getDescription() == null) {
						menu.setDescription("");
					}
				}
				
				List<City> cList = cDao.getCityList();
				for (Menu mn : mList) {
					for (City ct : cList) {
						if (mn.getSrcPlace().equals(ct.getCityCode())) {
							mn.setSrcCode(mn.getSrcPlace());
							mn.setSrcPlace(ct.getChineseName());
							
						}
						if (mn.getDestPlace().equals(ct.getCityCode())) {
							mn.setDestCode(mn.getDestPlace());
							mn.setDestPlace(ct.getChineseName());
						}
					}
				}
				
				logger.info("mList===length===" + mList.size());
			 
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		return mList;
	}

	@Override
	public Object addMenu(String fileDir, String srcPlace, String destPlace,
			String descripton, String href,String country,String level,String show,String image_url) {
		/**
		 * 判断菜单是否已存在
		 */
		List<Menu> existList = mDao.isMenuExists(srcPlace, destPlace, country);
		logger.info("existList.size==="+existList.size());
		if(existList.size() > 0){
			return "EXIST";
		}
		image_url = CreateFile.getFileName(image_url);
		logger.info("srcPlace====="+srcPlace);
		String fromP = srcPlace.split("\\&")[1];
		String fromCode = srcPlace.split("\\&")[0];
		String toP = destPlace.split("\\&")[1];
		String toCode = destPlace.split("\\&")[0];
		String fromEn = srcPlace.split("\\&")[2].toLowerCase();
		String toEn = destPlace.split("\\&")[2].toLowerCase();
		logger.info("fromEn====="+fromEn);
		
		if("".equals(descripton)){
			descripton = PhpFileContent.getDefaultComments(fromP, toP);
		}
		 
		
		/**
		 * 创建cheap-flights-from-seattle-to-shanghai/index.html
		 */
		String menuDir = ConstantUtil.BASE_DIR+"cheap-flights-from-"+fromEn+"-to-"+toEn+"/";
		CreateFile.isDirExist(menuDir);
		String menuName = menuDir+"index.html";
		logger.info("index menu name="+menuName);
		CreateFile.createFile(menuName, PhpFileContent.phpSingleHtmlContent(
				fromP, toP, fromCode, toCode,country));
		
		/**
		 * 创建new-cheap-flights/nyc_bjs.php
		 */
		String fileName = ConstantUtil.BASE_DIR+"new-cheap-flights/" + fromCode.toLowerCase() + "_" + toCode.toLowerCase()
				+ ".php";
		CreateFile.createFile(fileName, PhpFileContent.phpSingleHtmlContent(
				fromP, toP, fromCode, toCode,country));
		
		href = "./"+"cheap-flights-from-"+fromEn+"-to-"+toEn;
		logger.info("href="+href);
		Integer flag = 0;
		try {
			mDao.addMenu(fromCode, toCode, descripton,country,level,show, image_url);
			Integer id = mDao.getId(fromCode, toCode);
				flag = mDao.addDescription(id, descripton, href);
				List<Menu> commentList = new ArrayList<Menu>();
				Comment comment = new Comment();
				List<Comment> commentListTmp = commentDao.getCommentById(id+"");
				
				if(!commentListTmp.isEmpty()){
					comment = commentListTmp.get(0);
					String[] commentArry = comment.getDescription().split("\n");
					for (int i = 0; i < commentArry.length; i++) {
						Menu m = new Menu();
						m.setDescription(commentArry[i]);
						logger.info(m.getDescription());
						commentList.add(m);
					}
					CreateFile.createFile(ConstantUtil.BASE_DIR+fromCode.toLowerCase()+"_"+toCode.toLowerCase()+"_"+"price.php", PhpFileContent.commonRequestPhp(fromP, toP, fromCode, toCode,country,commentList));
				}
				
			
			logger.info("id===" + id + ",flag===" + flag);
		} catch (Exception e) {
			e.printStackTrace();
			
			return flag;
		}

		return flag;
	}

	@Override
	public List<Menu> webMenuList(String country) {
		List<Menu> mList = null;
		try {
			mList = mDao.getMenuList(country);
			List<City> cList = cDao.getCityList();

			for (Menu mn : mList) {
				for (City ct : cList) {
					if (mn.getSrcPlace().equals(ct.getCityCode())) {
						mn.setSrcCode(mn.getSrcPlace());
						mn.setSrcPlace(ct.getChineseName());
						
					}
					if (mn.getDestPlace().equals(ct.getCityCode())) {
						mn.setDestCode(mn.getDestPlace());
						mn.setDestPlace(ct.getChineseName());
					}
				}
			}
			User user = (User) session.getAttribute("user");
			logger.info("menuList.length===" + mList.size()
					+ ",cityList.length===" + cList.size() + ",userrole==="
					+ user.getStatus());
			if (user != null && user.getStatus() == 2) {
				Menu menuAdmin = new Menu();
				menuAdmin.setDescription("");
				menuAdmin.setSrcPlace(ConstantUtil.BACK_MANAGE);
				menuAdmin.setDestPlace("");
				menuAdmin.setHref("/v1/index.html");
				mList.add(menuAdmin);
			}
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
			return mList;
		}
		return mList;
	}

	@Override
	public List<Menu> getComment(String fromCode, String toCode) {
		return mDao.getComment(fromCode, toCode);
	}

	@Override
	public Integer deleteMenu(String id) {
		int flag = 0;
		flag = mDao.deleteMenu(id) + mDao.deleteComment(id);
		logger.info("deleteFlag === "+flag);
		return flag;
	}

	@Override
	public Integer addComment(String id, String comment,String fromCode,String toCode) {
		int flag = 0;
		flag = mDao.addComment(id, comment);
		City fromCity = mDao.getCityByCode(fromCode);
		City toCity = mDao.getCityByCode(toCode);
		String fromP = fromCity.getChineseName();
		String country = fromCity.getCountry();
		String toP = toCity.getCountry();
		String[] fromEnArry = fromCity.getEnglishName().split(" ");
		String [] toEnArry = toCity.getEnglishName().split(" ");
		String fromEn = "";
		String toEn = "";
		for(int i = 0 ; i < fromEnArry.length ; i++){
			if(fromEnArry.length == i || fromEnArry.length == 1){
				fromEn +=fromEnArry[i];
			}else{
				fromEn +=fromEnArry[i]+"-";
			}
			
		}
		
		for(int i = 0 ; i < toEnArry.length ; i++){
			if(toEnArry.length == i ||  toEnArry.length== 1){
				toEn +=toEnArry[i];
			}else{
				toEn +=toEnArry[i]+"-";
			}
			
		}
		
		//logger.info("fromCode=="+fromCode+",fromP=="+fromP+",toCode=="+toCode+",toP=="+toP+",country=="+country);
		List<Menu> commentList = mDao.getComment(fromCode, toCode);
		CreateFile.createFile("/opt/www/e-traveltochina/"+fromCode.toLowerCase()+"_"+toCode.toLowerCase()+"_"+"price.php", PhpFileContent.commonRequestPhp(fromP, toP, fromCode, toCode,country,commentList));
		
		
		logger.info("addcomment flag === "+flag);
		
		String menuDir = ConstantUtil.BASE_DIR+"cheap-flights-from-"+fromEn+"-to-"+toEn+"/";
		CreateFile.isDirExist(menuDir);
		String menuName = menuDir+"index.html";
		logger.info(menuName+"....................===================");
		logger.info("index menu name="+menuName);
		CreateFile.createFile(menuName, PhpFileContent.phpSingleHtmlContent(
				fromP, toP, fromCode, toCode,country));
		return flag;
	}

	@Override
	public List<Comment> getComments(String id) {
		
		return mDao.getComments(id);
	}

	@Override
	public Integer deleteCommentByCid(String cid) {
		logger.info("cid=="+cid);
		
		City fromCity = mDao.getFromCityByCid(cid);
		City toCity = mDao.getToCityByCid(cid);
		String fromCode = fromCity.getCityCode();
		String fromP = fromCity.getChineseName();
		String toCode = toCity.getCityCode();
		String toP = toCity.getChineseName();
		String country = fromCity.getCountry();
		logger.info("fromCode=="+fromCode+",fromP=="+fromP+",toCode=="+toCode+",toP=="+toP+",country=="+country);
		int delFlag = mDao.deleteCommentByCid(cid);
		List<Menu> commentList = mDao.getComment(fromCode, toCode);
		CreateFile.createFile("/opt/www/e-traveltochina/"+fromCode.toLowerCase()+"_"+toCode.toLowerCase()+"_"+"price.php", PhpFileContent.commonRequestPhp(fromP, toP, fromCode, toCode,country,commentList));
		return delFlag;
	}

	@Override
	public List<Comment> getComments(String fromCode, String toCode) {
		return mDao.getComments(fromCode, toCode);
	}
public static void main(String[] args) {
	String name = "NYC&纽约&New York".split("\\&")[2].toLowerCase();
	String[] arry = name.split(" ");
	System.out.println(arry.length);
	System.out.println(name.replace(" ", "-"));
}

}
