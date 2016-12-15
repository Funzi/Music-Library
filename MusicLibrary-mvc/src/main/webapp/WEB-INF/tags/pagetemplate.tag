<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ attribute name="scripts" fragment="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<c:set var="username" value="${pageContext.request.userPrincipal.name}"/>
<html lang="${pageContext.request.locale}">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><c:out value="${title}"/></title>
        <!-- bootstrap loaded from content delivery network -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"  crossorigin="anonymous">
        <jsp:invoke fragment="head"/>

        <style>
            .navbar-login
            {
                width: 305px;
                padding: 10px;
                padding-bottom: 0px;
            }

            .navbar-login-session
            {
                padding: 10px;
                padding-bottom: 0px;
                padding-top: 0px;
            }

            .icon-size
            {
                font-size: 87px;
            }

            .form-signin {
                max-width: 330px;
                padding: 15px;
                margin: 0 auto;
            }

            .form-signin .form-signin-heading,
            .form-signin .checkbox {
                margin-bottom: 10px;
            }

            .form-signin .checkbox {
                font-weight: normal;
            }

            .form-signin .form-control {
                position: relative;
                height: auto;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                box-sizing: border-box;
                padding: 10px;
                font-size: 16px;
            }

            .form-signin .form-control:focus {
                z-index: 2;
            }

            .form-signin input {
                margin-top: 10px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }

            .form-signin button {
                margin-top: 10px;
            }

            .has-error {
                color: red
            }

            .ui-sortable-helper {
                display: table;
            }

            .ui-widget {
                font-size:80%;
            }
        </style>
    </head>
    <body>
        <!-- navigation bar -->
        <nav class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}"><f:message key="navigation.project"/></a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><my:a href="/albums/"><f:message key="navigation.albums"/></my:a></li>
                        <li><my:a href="/genres/"><f:message key="navigation.genres"/></my:a></li>
                        <li><my:a href="/musicians/"><f:message key="navigation.musicians"/></my:a></li>
                        <li><my:a href="/songs/"><f:message key="navigation.songs"/></my:a> </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><f:message key="navigation.admin"/><b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><my:a href="/order/list/all"><f:message key="navigation.admin.orders"/></my:a></li>
                                <li><my:a href="/user/list"><f:message key="navigation.admin.customers"/></my:a></li>
                                <li><my:a href="/product/list"><f:message key="navigation.admin.products"/></my:a></li>
                                <li><my:a href="/category/list"><f:message key="navigation.admin.categories"/></my:a></li>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><f:message key="navigation.docs"/><b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li class="dropdown-header">Javadocs</li>
                                <li><a href="http://docs.oracle.com/javase/8/docs/api/">JDK 8 API</a></li>
                                <li><a href="http://docs.oracle.com/javaee/6/api/">Java EE 6 API</a></li>
                                <li><a href="http://docs.spring.io/spring/docs/current/javadoc-api/">Spring API</a></li>
                                <li role="separator" class="divider"></li>
                                <li class="dropdown-header">Other</li>
                                <li><a href="http://getbootstrap.com/css/">Bootstrap CSS</a></li>
                                <li><a href="http://getbootstrap.com/components/">Bootstrap components</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><f:message key="navigation.about"/><b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="https://is.muni.cz/predmet/fi/podzim2015/PA165">PA165</a></li>
                                <li><a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">SpringMVC</a></li>
                                <li><a href="http://getbootstrap.com/">Bootstrap</a></li>
                                <li><a href="https://maven.apache.org/">Maven</a></li>
                            </ul>
                        </li>
                    </ul>
                    <c:choose>
                        <c:when test="${username != null}">
                            <ul class="nav navbar-nav navbar-right">
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        <span class="glyphicon glyphicon-user"></span> 
                                        <strong><c:out value="${username}" /></strong>
                                        <span class="glyphicon glyphicon-chevron-down"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <div class="navbar-login">
                                                <div class="row">
                                                    <div class="col-lg-4">
                                                        <p class="text-center">
                                                            <span class="glyphicon glyphicon-user icon-size"></span>
                                                        </p>
                                                    </div>
                                                    <div class="col-lg-8">
                                                        <p class="text-left"><strong><c:out value="${username}" /></strong></p>
                                                        <p class="text-left small">test@example.com</p>
                                                        <p class="text-left">
                                                            <a href="#" class="btn btn-primary btn-block btn-sm">Profile</a>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <div class="navbar-login navbar-login-session">
                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <p>
                                                        <form id="logoutForm" method="POST" action="<c:url value="/logout" />">
                                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                        </form>
                                                        <my:a href="javascript:document.forms['logoutForm'].submit();" class="btn btn-danger btn-block">Logout</my:a>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                        </c:when>
                        <c:otherwise>
                            <ul class="nav navbar-nav navbar-right">
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        <span class="glyphicon glyphicon-user"></span> 
                                        <strong>Login</strong>
                                        <span class="glyphicon glyphicon-chevron-down"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <div class="navbar-login">
                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <form method="POST" action="<c:url value='/login' />" class="form-signin">
                                                            <h2 class="form-heading">Log in</h2>

                                                            <div class="form-group ${error != null ? 'has-error' : ''}">
                                                                <span>${message}</span>
                                                                <input name="username" type="text" class="form-control" placeholder="Username"
                                                                       autofocus="true"/>
                                                                <input name="password" type="password" class="form-control" placeholder="Password"/>
                                                                <span>${error}</span>
                                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                                                <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
                                                                <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
                                                            </div>

                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </nav>

        <div class="container">

            <!-- page title -->
            <c:if test="${not empty title}">
                <div class="page-header">
                    <h1><c:out value="${title}"/></h1>
                </div>
            </c:if>

            <!-- authenticated user info -->
            <c:if test="${not empty authenticatedUser}">
                <div class="row">
                    <div class="col-xs-6 col-sm-8 col-md-9 col-lg-10"></div>
                    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <c:out value="${authenticatedUser.givenName} ${authenticatedUser.surname}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <!-- alerts -->
            <c:if test="${not empty alert_danger}">
                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <c:out value="${alert_danger}"/></div>
                </c:if>
                <c:if test="${not empty alert_info}">
                <div class="alert alert-info" role="alert"><c:out value="${alert_info}"/></div>
            </c:if>
            <c:if test="${not empty alert_success}">
                <div class="alert alert-success" role="alert"><c:out value="${alert_success}"/></div>
            </c:if>
            <c:if test="${not empty alert_warning}">
                <div class="alert alert-warning" role="alert"><c:out value="${alert_warning}"/></div>
            </c:if>

            <!-- page body -->
            <jsp:invoke fragment="body"/>

        </div>
        <!-- footer -->
        <footer class="footer">
            <div class="container">
                <p class="text-muted">&copy;&nbsp;<%=java.time.Year.now().toString()%>&nbsp;Masaryk University</p>
            </div>
        </footer>
        <!-- javascripts placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
        <script src="<c:url value="/main.js" />"></script>
        <script>
            $(document).ready(function () {

                // data-confirm
                $('body').on('click', 'a[data-confirm], button[data-confirm], input[data-confirm]', function (e) {
                    if (!confirm($(this).attr('data-confirm'))) {
                        e.preventDefault();
                        e.stopImmediatePropagation();
                        return false;
                    }
                });
            });
            <jsp:invoke fragment="scripts"/>
        </script>
    </body>
</html>
