<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<spring:url value="/" var="home_url" />
<div class="panel panel-default">
	<div class="panel-heading" style="height: 40px;">
		<div class="pull-left">
			<strong><spring:message code="user.logged.facebook" /></strong>
		</div>
	</div>
	<div class="panel-body">
		<div class="row">
                <div class="col-sm-4">
                    <div class="form-group row">
                        <label class="col-sm-12 control-label sapo">
                            Full name: ${fullname}
                        </label>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-12 control-label sapo">
                            Gmail: ${gmail}
                        </label>
                    </div>
                </div>
                <div class="col-lg-1">
                    <img src="${picture}" alt="..." class="img-circle" height="60" width="60"/>
                </div>
            </div>
	</div>
</div>