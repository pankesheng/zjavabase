package test.zcj.demo.designpattern.other.ioc;


public class ArticleServlet extends BaseServlet {
	
	private static final long serialVersionUID = -1242712377030741530L;
	
	private ArticleDao articleDao;
	
	
	public void doing() {
		articleDao.add();
	}
	
	
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
}
