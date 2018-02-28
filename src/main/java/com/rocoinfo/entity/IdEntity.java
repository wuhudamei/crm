package com.rocoinfo.entity;

import java.io.Serializable;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/8 下午5:10</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class IdEntity implements Serializable {

    private static final long serialVersionUID = -2716222356509348153L;
    protected Long id;
    public static final String ID_FIELD_NAME = "id";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
