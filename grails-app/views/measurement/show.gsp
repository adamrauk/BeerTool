
<%@ page import="beertool.Measurement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measurement.label', default: 'Measurement')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: measurementInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.stage.label" default="Stage" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: measurementInstance, field: "stage")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.liquorTemperature.label" default="Liquor Temperature" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: measurementInstance, field: "liquorTemperature")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.liquorVolume.label" default="Liquor Volume" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: measurementInstance, field: "liquorVolume")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.wortTemperature.label" default="Wort Temperature" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: measurementInstance, field: "wortTemperature")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.wortVolume.label" default="Wort Volume" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: measurementInstance, field: "wortVolume")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.specificGravity.label" default="Specific Gravity" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: measurementInstance, field: "specificGravity")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.specificGravityTemperature.label" default="Specific Gravity Temperature" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: measurementInstance, field: "specificGravityTemperature")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.dateAdded.label" default="Date Added" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${measurementInstance?.dateAdded}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.batch.label" default="Batch" /></td>
                            
                            <td valign="top" class="value"><g:link controller="batch" action="show" id="${measurementInstance?.batch?.id}">${measurementInstance?.batch?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${measurementInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="measurement.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${measurementInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${measurementInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
