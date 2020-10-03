package com.xwwx.sewage.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.util.StringUtil;
import com.xwwx.sewage.bean.TCommUser;
import com.xwwx.sewage.bean.TCsPdaMacAddr;
import com.xwwx.sewage.bean.TCsPdaUser;
import com.xwwx.sewage.cache.Cache;
import com.xwwx.sewage.common.Constant;
import com.xwwx.sewage.common.Md5;
import com.xwwx.sewage.common.PageList;
import com.xwwx.sewage.common.Pages;
import com.xwwx.sewage.common.StringUtils;
import com.xwwx.sewage.common.excel.ExcelRead;
import com.xwwx.sewage.common.excel.ExcelUtil;
import com.xwwx.sewage.service.PdaService;

/**
 * pda业务处理
 * 
 * @author 可乐罐
 *
 */
@Controller
@RequestMapping("/pda")
public class PdaController {

	@Autowired
	private PdaService pdaService;
	@Autowired
	private Cache cache;

	@RequestMapping("/pdamacaddrlist")
	private String pdaMacAddrList(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		String cutelno = WebUtils.findParameterValue(request, "cutelno");
		Pages pages = new Pages();
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		PageList pageList = pdaService.getPdaMacAddrList("id", Constant.LIST_ASC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);
		map.put("cutelno", cutelno);
		return "pda/pdamacaddrlist";
	}

	@RequestMapping("/addmacaddrpage")
	private String AddMacAddrPage(HttpServletRequest request, ModelMap map) {

		return "pda/addmacaddr";
	}

	@RequestMapping(value = "/addmacaddr", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String AddMacAddr(HttpServletRequest request) {
		String macaddr = WebUtils.findParameterValue(request, "macaddr");
		String status = WebUtils.findParameterValue(request, "status");
		TCsPdaMacAddr tCsPdaMacAddr = new TCsPdaMacAddr();
		tCsPdaMacAddr.setMacaddr(macaddr);
		tCsPdaMacAddr.setStatus(Integer.valueOf(status));
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			pdaService.insertPdaMacAddr(tCsPdaMacAddr);
			cache.reload();
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}

	@RequestMapping("/editmacaddrpage")
	private String EditMacAddrPage(HttpServletRequest request, ModelMap map) {
		String id = WebUtils.findParameterValue(request, "id");
		String macaddr = WebUtils.findParameterValue(request, "macaddr");
		String status = WebUtils.findParameterValue(request, "status");

		map.put("id", id);
		map.put("macaddr", macaddr);
		map.put("status", status);
		return "pda/editmacaddr";
	}

	@RequestMapping(value = "/deletemacaddr", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String DeleteMacAddrPage(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			TCsPdaMacAddr tCsPdaMacAddr = new TCsPdaMacAddr();
			tCsPdaMacAddr.setId(Integer.valueOf(id));
			pdaService.deletePdaMacAddr(tCsPdaMacAddr);
			cache.reload();
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}

	@RequestMapping(value = "/editmacaddr", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String EditMacAddr(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		String macaddr = WebUtils.findParameterValue(request, "macaddr");
		String status = WebUtils.findParameterValue(request, "status");
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			TCsPdaMacAddr tCsPdaMacAddr = new TCsPdaMacAddr();
			tCsPdaMacAddr.setId(Integer.valueOf(id));
			tCsPdaMacAddr.setMacaddr(macaddr);
			tCsPdaMacAddr.setStatus(Integer.valueOf(status));
			pdaService.updatePdaMacAddr(tCsPdaMacAddr);
			cache.reload();
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}

	@RequestMapping("/pdaUserList")
	private String pdaUserList(HttpServletRequest request, ModelMap map) {
		String pageno = WebUtils.findParameterValue(request, "pageno");
		String cutelno = WebUtils.findParameterValue(request, "cutelno");
		Pages pages = new Pages();
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		pages.setPerPageNum(user.getPagenum());
		if (null == pageno || pageno.equals("0")) {
			pageno = "1";
		}
		pages.setPage(Integer.parseInt(pageno));
		Map paramMap = new HashMap();
		PageList pageList = pdaService.getPdaUserList("id", Constant.LIST_ASC, pages, paramMap);
		String pagination = pages.getUrlstr();
		map.put("pageList", pageList);
		map.put("pagination", pagination);
		map.put("cutelno", cutelno);
		return "pda/pdauserlist";
	}

	@RequestMapping("/addpdauserpage")
	private String AddPdaUserPage(HttpServletRequest request, ModelMap map) {

		return "pda/addpdauser";
	}

	@RequestMapping(value = "/addpdauser", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String AddPdaUser(HttpServletRequest request) {
		String pdauserid = WebUtils.findParameterValue(request, "pdauserid");
		String pdausername = WebUtils.findParameterValue(request, "pdausername");
		String pdatype = WebUtils.findParameterValue(request, "pdatype");
		String pdadept = WebUtils.findParameterValue(request, "pdadept");
		TCsPdaUser tCsPdaUser = new TCsPdaUser();
		tCsPdaUser.setPdauserid(pdauserid);
		tCsPdaUser.setPdausername(pdausername);
		tCsPdaUser.setPdatype(Integer.parseInt(pdatype));
		tCsPdaUser.setPdadept(pdadept);
		tCsPdaUser.setPdapwd(Md5.encrypt(Constant.DEFAULT_USER_PWD));
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			pdaService.insertPdaUser(tCsPdaUser);
			cache.reload();
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}

	@RequestMapping("/editpdauserpage")
	private String EditPdaUserPage(HttpServletRequest request, ModelMap map) {
		String id = WebUtils.findParameterValue(request, "id");
		TCsPdaUser tCsPdaUser = pdaService.getPdaUserById(Integer.parseInt(id));
		map.put("id", id);
		map.put("pdauser", tCsPdaUser);
		return "pda/editpdauser";
	}

	@RequestMapping("/tid")
	private String Tid(HttpServletRequest request, ModelMap map) {

		int tidcount = pdaService.getTidCount();
		map.put("tidcount", tidcount);
		return "pda/tid";
	}

	// 上传tid
	@RequestMapping(value = "/upload", produces = "application/json; charset=utf-8")
	public void upload(@RequestParam(value = "filename1") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取文件名
		String filename = file.getOriginalFilename();

		if (file == null || StringUtils.isEmpty(filename) || file.getSize() == 0) {
			response.getWriter().print("-1");
		}
		try {
			String postfix = ExcelUtil.getPostfix(file.getOriginalFilename());
			if (!StringUtil.isEmpty(postfix)) {
				if ("txt".equals(postfix.toLowerCase())) {
					// 文本文件
					InputStream input = file.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(input));
					String tid = "";
					while ((tid = br.readLine()) != null) {
						try {
							if(!StringUtils.isEmpty(tid)) {
								pdaService.insertTid(tid);
							}
						} catch (Exception e) {

						}
					}
					br.close();
				} else {
					// 读取Excel数据到List中
					List<ArrayList<String>> list = new ExcelRead().readExcel(file);

					for (ArrayList<String> arr : list) {
						try {
							pdaService.insertTid(arr.get(0));
						} catch (Exception e) {

						}
					}
				}
			} else {
				response.getWriter().print("-1");
			}

		} catch (Exception e) {
			e.getMessage();
			response.getWriter().print("-1");
		}
		response.getWriter().print("0");
	}

	@RequestMapping(value = "/editpdauser", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String EditPdaUser(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		String pdauserid = WebUtils.findParameterValue(request, "pdauserid");
		String pdausername = WebUtils.findParameterValue(request, "pdausername");
		String pdatype = WebUtils.findParameterValue(request, "pdatype");
		String pdadept = WebUtils.findParameterValue(request, "pdadept");
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			TCsPdaUser tCsPdaUser = new TCsPdaUser();
			tCsPdaUser.setId(Integer.valueOf(id));
			tCsPdaUser.setPdauserid(pdauserid);
			tCsPdaUser.setPdausername(pdausername);
			tCsPdaUser.setPdatype(Integer.parseInt(pdatype));
			tCsPdaUser.setPdadept(pdadept);
			pdaService.updatePdaUser(tCsPdaUser);
			System.out.println(tCsPdaUser.toString());
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}

	@RequestMapping(value = "/deletepdauser", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String DeletePdaUser(HttpServletRequest request) {
		String id = WebUtils.findParameterValue(request, "id");
		String rstr = "{\"code\":\"201\",\"message\":\"失败!\"}";
		try {
			TCsPdaUser tCsPdaUser = new TCsPdaUser();
			tCsPdaUser.setId(Integer.valueOf(id));
			pdaService.deletePdaUser(tCsPdaUser);
			rstr = "{\"code\":\"200\",\"message\":\"成功!\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstr;
	}

	@RequestMapping("/download")
	private String download(HttpServletRequest request, ModelMap map) {
		return "pda/download";
	}

	@RequestMapping("/pda_fahuo_d")
	private String pda_fahuo_d(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String name = "pda_fahuo_d.apk";
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			// 获取下载文件所在路径
			// String path = request.getServletContext().getRealPath("/WEB-INF/data/" +
			// name);
			String path = "d:/pda/pda_fahuo_d.apk";
			// 文件
			File file = new File(path);
			// 判断文件是否存在
			if (file.exists()) {
				// 且仅当此对象抽象路径名表示的文件或目录存在时，返回true
				// response.setContentType("application/pdf");
				// 控制下载文件的名字
				response.addHeader("Content-Disposition", "attachment;filename=" + name);
				byte buf[] = new byte[1024];
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				os = response.getOutputStream();
				int i = bis.read(buf);
				while (i != -1) {
					os.write(buf, 0, i);
					i = bis.read(buf);
				}
			} else {
				System.out.println("您下载的资源已经不存在了");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (bis != null)
					bis.close();
				if (fis != null)
					fis.close();
			} catch (Exception e) {

			}
		}
		return null;
	}

	@RequestMapping("/pda_shouhuo_d")
	private String pda_shouhuo_d(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String name = "pda_shouhuo_d.apk";
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			// 获取下载文件所在路径
			// String path = request.getServletContext().getRealPath("/WEB-INF/data/" +
			// name);
			String path = "d:/pda/pda_shouhuo_d.apk";
			// 文件
			File file = new File(path);
			// 判断文件是否存在
			if (file.exists()) {
				// 且仅当此对象抽象路径名表示的文件或目录存在时，返回true
				// response.setContentType("application/pdf");
				// 控制下载文件的名字
				response.addHeader("Content-Disposition", "attachment;filename=" + name);
				byte buf[] = new byte[1024];
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				os = response.getOutputStream();
				int i = bis.read(buf);
				while (i != -1) {
					os.write(buf, 0, i);
					i = bis.read(buf);
				}
			} else {
				System.out.println("您下载的资源已经不存在了");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (bis != null)
					bis.close();
				if (fis != null)
					fis.close();
			} catch (Exception e) {

			}
		}
		return null;
	}

	@RequestMapping("/pda_fahuo_x")
	private String pda_fahuo_x(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String name = "pda_fahuo_x.apk";
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			// 获取下载文件所在路径
			// String path = request.getServletContext().getRealPath("/WEB-INF/data/" +
			// name);
			String path = "d:/pda/pda_fahuo_x.apk";
			// 文件
			File file = new File(path);
			// 判断文件是否存在
			if (file.exists()) {
				// 且仅当此对象抽象路径名表示的文件或目录存在时，返回true
				// response.setContentType("application/pdf");
				// 控制下载文件的名字
				response.addHeader("Content-Disposition", "attachment;filename=" + name);
				byte buf[] = new byte[1024];
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				os = response.getOutputStream();
				int i = bis.read(buf);
				while (i != -1) {
					os.write(buf, 0, i);
					i = bis.read(buf);
				}
			} else {
				System.out.println("您下载的资源已经不存在了");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (bis != null)
					bis.close();
				if (fis != null)
					fis.close();
			} catch (Exception e) {

			}
		}
		return null;
	}

	@RequestMapping("/pda_shouhuo_x")
	private String pda_shouhuo_x(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String name = "pda_shouhuo_x.apk";
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			// 获取下载文件所在路径
			// String path = request.getServletContext().getRealPath("/WEB-INF/data/" +
			// name);
			String path = "d:/pda/pda_shouhuo_x.apk";
			// 文件
			File file = new File(path);
			// 判断文件是否存在
			if (file.exists()) {
				// 且仅当此对象抽象路径名表示的文件或目录存在时，返回true
				// response.setContentType("application/pdf");
				// 控制下载文件的名字
				response.addHeader("Content-Disposition", "attachment;filename=" + name);
				byte buf[] = new byte[1024];
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				os = response.getOutputStream();
				int i = bis.read(buf);
				while (i != -1) {
					os.write(buf, 0, i);
					i = bis.read(buf);
				}
			} else {
				System.out.println("您下载的资源已经不存在了");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (bis != null)
					bis.close();
				if (fis != null)
					fis.close();
			} catch (Exception e) {

			}
		}
		return null;
	}
}
