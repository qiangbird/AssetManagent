package com.augmentum.ams.service.todo;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.dao.todo.ToDoDao;
import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public class ToDoServiceTest extends BaseCaseTest {

	@Autowired
	private ToDoService todoService;
	@Autowired
	private UserCustomColumnsService userCustomColumnsService;
	@Autowired
	private ToDoDao todoDao;
	@Autowired
	private BaseHibernateDao<ToDo> baseHibernateDao;
	
	
	private Logger logger = Logger.getLogger(ToDoServiceTest.class);
	
	@Test
	public void testFindReturnedAsset() {
		Page<ToDo> page = new Page<ToDo>();
		page.setPageSize(20);
		
		List<ToDo> todos = todoService.findReturnedAsset();
		for (ToDo todo : todos) {
			logger.info(todos.size() + "--" + todo.getAsset().getAssetId());
		}
		
		List<UserCustomColumn> userCustomColumnList = userCustomColumnsService.findUserCustomColumns("todo", "T00245");
		JSONArray array = SearchCommonUtil.formatReturnedAndReceivedAssetTOJSONArray(todos, userCustomColumnList, "8,0");
		logger.info(array.size() + "--" + array);
	} 
	
	@Test
	public void testConfirmReturnedAsset() {
		todoService.confirmReturnedAndReceivedAsset("4028961242fa20eb0142fb206b5c123d", "AVAILABLE");
	}
	
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateIndex() {
        String hql = "FROM ToDo";
        List<ToDo> list = (List<ToDo>)todoDao.getHibernateTemplate().find(hql);
        Class<ToDo>[] clazzes = new Class[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ToDo todo = list.get(i);
            Class clazz =  todo.getClass();
            clazzes[i] = clazz;
        }
        try {
            baseHibernateDao.createIndex(clazzes);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindToDoListBySearchCondition() {
        SearchCondition sc = new SearchCondition();
        Page<ToDo> page = todoService.findToDoListBySearchCondition(sc, null);
        logger.info(page.getResult().size());
    }
    
}
