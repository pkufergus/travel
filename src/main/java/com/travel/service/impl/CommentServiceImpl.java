package com.travel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.CommentDao;
import com.travel.dao.MenuDao;
import com.travel.file.CreateFile;
import com.travel.pojo.City;
import com.travel.pojo.Comment;
import com.travel.pojo.Menu;
import com.travel.service.CommentService;
import com.travel.service.MenuService;
import com.travel.util.ConstantUtil;
import com.travel.util.PhpFileContent;

@Service
public class CommentServiceImpl implements CommentService {
	private static Logger logger = LoggerFactory
			.getLogger(CommentServiceImpl.class);
	@Autowired
	private CommentDao cDao;
	@Autowired
	private MenuDao mDao;
	@Autowired
	private MenuService msi;

	@Override
	public Comment getCommentById(String id) {

		Comment comment = new Comment();
		try {
			List<Comment> cList = cDao.getCommentById(id);
			if (!cList.isEmpty()) {
				comment = cList.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return comment;
		}
		return comment;
	}

	@Override
	public Integer updateComment(String id, String description,
			String fromCode,String toCode,String level,String show,String image_url) {
		
		if(!"".equals(image_url)){
			image_url = CreateFile.getFileName(image_url);
		}
		logger.info("show="+show+",image_url="+image_url);
		int updateFlag = cDao.updateComment(id, description);
		List<Comment> commentList = cDao.getCommentById(id);
		Comment comment = new Comment();
		try{
			int levelFlag = cDao.updateLevel(level,id, show,image_url);
			logger.info("level Flag -== "+levelFlag);
			City fromCity = mDao.getCityByCode(fromCode);
			City toCity = mDao.getCityByCode(toCode);
			String fromP = fromCity.getChineseName();
			String country = fromCity.getCountry();
			String toP = toCity.getChineseName();
			String fromEn = "";
			String toEn = "";
			String[] fromEnArry = fromCity.getEnglishName().split(" ");
			String [] toEnArry = toCity.getEnglishName().split(" ");
			for(int i = 0 ; i < fromEnArry.length ; i++){
				if(fromEnArry.length == i || fromEnArry.length == 1){
					fromEn +=fromEnArry[i];
				}else{
					fromEn +=fromEnArry[i]+"-";
				}
				
			}
			
			for(int i = 0 ; i < toEnArry.length ; i++){
				 
				if(toEnArry.length == i || toEnArry.length ==1){
					toEn +=toEnArry[i];
				}else{
					toEn +=toEnArry[i]+"-";
				}
				
			}
			fromEn = fromCity.getEnglishName().replace(" ", "-");
			toEn = toCity.getEnglishName().replace(" ", "-");
			
			logger.info("fromCode=="+fromCode+",fromP=="+fromP+",toCode=="+toCode+",toP=="+toP+",country=="+country+",fromEn="+fromEn+",toEn="+toEn);
			String menuDir = ConstantUtil.BASE_DIR+"cheap-flights-from-"+fromEn.toLowerCase()+"-to-"+toEn.toLowerCase()+"/";
			CreateFile.isDirExist(menuDir);
			String menuName = menuDir+"index.html";
			logger.info(menuName+"....................===================");
			logger.info("index menu name="+menuName);
			CreateFile.createFile(menuName, PhpFileContent.phpSingleHtmlContent(
					fromP, toP, fromCode, toCode,country));
		if (!commentList.isEmpty()) {
			List<Menu> menuList = new ArrayList<Menu>();
			comment = commentList.get(0);
			String[] commentArry = comment.getDescription().split("\n");
			for (int i = 0; i < commentArry.length; i++) {
				Menu m = new Menu();
				m.setDescription(commentArry[i]);
				//logger.info(m.getDescription());
				menuList.add(m);
			}
			//logger.info("menuList.size==="+menuList.size());
			CreateFile.createFile(ConstantUtil.BASE_DIR+fromCode.toLowerCase()+"_"+toCode.toLowerCase()+"_"+"price.php", PhpFileContent.commonRequestPhp(fromP, toP, fromCode, toCode,country,menuList));
		}
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
			return updateFlag;
		}
		return updateFlag;
	}

	@Override
	public String resetAllMenu() {
		String result = "0";
		try{
			logger.info("1111");
		List<Menu> lm = msi.getMenuList(1, 10);
		logger.info("lmsize = " + lm.size());
		for(Menu menu : lm){
			Integer id = menu.getId();
			Comment comment = this.getCommentById(id+"");
			this.updateComment(id+"", comment.getDescription(),
					menu.getSrcCode(), menu.getDestCode(),
					comment.getLevel(), comment.getShow(), comment.getImage_url());
		logger.info("id="+id);
		}
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
			return "1";
		}
		return result;
	}

}
