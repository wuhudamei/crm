package com.rocoinfo.entity.employee;


import com.rocoinfo.entity.IdEntity;

/**
 * <dl>
 * <dd>Description: 数据权限实体类</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/9</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
public class EmployeeDataPermission extends IdEntity {

    //权限名
    private String name;
    //权限编号
    private String code;
    //所属模块
    private String module;
    //所属模块编号
    private String moduleCode;
    //是否选中
    private Boolean checked;
    //状态
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}
