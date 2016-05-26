<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<spring:url value="/" var="home_url" />
<div class="panel panel-default">
    <div class="panel-heading" style="height: 40px;">
        <div class="pull-left">
            <strong><spring:message code="button.user.update"/></strong>
        </div>
    </div>
    <div class="panel-body">
        <div class="row form-user-management">
            <div class="col-sm-8">
                <form name="form" id="form-update-user" onsubmit="return false;"  class="form-horizontal" method="POST" action="${home_url}update-user">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group row">
                        <label class="col-sm-3 control-label"> <spring:message code="user.currentpassword"/></label>
                        <div class="col-sm-9">
                            <input type="password" name="currentPassword" class="form-control" placeholder="Please input your current password" required autofocus />
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-3 control-label"> <spring:message code="user.newpassword" /></label>
                        <div class="col-sm-9">
                            <input type="password" name="password" class="form-control input-sm" placeholder="Please input new password" required/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-3 control-label"> <spring:message code="user.passwordRepeated" /></label>
                        <div class="col-sm-9">
                            <input type="password" name="passwordRepeated" class="form-control input-sm" placeholder="Please input confirm new password" required/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-12">
                            <div class="pull-right">
                                <button class="update-user btn btn-primary btn-sm"><spring:message code="button.update"/></button>
                                <button type="reset" class="btn btn-default btn-sm"><spring:message code="button.clear"/></button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    /* Update user */
    $(".update-user").click(function () {
        var postData = $("#form-update-user").serializeArray();
        var url = $("#form-update-user").attr("action");
        $.ajax({
            url: url,
            cache: false,
            type: "POST",
            data: postData,
            success: function (result) {
                bootbox.alert(result.message);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                bootbox.alert(errorThrow);
            }
        });
    });
</script>

