package com.xwwx.sewage.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.cj.api.log.Log;
import com.xwwx.sewage.controller.OrderController;

/**
 * Servlet implementation class BackDatabaseTask
 */
/*@WebServlet(loadOnStartup = 1, urlPatterns = { "/servlet/BackDatabaseTask" })*/
public class BackDatabaseTask extends HttpServlet {
	private static Logger logger = Logger.getLogger(BackDatabaseTask.class);
	private static final long serialVersionUID = 1L;
	private BackDatabaseThread dbt;

	@Override
	public void init() throws ServletException {
		super.init();
		// 开启备份线程
		System.out.println("init..................");
		/*
		if (dbt == null) {
			dbt = new BackDatabaseThread();
			dbt.flag = true;
			dbt.start();
		}
		*/
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BackDatabaseTask() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void destroy() {
		dbt.flag = false;
		if (dbt != null && dbt.isInterrupted()) {
			dbt.interrupt();
		}
		super.destroy();
	}

}

class BackDatabaseThread extends Thread {
	public boolean flag = false;
	private String rq = CommTime.getCurrDate1();
	private long interval = 1000 * 60 * 3; // 40分钟
	private int protime = 2;// 执行时间

	@Override
	public void run() {
		while (flag) {
			try {
				// 判断是否凌晨2点
				//if (CommTime.getHour() == protime) {
					// 判断是否执行过
				//	if (!rq.equals(CommTime.getCurrDate1())) {
						// 执行
						Runtime r = Runtime.getRuntime();
						try {
							// 停止数据库服务
							//System.out.println("停止mysql开始" + CommTime.getNewTime());
							//r.exec("net stop mariadb");
							//System.out.println("停止mysql结束" + CommTime.getNewTime());
							// 拷贝文件夹
							copyDirectiory("D:\\mariadb-10.4.6-winx64\\data\\mud","F:\\backdatabase\\"+CommTime.getCurrDate1()+CommTime.getMinute());
							// 开始数据库服务
							//System.out.println("启动mysql开始" + CommTime.getNewTime());
							//r.exec("net start mariadb");
							//System.out.println("启动mysql结束" + CommTime.getNewTime());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						rq = CommTime.getCurrDate1();
				//	}
				//}
				sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 复制文件夹
	public void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	// 复制文件
	public void copyFile(File sourceFile, File targetFile) throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}
}
