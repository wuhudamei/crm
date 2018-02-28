<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!--左侧导航开始-->
<nav id="nav" class="navbar-default navbar-static-side"
     role="navigation">
    <div class="nav-close">
        <i class="fa fa-times-circle"></i>
    </div>
    <div id="navUser" class="sidebar-collapse">
        <div class="nav-header">
            <div>
                <div class="dropdown profile-element">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
                            class="clear"> <span class="block m-t-xs"> <strong
                            class="font-bold"><shiro:principal property="name"/></strong>
						</span> 
<%-- 						<span class="text-muted text-xs block"><shiro:principal --%>
<%-- 									property="position" /> <b class="caret"></b> </span> --%>
					</span>
                    </a>
                </div>
            </div>
        </div>
        <!-- 左侧菜单 start-->
        <ul class="nav metismenu" id="sideMenu">
            <%-- <shiro:hasPermission name="index:list">
                <li id="dashboardMenu"><a href="/"> <i class="fa fa-home"></i>
                        <span class="nav-label">主页</span>
                </a></li>
            </shiro:hasPermission> --%>

            <shiro:hasPermission name="customer:menu">
                <li id="customerMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">客户管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="customer:info">
                            <li id="customer">
                                <a href="/api/customer/customerList"> <i class="fa fa-edit"></i>
                                    <span class="nav-label">客户库</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="customer:addTaskForCome">
                            <li id="addTaskMDQT">
                                <a href="/api/customer/addTask?dataSource=MDQT"> <i class="fa fa-edit"></i>
                                    <span class="nav-label">新增任务(前台)</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="customer:addTaskForMarket">
                            <li id="addTaskSCB">
                                <a href="/api/customer/addTask?dataSource=SCB"> <i class="fa fa-edit"></i>
                                    <span class="nav-label">新增任务(市场部)</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="customer:addTaskForHWZX">
                            <li id="addTaskHWZX">
                                <a href="/api/customer/addTask?dataSource=HWZX"> <i class="fa fa-edit"></i>
                                    <span class="nav-label">新增任务(话务中心)</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="customer:addTaskForXMT">
                            <li id="addTaskXMT">
                                <a href="/api/customer/addTask?dataSource=XMT"> <i class="fa fa-edit"></i>
                                    <span class="nav-label">新增任务(新媒体)</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

						<shiro:hasPermission name="customer:addTaskForXMFD">
							<li id="addTaskXMFD">
								<a href="/api/customer/addTask?dataSource=XMFD">  <i class="fa fa-edit"></i>
									<span class="nav-label">新增任务(小美返单)</span>
								</a>
							</li>
						</shiro:hasPermission>

							<!-- 模拟新增任务 -->
<!-- 							<li id="addTaskSCBTest"> -->
<!-- 								<a href="/api/customer/addTaskTest">  <i class="fa fa-edit"></i>  -->
<!-- 									<span class="nav-label">模拟新增任务</span> -->
<!-- 								</a> -->
<!-- 							</li> -->
						
						<shiro:hasPermission name="customer:unDistributeTask">
							<li id="unDistributeTask"><a href="/admin/task/taskList?distributeStatus=N"> <i
									class="fa fa-edit"></i> <span class="nav-label">未派发任务</span>
							</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="customer:distributeTask">
							<li id="distributedTask"><a href="/admin/task/taskList?distributeStatus=Y"> <i
									class="fa fa-edit"></i> <span class="nav-label">已派发任务</span>
							</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="customer:allTaskList">
							<li id="allTaskList"><a href="/admin/task/allTaskList"> <i
									class="fa fa-edit"></i> <span class="nav-label">任务列表</span>
							</a></li>
						</shiro:hasPermission>
                        <shiro:hasPermission name="customer:taskInQueueList">
                            <li id="taskInQueueList"><a href="/admin/task/taskInQueueList?source=taskInQueueList"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">自动派发线索列表</span>
                            </a></li>
                        </shiro:hasPermission>
					</ul>
				<li />
			</shiro:hasPermission>

            <shiro:hasPermission name="employee:menu">
                <li id="regulationMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">用户管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="employee:list">
                            <li id="csList"><a href="/admin/employee/list?source=list"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">用户管理</span>
                            </a></li>
                        </shiro:hasPermission>
                            <%-- 						<shiro:hasPermission name="employee:add"> --%>
                        <!--  							<li id="csAdd"><a href="/admin/employee/add"> --%> -->
                        <!-- 									<i class="fa fa-edit"></i> <span class="nav-label">新增客服</span> -->
                        <!-- 							</a></li> -->
                            <%-- 						</shiro:hasPermission> --%>
                        <shiro:hasPermission name="employee:commonlist">
                            <li id="commonList"><a href="/admin/employee/list?source=commonList"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">用户列表</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="employee:employeeInQueueList">
                            <li id="employeeInQueueList"><a href="/admin/employee/employeeInQueueList?source=employeeInQueueList"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">自动接单员工列表</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="report:center">
                <li id="reportCenter"><a href="#"> <i
                        class="fa fa-edit"></i> <span class="nav-label">报表中心</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">

                        <shiro:hasPermission name="report:source">
                            <li id="reportSource"><a href="/admin/reportCenter/sourceList"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">来源报表</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="report:channel">
                            <li id="reportChannel"><a href="/admin/reportCenter/channelList"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">渠道报表</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="report:personal">
                            <li id="reportPersonal"><a href="/admin/reportCenter/personalList"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">个人报表</span>
                            </a></li>
                        </shiro:hasPermission>

                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="source:menu">
                <li id="dataSourceMenu"><a href="#"> <i
                        class="fa fa-edit"></i> <span class="nav-label">来源管理</span> <span
                        class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="source:add">
                            <li id="sourceAdd"><a href="/admin/dataSource/edit"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">新增来源</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="source:list">
                            <li id="sourceList"><a href="/admin/dataSource/list"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">来源列表</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="source:monitor">
                            <li id="sourceMonitor"><a
                                    href="/admin/abnormalData/list"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">来源异常监控</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                <li/>
            </shiro:hasPermission>

            <shiro:hasPermission name="base:config">
                <li id="baseConfig">
                    <a href="#"> <i class="fa fa-edit"></i>
                        <span class="nav-label">基础配置</span> <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="base:distributeRule">
                            <li id="distributeRule">
                                <a href="/admin/distributeRule"> <i class="fa fa-edit"></i>
                                    <span class="nav-label">派发规则</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="base:remindRule">
                            <li id="remindRule">
                                <a href="/admin/remindRule"> <i class="fa fa-edit"></i>
                                    <span class="nav-label">提醒规则</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                            <%-- <shiro:hasPermission name="base:roleList">
                                <li id="roleList">
                                    <a href="/admin/roles"> <i class="fa fa-edit"></i>
                                        <span class="nav-label">角色列表</span>
                                    </a>
                                </li>
                            </shiro:hasPermission> --%>

                        <shiro:hasPermission name="base:dictList">
                            <li id="dictList">
                                <a href="/admin/dicList"> <i class="fa fa-edit"></i>
                                    <span class="nav-label">数据字典</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="taskRules:config">
                <li id="taskRules">
                    <a href="#"> <i class="fa fa-edit"></i>
                        <span class="nav-label">规则配置</span> <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="taskRules:distributeRule">
                            <li id="autoDistributed">
                                <a href="/admin/taskrules/taskRulesList"> <i class="fa fa-edit"></i>
                                    <span class="nav-label">自动派单池</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>
            <li id="updatePassword"><a href="/api/modify/password"> <i
                    class="fa fa-edit"></i> <span class="nav-label">修改密码</span>
            </a>
            </li>

        </ul>
        <!-- 左侧菜单 end-->
    </div>
</nav>
<!--左侧导航结束-->