<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <spring:url value="/share" var="js_path"/>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        
        <title><tiles:getAsString name="title" /></title>
        <script src="${js_path}/jquery/jquery-2.2.3.js" type="text/javascript"></script>
        <script src="${js_path}/bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <script src="${js_path}/bootstrap/js/bootbox.js" type="text/javascript"></script>
        <script src="${js_path}/bootstrap/js/plugin/metisMenu.js"></script>
        <script src="${js_path}/bootstrap/js/sb-admin-2.js"></script>
        <script src="${js_path}/loading.js" type="text/javascript"></script>
        <script src="${js_path}/main.js" type="text/javascript"></script>
        <script src="${js_path}/paging/jquery.simplePagination.js" type="text/javascript"></script>

        <link rel="stylesheet" href="${js_path}/bootstrap/css/bootstrap.min.css" type="text/css" title="ui-theme" />
        <link rel="stylesheet" href="${js_path}/bootstrap/css/bootstrap-theme.min.css" type="text/css" title="ui-theme" />
        <link rel="stylesheet" href="${js_path}/bootstrap/css/plugin/metisMenu.css" type="text/css" title="ui-theme" />
        <link rel="stylesheet" href="${js_path}/bootstrap/fonts/css/font-awesome.min.css" type="text/css" title="ui-theme" />
        <link rel="stylesheet" href="${js_path}/bootstrap/css/sb-admin-2.css" type="text/css" title="ui-theme" />
        <link rel="stylesheet" href="${js_path}/main.css" type="text/css" title="ui-theme" />
        <link rel="stylesheet" href="${js_path}/paging/style-paging.css" type="text/css" title="ui-theme" />
    </head>
    <body>
        <div id="wrapper">
            <!-- Header -->
            <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
                <tiles:insertAttribute name="header" />
            </nav>
            <!-- Body-->
            <div id="page-wrapper">
                <tiles:insertAttribute name="body" />
            </div>
            <!-- Footer -->
            <tiles:insertAttribute name="footer" />
        </div>
    </body>
</html>