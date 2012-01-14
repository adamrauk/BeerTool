<div id="plot2">
    	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.resize.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.selection.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/date.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/leastsquares.js"></script>

			<script type="text/javascript">
			var t = {};
			var timerref;
			var timerindx;
			var hoptimer = {};
			function timedCount(c,timerref,timerindx)
			{
					var pad = "00";
					var seconds=""+c%60;
					var minutes=Math.floor(c/60);
					document.getElementById(timerref).innerHTML=minutes+":"+pad.substring(0,pad.length-seconds.length)+seconds;
					c=c-1;
					if(c>=0) {
						t[timerindx]=setTimeout("timedCount("+c+",\'"+timerref+"\',"+timerindx+")",1000);
					}
			}
			
			
		   function getTargetTime() {
			    var targetTemp = document.getElementById('targettemp').value;
				var now = new Date();
			    var timeoffset =  now.getTimezoneOffset()*60*1000;
			    var predictedtime = fitLine(liquorCurrent['time'],liquorCurrent['temp'],targetTemp);
			    if ((d0+predictedtime) > (now.getTime()+timeoffset)) {
					var c=Math.round(predictedtime/1000);
					timedCount(Math.round(((d0+predictedtime)-(now.getTime()+timeoffset))/1000),"liquortimer",0);
				}
		   }
			
		   function getTargetTimeWort() {
			    var targetTemp = document.getElementById('targettempwort').value;
				var now = new Date();
			    var timeoffset =  now.getTimezoneOffset()*60*1000;
			    var predictedtime = fitLine(wortCurrent['time'],wortCurrent['temp'],targetTemp);
			    if ((d0+predictedtime) > (now.getTime()+timeoffset)) {
					var c=Math.round(predictedtime/1000);
					timedCount(Math.round(((d0+predictedtime)-(now.getTime()+timeoffset))/1000),"worttimer",1);
				}
		   }

		   </script> 
		 <p>
<!--		      <input class="fetchSeries" type="button" value="Show Data"> -->
<!--  		      <a href="getData?batch.id=1">data</a> --->
		      <span></span>
		    </p>
    		<div id="placeholder"  style="width:400px;height:200px;"></div>
		<div id="placeholder2"  style="width:50px;height:50px;"></div>
	   <div id="overview" style="margin-left:50px;margin-top:20px;width:400px;height:50px"></div>
	   Liquor Timer: <span id="liquortimer"></span> 
	   	<br>
	   	Temperature: <g:textField name="targettemp" value="${recipeInstance.mashTemperature}" />
	   	<input class="timerbutton" type="button" value="Refresh Timer"">
		<br>
	   Wort timer: <span id="worttimer"></span> 
	   	<br>
	   	Temperature: <g:textField name="targettempwort" value="${recipeInstance.mashTemperature}" />
	   <span id="clickdata"></span>
	   <span id="txt"></span>
       	<g:set var="counter" value="${1}" />
		<table>
        <g:each in="${recipeInstance.recipeHops}" var="r">
        	<g:set var="boiltimes[${counter-1}]" value="${r.boilTime}" />
			<tr><td>
            <span id="hoptimer[${r.id}]" title="${r.boilTime}"></span></td><td><g:link controller="recipeHops" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link>
        	  </td></tr>
        	  <g:set var="counter" value="${counter+1}" />
        </g:each>
		
		</table>

		<script id="source2" language="javascript" type="text/javascript">
		
		$(function () {

		    var options = {
			    		selection: {mode: "x"},
			    		legend: {container: placeholder2, noColumns: 3},
			    		grid: {hoverable: true, clickable: true, borderWidth: 0},
			    		xaxis: {mode: "time"}
		    		};

            var placeholder = $("#placeholder");
	    
				var dataurl = 'getBatch?batch.id=${batchid}'
					            
	            function onDataReceived(temparray2) {
		            var temparray = temparray2[1];
		            var hopsinput = temparray2[2];
	    	        var stage = [];
	    	        var stagewort = [];
	    	        var liquorTempArray = [];
	    	        var liquorIndex=[];
	    	        var liquorCurrent={time: [], temp: []};
	    	        var wortCurrent={time: [], temp: []};
	    			var wortTempArray = [];
	    	        var wortIndex=[];
	    	        var startboil = null;
	    	        var hopsArray = [];
	    	        // go through Objects that are returned and convert them to a 2 dimensional vector [id, temp]
	    			var d0 =Number(new Date(getDateFromFormat(temparray[0].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
	    	        for(var i=0;i<temparray.length;i++) {
	    	   				dMilliseconds=(Number(new Date(getDateFromFormat(temparray[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime()));
	    	   				d=new Date(getDateFromFormat(temparray[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ'));

	    	   				if (temparray[i].liquorTemperature != null) {
	    							if (temparray[i].stage != null & temparray[i].stage != stage[stage.length-1]) {
	    				   				stage.push(null);
	    				   				liquorTempArray.push([null,null])
	    				   				liquorIndex.push(null)
						    	        liquorCurrent={time: [], temp: []};
	    				   				dCurrent=[]
	    				    			var d0 =Number(new Date(getDateFromFormat(temparray[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
	    								}
    								
	    						stage.push(temparray[i].stage)
	    		   				liquorTempArray.push([
	    	  			   			d, 
	    	  			   			temparray[i].liquorTemperature]);
	    						liquorIndex.push(temparray[i].id)
	    						liquorCurrent['time'].push(dMilliseconds-d0)
	    						liquorCurrent['temp'].push(temparray[i].liquorTemperature)
	    	   				}
	    	   				if (temparray[i].wortTemperature != null) {
    							if (temparray[i].stage != null & temparray[i].stage != stagewort[stagewort.length-1]) {
    				   				stagewort.push(null);
    				   				wortCurrent={time: [], temp: []}
    				   				dCurrentWort=[]
    				    			var d0 =Number(new Date(getDateFromFormat(temparray[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
    							}

    							stagewort.push(temparray[i].stage)
	    	   					wortTempArray.push([
	    		  			   			d, 
	    		  			   			temparray[i].wortTemperature]);
		  			   			wortIndex.push(temparray[i].id)
	    						wortCurrent['time'].push(dMilliseconds-d0)
	    						wortCurrent['temp'].push(temparray[i].wortTemperature)
	    			   		}
							if (temparray[i].stage == "Boil" & startboil == null) {
								var startboil = new Date(getDateFromFormat(temparray[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ'))}
	    	   				if (temparray[i].wortTemperature == null & temparray[i].stage == "Boil") {
    							if (temparray[i].stage != null & temparray[i].stage != stagewort[stagewort.length-1]) {
    				   				wortCurrent={time: [], temp: []}
    				   				dCurrentWort=[]
    				    			var d0 =Number(new Date(getDateFromFormat(temparray[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
    							}
    							stagewort.push(temparray[i].stage)
	    	   					wortTempArray.push([
	    		  			   			d, 
	    		  			   			212]);
		  			   			wortIndex.push(temparray[i].id)
	    						wortCurrent['time'].push(dMilliseconds-d0)
	    						wortCurrent['temp'].push(212)
							}
	    	        }

	    	        if (startboil != null) {
		    	        for(var i=0;i<hopsinput.length;i++) {
			   				hopsArray.push([new Date(Number(startboil.getTime())+Number((60-hopsinput[i].boilTime)*60*1000)), 
		  	    	  			212]);
		    	        }
	    	        }
					
					var now = new Date();
					var timeoffset =  now.getTimezoneOffset()*60*1000;
//					var predictedtime = new Date(Math.round(Number(fitLine(liquorCurrent['time'],liquorCurrent['temp'],160)))+timeoffset);

					var targetTemp = document.getElementById('targettemp').value;
					var predictedtime = fitLine(liquorCurrent['time'],liquorCurrent['temp'],targetTemp);
//					document.getElementById('txt').innerHTML=predictedtime;
					if ((d0+predictedtime) > (now.getTime()+timeoffset)) {
//						document.getElementById('txt').innerHTML=new Date(Math.round(d0+predictedtime)-timeoffset);
						clearTimeout(t[0]);
						var c=Math.round(predictedtime/1000);
						timedCount(Math.round(((d0+predictedtime)-(now.getTime()+timeoffset))/1000),
								"liquortimer",0);
					}
					else {clearTimeout(t[0]); 
						document.getElementById('liquortimer').innerHTML=""}

					var targetTemp = document.getElementById('targettempwort').value;
					var predictedtime = fitLine(wortCurrent['time'],wortCurrent['temp'],targetTemp);
					if ((d0+predictedtime) > (now.getTime()+timeoffset)) {
						clearTimeout(t[1]);
						var c=Math.round(predictedtime/1000);
						timedCount(Math.round(((d0+predictedtime)-(now.getTime()+timeoffset))/1000),"worttimer",1);
					}
					else {clearTimeout(t[1]); 
						document.getElementById('worttimer').innerHTML=""}

	    	        if (startboil != null) {
		    	        for(var i=0;i<hopsinput.length;i++) {
		    	        	if (Number(startboil.getTime())+Number((60-hopsinput[i].boilTime)*60*1000) > (now.getTime()+timeoffset)) {
		    	        		timedCount(Math.round(((Number(startboil.getTime())+Number((60-hopsinput[i].boilTime)*60*1000))-(now.getTime()+timeoffset))/1000),"hoptimer["+hopsinput[i].id+"]",i+2);
			    	        	}

		    	        }

	    	        }
					document.getElementById('txt').innerHTML=hopsinput[0].id;
					

	    	       var plot = $.plot($("#placeholder"), 
	    	 	    	   [{label: "Liquor", color: "#0066CC", data: liquorTempArray, lines: {show: true}, points: {show: true}}, 
	    	 		    	{label: "Wort", color: "#B05F3C", data: wortTempArray, lines: {show: true}, points: {show: false}},
	    	 		    	{label: "Hops", color:"#009900", data: hopsArray, lines: {show: false}, points: {show: true}}],
	    	 		    	options
	    	 		    	);
	    		       function showTooltip(x, y, contents) {
	    		           $('<div id="tooltip">' + contents + '</div>').css( {
	    		               position: 'absolute',
	    		               display: 'none',
	    		               top: y + 5,
	    		               left: x + 5,
	    		               border: '1px solid #fdd',
	    		               padding: '2px',
	    		               'background-color': '#fee',
	    		               opacity: 0.80
	    		           }).appendTo("body").fadeIn(200);
	    		       }

	    		       var previousPoint = null;
	    		       $("#placeholder").bind("plothover", function (event, pos, item) {
	    		           $("#x").text(pos.x.toFixed(2));
	    		           $("#y").text(pos.y.toFixed(2));

	    		               if (item) {
	    		                   if (previousPoint != item.dataIndex) {
	    		                       previousPoint = item.dataIndex;
	    		                       
	    		                       $("#tooltip").remove();
	    		                       var x = item.datapoint[0].toFixed(2),
	    		                           y = item.datapoint[1].toFixed(0);
	    		                       
	    		                       showTooltip(item.pageX, item.pageY,
	    		                                   item.series.label + ": " + y);
	    		                   }
	    		               }

	    		       });
	    		       $("#placeholder").bind("plotclick", function (event, pos, item) {
	    		           if (item) {
		    		           if (item.series.label == "Wort") {
									var indexArray = wortIndex;
			    		           }
		    		           if (item.series.label == "Liquor") {
									var indexArray = liquorIndex;
			    		           }
		    		           		    		           
	    		        	   window.location = '/BeerTool/measurement/editValue/'+indexArray[item.dataIndex]
	    		           }
	    		       });

	    		       
	            }
	            
	            
	            $.ajax({
	                url: dataurl,
	                method: 'GET',
	                dataType: 'json',
	                success: onDataReceived
	            });

			$("input.timerbutton").click(function() {
	            $.ajax({
	                url: dataurl,
	                method: 'GET',
	                dataType: 'json',
	                success: onDataReceived
	            });
			})
	        
	        // and plot all we got
	           // make sure to include liquorTempArray as an array because flot expects you to have 1 or more series to plot

	    });
	           
		</script>   	
		
</div>
	   