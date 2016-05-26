<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<spring:url value="/" var="home_url" />
<style type="text/css">
	#page-wrapper {
		margin: 0px;
	}
</style>
<div style="margin-left: auto; margin-right: auto; margin-top: 10%; margin-left: 10%; margin-right: 10%">
	<div class="panel panel-default">
	    <div class="panel-heading" style="height: 40px;">
	        <div class="pull-left">
	            <strong><spring:message code="form.login"/></strong>
	        </div>
	    </div>
	    <div class="panel-body">
	    
	    	<c:if test="${pageContext['request'].userPrincipal != null}">
	            <spring:message code="user.logged" />
	        </c:if>
	        <c:if test="${pageContext['request'].userPrincipal == null}">
	            <spring:url value="/resources/j_spring_security_check" var="form_url" />
		        <form name="form" class="form-horizontal" method="POST" action="${home_url}login">
		            <div class="form-group row">
		                <label class="col-sm-3 control-label"> <spring:message code="user.username" />
		                </label>
		                <div class="col-sm-6">
		                    <input type="text" name="username" class="form-control" placeholder="Input Your Username"/>
		                </div>
		            </div>
		            <div class="form-group row">
		                <label class="col-sm-3 control-label"> <spring:message code="user.password" />
		                </label>
		                <div class="col-sm-6">
		                    <input type="password" name="password" class="form-control" placeholder="Input Your Password"/>
		                </div>
		            </div>
		
		            <div class="form-group row">
		                <div class="col-sm-9">
		                    <div class="pull-right">
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		                    	<button class="submit btn btn-primary btn-sm">
		                    		<i class="fa fa-google fa-lg"></i>| <spring:message code="button.login.gmail" />
		                    	</button>
		                    	<a href="${requestUrl}?client_id=${clientId}&amp;redirect_uri=${rootUrl}oauth/facebook" class="btn btn-primary btn-sm">
		                    		<i class="fa fa-facebook-square fa-lg"></i> <spring:message code="button.login.facebook" />
		                    	</a>
		                        <button class="submit btn btn-success btn-sm"><spring:message code="button.login" /></button>
		                        <button type="reset" class="btn btn-default btn-sm"><spring:message code="button.clear" /></button>
		                    </div>
		                </div>
		            </div>
		        </form>
	        </c:if>
	    </div>
	</div>
	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
	    <div class="alert alert-danger" style="margin-top: 10px">
	        <button type="button" class="close" data-dismiss="alert"><!----></button>
	        <spring:message code="user.login.fail"/>
	    </div>
	</c:if>
</div>