<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<spring:url value="/" var="home_url" />
<c:if test="${pageContext['request'].userPrincipal != null}">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="root-menu navbar-brand" href="${home_url}home" title="Home" style="font-size: 14px;">
            <span class="fa fa-home fa-fw"></span> <spring:message code="menu.home"/>
        </a>
    </div>
</c:if>
<ul class="nav navbar-top-links navbar-right">
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
            <i class="fa fa-tasks fa-fw"></i> <spring:message code="menu.language"/> <i class="fa fa-caret-down"></i>
        </a>
        <ul class="dropdown-menu dropdown-messages">
            <li><a href="${home_url}?lang=en"><img src="${home_url}share/images/England.png" width="17" height="10"/> United Kingdom</a></li>
            <li><a href="${home_url}?lang=jp"><img src="${home_url}share/images/Japan.png" width="17" height="10"/> Japan</a></li>
            <li><a href="${home_url}?lang=vn"><img src="${home_url}share/images/Vietnam.png" width="17" height="10"/> Vietnam</a></li>
        </ul>
    </li>
    <c:if test="${pageContext['request'].userPrincipal != null}">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <sec:authentication property="principal.username"/> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li>
                    <a href="${home_url}update-user"><i class="fa fa-user fa-fw"></i> <spring:message code="user.update"/></a>
                </li>
                <li class="divider"></li>
                <c:forEach items="${roles}" var="role">
                    <c:if test="${role == 'ADMIN' || role == 'MANAGER'}">
                    <li>
                        <a href="${home_url}user-manager"><i class="fa fa-gear fa-fw"></i> <spring:message code="user.admin.area"/></a>
                    </li>
                    <li class="divider"></li>
                    </c:if>
                </c:forEach>
                <li>
                    <a href="${home_url}logout"><i class="fa fa-sign-out fa-fw"></i> <spring:message code="menu.logout"/></a>
                </li>
            </ul>
        </li>
    </c:if>
</ul>
<c:if test="${pageContext['request'].userPrincipal != null}">
    <div class="navbar-default sidebar" role="navigation" style="border-radius: 0px;">
        <div class="sidebar-nav navbar-collapse" style="max-height: 1000px;">
            <ul class="nav" id="side-menu">
                <li class="sidebar-search">
                    <div class="input-group custom-search-form">
                        <input type="text" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                            <button class="btn btn-default" style="padding: 9px 12px;" type="button">
                                <i class="fa fa-search"></i>
                            </button>
                        </span>
                    </div>
                    <!-- /input-group -->
                </li>
                <li>
                    <a class="active" href="${home_url}"><i class="fa fa-home fa-fw"></i> <spring:message code="menu.home"/></a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Charts<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="flot.html">Flot Charts</a>
                        </li>
                        <li>
                            <a href="morris.html">Morris.js Charts</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li>
                    <a href="tables.html"><i class="fa fa-table fa-fw"></i> Tables</a>
                </li>
                <li>
                    <a href="forms.html"><i class="fa fa-edit fa-fw"></i> Forms</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-wrench fa-fw"></i> UI Elements<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="panels-wells.html">Panels and Wells</a>
                        </li>
                        <li>
                            <a href="buttons.html">Buttons</a>
                        </li>
                        <li>
                            <a href="notifications.html">Notifications</a>
                        </li>
                        <li>
                            <a href="typography.html">Typography</a>
                        </li>
                        <li>
                            <a href="grid.html">Grid</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li>
                    <a href="#"><i class="fa fa-sitemap fa-fw"></i> Multi-Level Dropdown<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="#">Second Level Item</a>
                        </li>
                        <li>
                            <a href="#">Second Level Item</a>
                        </li>
                        <li>
                            <a href="#">Third Level <span class="fa arrow"></span></a>
                            <ul class="nav nav-third-level">
                                <li>
                                    <a href="#">Third Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Third Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Third Level Item</a>
                                </li>
                                <li>
                                    <a href="#">Third Level Item</a>
                                </li>
                            </ul>
                            <!-- /.nav-third-level -->
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li>
                    <a href="#"><i class="fa fa-files-o fa-fw"></i> Sample Pages<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="blank.html">Blank Page</a>
                        </li>
                        <li>
                            <a href="login.html">Login Page</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</c:if>