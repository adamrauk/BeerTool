

<%@ page import="beertool.Measurement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measurement.label', default: 'Measurement')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${measurementInstance}">
            <div class="errors">
                <g:renderErrors bean="${measurementInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${measurementInstance?.id}" />
                <g:hiddenField name="version" value="${measurementInstance?.version}" />
                <g:hiddenField name="batch.id" value="${measurementInstance?.batch?.id}"  />

                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="customupdate" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="customdelete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="stage"><g:message code="measurement.stage.label" default="Stage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'stage', 'errors')}">
                                    <g:select name="stage" from="${measurementInstance.constraints.stage.inList}" value="${measurementInstance?.stage}" valueMessagePrefix="measurement.stage" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="liquorTemperature"><g:message code="measurement.liquorTemperature.label" default="Liquor Temperature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'liquorTemperature', 'errors')}">
                                    <g:textField name="liquorTemperature" value="${fieldValue(bean: measurementInstance, field: 'liquorTemperature')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="liquorVolume"><g:message code="measurement.liquorVolume.label" default="Liquor Volume" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'liquorVolume', 'errors')}">
                                    <g:textField name="liquorVolume" value="${fieldValue(bean: measurementInstance, field: 'liquorVolume')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="wortTemperature"><g:message code="measurement.wortTemperature.label" default="Wort Temperature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'wortTemperature', 'errors')}">
                                    <g:textField name="wortTemperature" value="${fieldValue(bean: measurementInstance, field: 'wortTemperature')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="wortVolume"><g:message code="measurement.wortVolume.label" default="Wort Volume" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'wortVolume', 'errors')}">
                                    <g:textField name="wortVolume" value="${fieldValue(bean: measurementInstance, field: 'wortVolume')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="specificGravity"><g:message code="measurement.specificGravity.label" default="Specific Gravity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'specificGravity', 'errors')}">
                                    <g:textField name="specificGravity" value="${fieldValue(bean: measurementInstance, field: 'specificGravity')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="specificGravityTemperature"><g:message code="measurement.specificGravityTemperature.label" default="Specific Gravity Temperature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'specificGravityTemperature', 'errors')}">
                                    <g:textField name="specificGravityTemperature" value="${fieldValue(bean: measurementInstance, field: 'specificGravityTemperature')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateAdded"><g:message code="measurement.dateAdded.label" default="Date Added" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'dateAdded', 'errors')}">
                                    <g:datePicker name="dateAdded" precision="minute" precision="day" value="${measurementInstance?.dateAdded}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
            </g:form>
        </div>
    </body>
</html>
