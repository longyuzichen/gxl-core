package com.longyuzichen.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @desc  项目第一次启动时加载
 * @auto longyuzichen@126.com
 * @date 2016年11月4日 下午3:31:41
 * @version V1.0
 */
public class LongYuZiChenListener implements ServletContextListener {
	
	private final Logger log = LoggerFactory.getLogger(LongYuZiChenListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.debug("");
		log.debug("                                   "
				+"                                   \n\n\n\n\n"
				+"                  _ooOoo_  \n"
				+"                 o8888888o  \n"
				+"                 88' . '88  \n"
				+"                 (| -_- |)  \n"
				+"                  O\\ = /O  \n"
				+"              ____/`---'\\____  \n"
				+"              .   ' \\| |// `. \n" 
				+"              / \\||| : |||// \\ \n" 
				+"            / _||||| -:- |||||- \\ \n" 
				+"              | | \\\\ - /// | |  \n"
				+"            | \\_| ''\\---/'' | |  \n"
				+"             \\ .-\\__ `-` ___/-. /  \n"
				+"           ___`. .' /--.--\\ `. . __  \n"
				+".\"\" '&lt; `.___\\_&lt;|&gt;_/___.' &gt;'\"\".  \n"
				+"       | | : `- \\`.;`\\ _ /`;.`/ - ` : | |  \n"
				+"         \\ \\ `-. \\_ __\\ /__ _/ .-` / /  \n"
				+"  ======`-.____`-.___\\_____/___.-`____.-'====== \n" 
				+"                     `=---='  \n"
				+"\n"
				+"............................................. \n" 
				+"              佛祖保佑         永无BUG \n"
				+"     佛曰:  \n"
				+"             写字楼里写字间，写字间里程序员；  \n"
				+"             程序人员写程序，又拿程序换酒钱。  \n"
				+"             酒醒只在网上坐，酒醉还来网下眠；  \n"
				+"             酒醉酒醒日复日，网上网下年复年。  \n"
				+"             但愿老死电脑间，不愿鞠躬老板前；  \n"
				+"             奔驰宝马贵者趣，公交自行程序员。  \n"
				+"             别人笑我忒疯癫，我笑自己命太贱；  \n"
				+"             不见满街漂亮妹，哪个归得程序员？\n");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.debug("项目启动......");
		log.debug("\n"
				+"                  _ooOoo_  \n"
				+"                 o8888888o  \n"
				+"                 88' . '88  \n"
				+"                 (| -_- |)  \n"
				+"                  O\\ = /O  \n"
				+"              ____/`---'\\____  \n"
				+"              .   ' \\| |// `. \n" 
				+"              / \\||| : |||// \\ \n" 
				+"            / _||||| -:- |||||- \\ \n" 
				+"              | | \\\\ - /// | |  \n"
				+"            | \\_| ''\\---/'' | |  \n"
				+"             \\ .-\\__ `-` ___/-. /  \n"
				+"           ___`. .' /--.--\\ `. . __  \n"
				+".\"\" '&lt; `.___\\_&lt;|&gt;_/___.' &gt;'\"\".  \n"
				+"       | | : `- \\`.;`\\ _ /`;.`/ - ` : | |  \n"
				+"         \\ \\ `-. \\_ __\\ /__ _/ .-` / /  \n"
				+"  ======`-.____`-.___\\_____/___.-`____.-'====== \n" 
				+"                     `=---='  \n"
				+"\n"
				+"............................................. \n" 
				+"               佛祖保佑          永无BUG \n"
				+"     佛曰:  \n"
				+"             写字楼里写字间，写字间里程序员；  \n"
				+"             程序人员写程序，又拿程序换酒钱。  \n"
				+"             酒醒只在网上坐，酒醉还来网下眠；  \n"
				+"             酒醉酒醒日复日，网上网下年复年。  \n"
				+"             但愿老死电脑间，不愿鞠躬老板前；  \n"
				+"             奔驰宝马贵者趣，公交自行程序员。  \n"
				+"             别人笑我忒疯癫，我笑自己命太贱；  \n"
				+"             不见满街漂亮妹，哪个归得程序员？\n");

	}

}
