<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>客户列表</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content" v-cloak>
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" @submit.prevent="query">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="sr-only" for="keyword"></label>
                            <input v-model="form.keyword" id="keyword"
                                    name="keyword" type="text"
                                    placeholder="    姓名 | 手机号 | 客户编号" class="form-control"/>
                        </div>
                    </div>
                    
                    <!-- 门店信息 -->
                    <div class="col-md-2">
                        <div class="form-group">
				        	<select v-model="form.store" placeholder="门店" class="form-control">
							  <option value="" v-if="stores.length == 0" >--选择门店--</option>
							  <option v-for="store in stores" v-bind:value="store.code">{{store.name}}</option>
							</select>
                        </div>
                    </div>
                    
                    <!-- 数据来源 -->
                    <div class="col-md-2">
                        <div class="form-group">
				        	<select v-model="form.dataSource" placeholder="来源" class="form-control">
							  <option value="" v-if="dataSources.length == 0" >--选择来源--</option>
							  <option v-for="dataSource in dataSources" :value="dataSource.code">{{dataSource.name}}</option>
							</select>
                        </div>
                    </div>
                    
                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                    alt="搜索" title="搜索">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <!-- <div class="columns columns-right btn-group pull-right"></div> -->
            <table v-el:data-table id="dataTable" width="100%"
                   class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->

<script src="${ctx}/static/js/containers/customer/customerList.js"></script>
