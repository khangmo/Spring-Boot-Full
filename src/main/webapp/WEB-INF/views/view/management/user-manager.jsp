<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<spring:url value="/" var="home_url" />
<div class="panel with-nav-tabs panel-default">
    <div class="panel-heading" style="padding-top: 5px; padding-bottom: 0px; padding-left: 5px; border-bottom: 0px solid;">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#list-of-users" data-toggle="tab"><spring:message code="user.management"/></a></li>
            <li><a href="#create-new-account" data-toggle="tab"><spring:message code="user.createnew"/></a></li>
        </ul>
    </div>
    <div class="panel-body">
        <div class="tab-content">
            <!-- List account -->
            <div class="tab-pane fade in active" id="list-of-users">
                <form name="form" id="form-users" method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="list-group">
                        <div class="list-group-item active" style="padding: 7px 15px;">
                            <div style="color: #FFFFFF" class="row">
                                <div class="col-sm-8"><spring:message code="user.username"/></div>
                                <div class="col-sm-1"><spring:message code="user.status"/></div>
                                <div class="col-sm-3"><spring:message code="user.role"/></div>
                            </div>
                        </div>
                        <!-- List Users Start -->
                        <c:forEach items="${users}" var="user">
                            <div class="list-group-item" style="padding: 7px 15px;">
                                <div class="row">
                                    <div class="col-sm-7">
                                        <div class="row">
                                            <div class="col-sm-2">
                                                <input name="users" type="checkbox" value="${user.username}">
                                            </div>
                                            <div class="col-sm-10">${user.username}</div>
                                        </div>
                                    </div>
                                    <div class="col-sm-2">
                                        <c:choose>
                                            <c:when test="${user.enabled}">
                                                <spring:message code="user.active"/>
                                            </c:when>
                                            <c:otherwise>
                                                <spring:message code="user.deleted"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="col-sm-3">
                                        <c:forEach items="${user.roles}" var="role">
                                            <a>${role}</a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <!-- List Users End -->
                        <!-- Pagination End here -->
                        <div class="pagination-holder clearfix pagination-boundary">
                            <div id="light-pagination"><!-- --></div>
                        </div>
                        <script type="text/javascript">
                            $(function () {
                                var pages = "${pages}";
                                var currentPage = "${currentPage}";
                                var action = "${home_url}user-manager/p"
                                if (pages > 1) {
                                	$(".pagination-holder").show();
                                	$('#light-pagination').show();
                                    $('#light-pagination').pagination({action: action, pages: pages, cssStyle: "light-theme", displayedPages: 3, edges: 3, currentPage: currentPage});
                                } else {
                                	$(".pagination-holder").hide();
                                	$('#light-pagination').hide();
                                }
                            });
                        </script>
                        <!-- Pagination End Here -->
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="pull-right">
                                <button data-toggle="modal" data-target="#rollbackAccounts" class="btn btn-success btn-sm">
                                    <span class="glyphicon glyphicon-repeat"></span> <spring:message code="button.user.rollback"/>
                                </button>
                                <button data-toggle="modal" data-target="#delelteAccounts" style="margin-right: 2px;" class="btn btn-danger btn-sm">
                                    <span class="glyphicon glyphicon-trash"></span> <spring:message code="button.user.delete"/>
                                </button>
                            </div>
                        </div>
                    </div>	
                </form>
            </div>
            <!-- Create New Account -->
            <div class="tab-pane fade" id="create-new-account">
               	<div class="row form-user-management">
                    <div class="col-sm-10">
                        <form name="form" class="form-horizontal" method="POST" action="${home_url}create-user">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group row">
                                <label class="col-sm-4 control-label"> <spring:message code="user" /></label>
                                <div class="col-sm-8">
                                    <input type="text" name="username" class="form-control input-sm" placeholder="Please input new user" required autofocus />
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-4 control-label"> <spring:message code="user.password" /></label>
                                <div class="col-sm-8">
                                    <input type="password" name="password" class="form-control input-sm" placeholder="Please input your password" required/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-4 control-label"> <spring:message code="user.passwordRepeated" /></label>
                                <div class="col-sm-8">
                                    <input type="password" name="passwordRepeated" class="form-control input-sm" placeholder="Please input confirm password" required/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-sm-4 control-label"> <spring:message code="user.role" /></label>
                                <div class="col-sm-8 selectContainer">
                                    <select name="role" class="form-control" title="Choose account's role" required>
                                        <option>ADMIN</option>
                                        <option>USER</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-sm-12">
                                    <div class="pull-right">
                                        <button class="submit btn btn-primary btn-sm"><spring:message code="button.create" /></button>
                                        <button type="reset" class="btn btn-default btn-sm"><spring:message code="button.clear" /></button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Dialog Confirm Delete -->
    <div id="delelteAccounts" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                    <h4 class="modal-title" id="myModalLabel">Message information</h4>
                </div>
                <div class="modal-body">
                    <h4><spring:message code="message.confirm.delete"/></h4>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default btn-sm" data-dismiss="modal"><spring:message code="button.close"/></button>
                    <c:out value=" "/>
                    <button id="delete-username-selected" class="btn btn-danger btn-sm">
                        <span class="glyphicon glyphicon-trash"></span> <spring:message code="button.close"/> <spring:message code="button.user.delete"/>"
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Dialog Confirm Rollback Account -->
    <div id="rollbackAccounts" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                    <h4 class="modal-title" id="myModalLabel">Message information</h4>
                </div>
                <div class="modal-body">
                    <h4>Do you want rollback these accounts?</h4>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default btn-sm" data-dismiss="modal">Close</button>
                    <c:out value=" "/>
                    <button id="rollback-username-selected" class="btn btn-success btn-sm">
                        <span class="glyphicon glyphicon-repeat"></span> Rollback accounts
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        /* Delete users are selected */
        $("#delete-username-selected").click(function () {
            if (!checkChecked()) {
                bootbox.alert("You didn't selected any user!");
                return;
            }
            var form = $("#form-users");
            form.attr("action", "${home_url}delete-users");
            form.submit();
        });

        /* Rollback users are selected */
        $("#rollback-username-selected").click(function () {
            if (!checkChecked()) {
                bootbox.alert("You didn't selected any user!");
                return;
            }
            var form = $("#form-users");
            form.attr("action", "${home_url}rollback-users");
            form.submit();
        });

        var checkChecked = function () {
            if ($("input:checkbox:checked").length > 0) {
                return true;
            } else {
                return false;
            }
        };
    </script>
</div>

