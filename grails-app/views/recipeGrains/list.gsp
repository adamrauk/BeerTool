
<%@ page import="beertool.RecipeGrains" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipeGrains.label', default: 'RecipeGrains')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'recipeGrains.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="amount" title="${message(code: 'recipeGrains.amount.label', default: 'Amount')}" />
                        
                            <th><g:message code="recipeGrains.grains.label" default="Grains" /></th>
                        
                            <th><g:message code="recipeGrains.recipe.label" default="Recipe" /></th>
                        
                            <g:sortableColumn property="targetTemperature" title="${message(code: 'recipeGrains.targetTemperature.label', default: 'Target Temperature')}" />
                        
                            <g:sortableColumn property="targetTime" title="${message(code: 'recipeGrains.targetTime.label', default: 'Target Time')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${recipeGrainsInstanceList}" status="i" var="recipeGrainsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${recipeGrainsInstance.id}">${fieldValue(bean: recipeGrainsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: recipeGrainsInstance, field: "amount")}</td>
                        
                            <td>${fieldValue(bean: recipeGrainsInstance, field: "grains")}</td>
                        
                            <td>${fieldValue(bean: recipeGrainsInstance, field: "recipe")}</td>
                        
                            <td>${fieldValue(bean: recipeGrainsInstance, field: "targetTemperature")}</td>
                        
                            <td>${fieldValue(bean: recipeGrainsInstance, field: "targetTime")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${recipeGrainsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
