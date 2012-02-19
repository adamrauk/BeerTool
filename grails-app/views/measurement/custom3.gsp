<%@ page import="beertool.Measurement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measurement.label', default: 'Measurement')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    	<script type="text/javascript" src="/BeerTool/scripts/jquery.js"></script>
		<script>
			$(document).ready(function(){
			  $("#flip").click(function(){
				  $("#sgdisplay").slideToggle("slow");
			  });
			  $("#flipliquor").click(function(){
				  $("#liquorTempEntry").show();
				  $("#liquorVolEntry").show();
				  $("#wortTempEntry").hide();
				  $("#wortVolEntry").hide();
				  $("#SGEntry").hide();
				  $("#SGTempEntry").hide();
				  $("#enterdata").css({"background-color":"#AACCEE"});
			  });
			  $("#flipwort").click(function(){
				  $("#liquorTempEntry").hide();
				  $("#liquorVolEntry").hide();
				  $("#wortTempEntry").show();
				  $("#wortVolEntry").show();
				  $("#SGEntry").hide();
				  $("#SGTempEntry").hide();
				  $("#enterdata").css({"background-color":"#D8AF9E"});
			  });
			  $("#flipsg").click(function(){
				  $("#liquorTempEntry").hide();
				  $("#liquorVolEntry").hide();
				  $("#wortTempEntry").hide();
				  $("#wortVolEntry").hide();
				  $("#SGEntry").show();
				  $("#SGTempEntry").show();
				  $("#enterdata").css({"background-color":"#FFFFFF"});
			  });
			  
			});

		</script>        
		<style type="text/css">
			div#flipliquor
			{
			float:left;
			width:100px;
			background:#AACCEE;
			color:#333333;
			text-align:center;
			cursor:pointer;
			
			}
			div#flipwort
			{
			float:left;
			width:100px;
			background:#D8AF9E;
			color:#333333;
			text-align:center;
			cursor:pointer;
			}
			div#flipsg
			{
			text-align:center;
			cursor:pointer;
			}
			p#flip
			{
			cursor:pointer;
			}
			#wortTempEntry
			{
			display:none;
			}
			#wortVolEntry
			{
			display:none;
			}
			#SGEntry
			{
			display:none;
			}
			#SGTempEntry
			{
			display:none;
			}
			#enterdata
			{
			background:#AACCEE;
			}
			#graphboiltimer
			{
			cursor:default;
			}
			
		</style>
        
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link controller="measurement" class="list" action="listbatch" params="['batch.id': batchInstance.id]"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
             <span class="menuButton"><g:link controller="recipe" class="list" action="show" params="[id: recipeInstance.id]">Recipe</g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${measurementInstance}">
            <div class="errors">
                <g:renderErrors bean="${measurementInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="customsave" >
					<div id="placeholder"  style="width:650px;height:200px;"></div>
					<div style="position:relative;">
						<div id="placeholder2"  style="width:50px;height:50px;"></div>
						<div id="messages"  style="position:absolute;top:0px;right:0px;width:400px;height:50px;overflow-y:scroll;"></div>
					</div>					
                <div class="buttons" style="clear:both;">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button" style="float: right"><g:link class="edit" controller="batch" action="show" params="[id: batchInstance.id]">Edit Batch</g:link></span>

                </div>
                <div class="dialog">
							
				<table>
					<tr><td>
	                <div id="flipliquor">Liquor</div><div id="flipwort">Wort</div><div id="flipsg">Specific Gravity</div>
                    <table id=enterdata>
                        <tbody>
							<tr></tr>                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="stage"><g:message code="measurement.stage.label" default="Stage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'stage', 'errors')}">
                                    <g:select name="stage" from="${measurementInstance.constraints.stage.inList}" value="${measurementInstance?.stage}" valueMessagePrefix="measurement.stage" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop" id="liquorTempEntry">
                                <td valign="top" class="name">
                                    <label for="liquorTemperature"><g:message code="measurement.liquorTemperature.label" default="Liquor Temperature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'liquorTemperature', 'errors')}">
                                    <g:textField name="liquorTemperature" value="${fieldValue(bean: measurementInstance, field: 'liquorTemperature')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop" id="liquorVolEntry">
                                <td valign="top" class="name">
                                    <label for="liquorVolume"><g:message code="measurement.liquorVolume.label" default="Liquor Volume" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'liquorVolume', 'errors')}">
                                    <g:textField name="liquorVolume" value="${fieldValue(bean: measurementInstance, field: 'liquorVolume')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop" id="wortTempEntry">
                                <td valign="top" class="name">
                                    <label for="wortTemperature"><g:message code="measurement.wortTemperature.label" default="Wort Temperature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'wortTemperature', 'errors')}">
                                    <g:textField name="wortTemperature" value="${fieldValue(bean: measurementInstance, field: 'wortTemperature')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop" id="wortVolEntry">
                                <td valign="top" class="name">
                                    <label for="wortVolume"><g:message code="measurement.wortVolume.label" default="Wort Volume" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'wortVolume', 'errors')}">
                                    <g:textField name="wortVolume" value="${fieldValue(bean: measurementInstance, field: 'wortVolume')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop" id="SGEntry">
                                <td valign="top" class="name">
                                    <label for="specificGravity"><g:message code="measurement.specificGravity.label" default="Specific Gravity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'specificGravity', 'errors')}">
                                    <g:textField name="specificGravity" value="${fieldValue(bean: measurementInstance, field: 'specificGravity')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop" id="SGTempEntry">
                                <td valign="top" class="name">
                                    <label for="specificGravityTemperature"><g:message code="measurement.specificGravityTemperature.label" default="Specific Gravity Temperature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'specificGravityTemperature', 'errors')}">
                                    <g:textField name="specificGravityTemperature" value="${fieldValue(bean: measurementInstance, field: 'specificGravityTemperature')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                               <!--      <label for="batch"><g:message code="measurement.batch.label" default="Batch" /></label> -->
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: measurementInstance, field: 'batch', 'errors')}">
                              <!--       <g:select name="batch.id" from="${beertool.Batch.list()}" optionKey="id" value="${measurementInstance?.batch?.id}"  /> -->
                                   <input name="batch.id" type="hidden" value="${measurementInstance?.batch?.id}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>

                    </td>
                    <td rowspan="3">


              			<g:render template="/layouts/plot2" />
					    	<table width="100">
								<tr><th>Target:</th><th>Liquor</th><th>Wort</th></tr>
								<tr><td><input class="timerbutton" type="button" value="Refresh" /></td>
									<td><g:textField name="targettemp" value="${recipeInstance.mashTemperature}" style="width:40px" /></td>
									<td><g:textField name="targettempwort" value="${recipeInstance.mashTemperature}" style="width:40px" /></td></tr>
							</table><br>
						   	
						   <span id="clickdata"></span>
						   <span id="txt"></span><br>
						   <span id="txt2"></span>
							
							<table>
								<tr><th>Countdown</th><th>Hop Addition</th></tr>
						        <g:each in="${recipeInstance.recipeHops}" var="r">
								<tr><td>
					            <span id="hoptimer[${r.id}]" title="${r.boilTime}"></span></td><td><g:link controller="recipeHops" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link>
					        	  </td></tr>
					        </g:each>
							</table>
							<p id="flip">show/hide</p>
							<table id="sgdisplay">
								<tr class="prop" id="trcurrentsg"><td>Current SG</td>
									<td id="sgreadings"></td></tr>
								<tr class="prop"><td>Wort Volume</td>
									<td id="wortvolume"></td></tr>
								<tr class="prop"><td>Target SG</td>
									<td id="targetsg"></td></tr>
								<tr class="prop"><td>Target Volume</td>
									<td id="targetvol"></td></tr>
								<tr class="prop" id="trsugarunits"><td>Gravity Units Off Target</td>
									<td id="sugarunits"></td></tr>
								<tr class="prop" id="trnewvol"><td>New Volume</td>
									<td id="newvol"></td></tr>
							
							</table>
					
        

	      			</td></tr>
          			</table> 	
                    
                </div>
            </g:form>
        </div>
    </body>
</html>
