
<%@ page import="com.springminutes.example.Cow" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cow.label', default: 'Cow')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'cow.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="color" title="${message(code: 'cow.color.label', default: 'Color')}" />
                        
                            <g:sortableColumn property="legs" title="${message(code: 'cow.legs.label', default: 'Legs')}" />
                        
                            <g:sortableColumn property="breed" title="${message(code: 'cow.breed.label', default: 'Breed')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${cowInstanceList}" status="i" var="cowInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${cowInstance.id}">${fieldValue(bean: cowInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: cowInstance, field: "color")}</td>
                        
                            <td>${fieldValue(bean: cowInstance, field: "legs")}</td>
                        
                            <td>${fieldValue(bean: cowInstance, field: "breed")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${cowInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
