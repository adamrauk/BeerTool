

<%@ page import="beertool.RecipeGrains" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'recipeGrains.label', default: 'RecipeGrains')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${recipeGrainsInstance}">
            <div class="errors">
                <g:renderErrors bean="${recipeGrainsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="amount"><g:message code="recipeGrains.amount.label" default="Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeGrainsInstance, field: 'amount', 'errors')}">
                                    <g:textField name="amount" value="${fieldValue(bean: recipeGrainsInstance, field: 'amount')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="grains"><g:message code="recipeGrains.grains.label" default="Grains" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeGrainsInstance, field: 'grains', 'errors')}">
                                    <g:select name="grains.id" from="${beertool.Grains.list()}" optionKey="id" value="${recipeGrainsInstance?.grains?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="recipe"><g:message code="recipeGrains.recipe.label" default="Recipe" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeGrainsInstance, field: 'recipe', 'errors')}">
                                    <g:select name="recipe.id" from="${beertool.Recipe.list()}" optionKey="id" value="${recipeGrainsInstance?.recipe?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="targetTemperature"><g:message code="recipeGrains.targetTemperature.label" default="Target Temperature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeGrainsInstance, field: 'targetTemperature', 'errors')}">
                                    <g:textField name="targetTemperature" value="${fieldValue(bean: recipeGrainsInstance, field: 'targetTemperature')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="targetTime"><g:message code="recipeGrains.targetTime.label" default="Target Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: recipeGrainsInstance, field: 'targetTime', 'errors')}">
                                    <g:textField name="targetTime" value="${fieldValue(bean: recipeGrainsInstance, field: 'targetTime')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
