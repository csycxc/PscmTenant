package com.banry.pscm.tenant.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.biz.BizAuthority;
import com.banry.pscm.tenant.service.biz.BizAuthorityService;
import com.banry.pscm.tenant.service.biz.DataTableModel;
import com.banry.pscm.tenant.service.biz.DynamicSourceService;
import com.banry.pscm.tenant.service.biz.Tenant;
import com.banry.pscm.tenant.service.biz.TenantRealService;
import com.banry.pscm.tenant.service.biz.TenantService;
import com.banry.pscm.tenant.service.biz.TreeNode;

/**
 * 租户Controller
 * @author chenJuan
 * @date 2018-4-8
 */
@Controller
@RequestMapping("/tenant")
public class TenantController {

	@Autowired
	TenantService tenService;
	@Autowired
	BizAuthorityService bizAuthService;
	@Autowired
	TenantRealService tenRealService;
	@Autowired
	DynamicSourceService sourceService;
	
	private static Logger log = LoggerFactory.getLogger(TenantController.class);
	
	
	/**
	 * 查询全部租户
	 * @return List
	 */
	@RequestMapping("/getTenantList")
	@ResponseBody
	public Object getTenantList(){
		DataTableModel data = new DataTableModel();
		try {
			List<Tenant> tenList = tenService.findTenantList();
			data.setData(tenList);
			return data;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			return null;
		}catch(Exception e){
			log.error("Exception异常：", e);
			return null;
		}
	}
	
	/**
	 * 根据租户编号查询租户
	 * @param code
	 * @return 租户
	 */
	@RequestMapping("/findTenantByCode")
	@ResponseBody
	public Object findTenantByCode(String code){
		Tenant tenant = new Tenant();
		try {
			tenant = tenService.findTenantByCode(code);
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
		}catch(Exception e){
			log.error("Exception异常：", e);
		}
		return tenant;
	}
	
	/**
	 * 根据租户账号查询租户
	 * @param account
	 * @return 租户
	 */
	@RequestMapping("/findTenantByAccount")
	@ResponseBody
	public Object findTenantByAccount(String account){
		Tenant tenant = new Tenant();
		try {
			tenant = tenService.findTenantByAccount(account);
			if (tenant != null) {
				//如果是公司级租户查询子租户列表
				if (tenant.getTenantType().intValue() == 1) {
					List<Tenant> sonList = tenService.findSonTenantList(tenant.getTenantCode());
					tenant.setSonTenant(sonList);
				//如果是项目级租户查询父租户账号
				} else if (tenant.getTenantType().intValue() == 0) {
					Tenant parent = tenService.findTenantByCode(tenant.getParentCode());
					tenant.setParentAccount(parent.getAccount());
				}
			}
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
		}catch(Exception e){
			log.error("Exception异常：", e);
		}
		return tenant;
	}
	
	@RequestMapping("/findTenantTree")
	@ResponseBody
	public Object findTenantTree(){
		ModelAndView mv = new ModelAndView("tenant/tenant");
		try {
			List<Tenant> tenantList = tenService.findTenantList();
			List<TreeNode> treeLst = new ArrayList<TreeNode>();
			for(Tenant ten : tenantList){
				TreeNode tree = new TreeNode();
				tree.setId(ten.getTenantCode());
				tree.setpId(ten.getParentCode());
				tree.setName(ten.getName());
				tree.setTarget("mainframe2");
//				if(ten.getTenantReal()!=null && ten.getDynamicSource()!=null){
//					tree.setUrl("tenant-info?tenantCode="+ten.getTenantCode()+"&realCode="+ten.getTenantReal().getRealCode()+"&sourceCode="+ten.getDynamicSource().getSourceCode());
//				}else if(ten.getTenantReal()!=null){
//					tree.setUrl("tenant-info?tenantCode="+ten.getTenantCode()+"&realCode="+ten.getTenantReal().getRealCode());
//				}else if(ten.getDynamicSource()!=null){
//					tree.setUrl("tenant-info?tenantCode="+ten.getTenantCode()+"&sourceCode="+ten.getDynamicSource().getSourceCode());
//				}else{
//					tree.setUrl("tenant-info?tenantCode="+ten.getTenantCode());
//				}
				tree.setUrl("tenant-info?tenantCode="+ten.getTenantCode());
				tree.setOpen(true);
				treeLst.add(tree);
			}
			String jsonString = JSON.toJSONString(treeLst);
			mv.addObject("treeNode", jsonString);
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
		return mv;
	}
	
	@RequestMapping("/tenant-info")
	@ResponseBody
	public Object findTenantInfo(String tenantCode){
		ModelAndView mv = new ModelAndView("tenant/tenant-info");
		try {
			if (tenantCode != null && !"".equals(tenantCode)) {
				Tenant t = tenService.findTenantByCode(tenantCode);
				mv.addObject("t", t);
			}
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
		return mv;
	}
	
	/**
	 * 增加或修改租户信息
	 * @param tenant
	 * @param flag
	 * @return map
	 */
	@RequestMapping("/addOrUpdateTenant")
	@ResponseBody
	public Map addOrUpdateTenant(Tenant tenant,String flag){
		Map map = new HashMap();
		try {
			
			//新增
			if ("I".equals(flag)) {
				Tenant t = tenService.findTenantByCode(tenant.getTenantCode());
				if(t!=null){
					map.put("result", false);
					map.put("retMsg", "编号已存在");
					return map;
				}
				//判断租户账户是否已经存在
				Tenant tenantByAccount = tenService.findTenantByAccount(tenant.getAccount());
				if(tenantByAccount!=null){
					map.put("result", false);
					map.put("retMsg", "账号已存在");
					return map;
				}
				
				//可设置租户编号
				//tenant.setTenantcode("");
				
				tenService.saveTenant(tenant);
				map.put("result", true);
				map.put("retMsg", "添加成功");
				return map;
			}else if("U".equals(flag) && (tenant.getTenantCode()!=null || tenant.getTenantCode().equals(""))){
				tenService.updateTenantByCodeSelective(tenant);
				map.put("result", true);
				map.put("retMsg", "修改成功");
				return map;
			}else{
				map.put("result", false);
				map.put("retMsg", "数据错误");
				return map;
			}
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}catch(Exception e){
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 删除租户
	 * @param code
	 * @return
	 */
	@RequestMapping("/deleteTenantByCode")
	@ResponseBody
	public Map deleteTenantByCode(String code){
		Map map=new HashMap<>();
		try {
			//根据code查询租户分配功能模块表，如果该租户被授权，解除授权再删除，如果没有被授权，可直接删除租户
			List<BizAuthority> list = bizAuthService.findBizAuthorityListByTenantCode(code);
			if(list.size()>0){
				bizAuthService.deleteJoinBizAuthority(list);
			}
			int i = tenService.deletetenant(code);
			if(i==0){
				map.put("result", false);
				map.put("retMsg", "删除失败");
				return map;
			}else{
				map.put("result", true);
				map.put("retMsg", "删除成功");
				return map;
			}
		}catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}catch(Exception e){
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 跳转新增编辑页面
	 * @param model
	 * @param tenantCode
	 * @return
	 */
	@RequestMapping("/editTenant")
    public String editTenant(Model model, String tenantCode,String parentCode){
		try {
			Tenant tenant = tenService.findTenantByCode(tenantCode);
			model.addAttribute("tenant",tenant);
			model.addAttribute("parentCode",parentCode);
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
        return "tenant/tenant-edit";
    }
	
	@RequestMapping("/tenantAddParent")
    public String tenantAddParent(){
        return "tenant/tenant-add-parent";
    }
}

