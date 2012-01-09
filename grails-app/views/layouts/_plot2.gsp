<div id="plot2">
    	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.resize.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.selection.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/date.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/leastsquares.js"></script>

			<script type="text/javascript">
			var t;
			var timer_is_on=0;
			var c=3;
			function timedCount(c)
			{
					var pad = "00";
					var seconds=""+c%60;
					var minutes=Math.floor(c/60);
					document.getElementById('timer').innerHTML=minutes+":"+pad.substring(0,pad.length-seconds.length)+seconds;
					c=c-1;
					if(c>=0) {
						t=setTimeout("timedCount("+c+")",1000);
					}
			}
			
			function doTimer()
			{
			if (!timer_is_on)
			  {
			  timer_is_on=1;
			  timedCount();
			  }
			}

		   function getTargetTime() {
			    var targetTemp = document.getElementById('targettemp').value;
				var now = new Date();
			    var timeoffset =  now.getTimezoneOffset()*60*1000;
			    var predictedtime = fitLine(liquorCurrent['time'],liquorCurrent['temp'],targetTemp);
			    if ((d0+predictedtime) > (now.getTime()+timeoffset)) {
					var c=Math.round(predictedtime/1000);
					timedCount(Math.round(((d0+predictedtime)-(now.getTime()+timeoffset))/1000));
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
	   <span id="timer"></span> 
	   	<br>
	   	Temperature: <g:textField name="targettemp" value="160" />
	   	<input class="getTargetTime" type="button" value="Refresh Timer" onclick="getTargetTime()">
	   <span id="clickdata"></span>
	   <span id="txt"></span>
		<script id="source2" language="javascript" type="text/javascript">
		
		$(function () {

		    var options = {
			    		selection: {mode: "x"},
			    		legend: {container: placeholder2},
			    		grid: {hoverable: true, clickable: true, borderWidth: 0},
			    		xaxis: {mode: "time"}
		    		};

            var placeholder = $("#placeholder");
	        

	//        $("input.fetchSeries").click(function () {
//	            var button = $(this);
	            
	            // find the URL in the link right next to us 
	//            var dataurl = button.siblings('a').attr('href');
				var dataurl = 'getStuff?batch.id=${batchid}'
	            // then fetch the data with jQuery

			     function getLocalTime(gmt)  {
			       var min = gmt.getTime() / 1000 / 60; // convert gmt date to minutes
			       var localNow = new Date().getTimezoneOffset(); // get the timezone 
			                                                      // offset in minutes            
			       var localTime = min - localNow; // get the local time
			       return new Date(localTime * 1000 * 60); // convert it into a date
			    }	            
	            
	            function onDataReceived(temparray2) {
		            var temparray = temparray2[1];
	    	        var stage = [];
	    	        var liquorTempArray = [];
	    	        var liquorIndex=[];
	    	        var liquorCurrent={time: [], temp: []};
	    			var wortTempArray = [];
	    	        var wortIndex=[];
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
	    		   				wortTempArray.push([
	    		  			   			d, 
	    		  			   			temparray[i].wortTemperature]);
		  			   			wortIndex.push(temparray[i].id)
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
						clearTimeout(t);
						var c=Math.round(predictedtime/1000);
						timedCount(Math.round(((d0+predictedtime)-(now.getTime()+timeoffset))/1000));
						
					}
//					document.getElementById('txt').innerHTML=Math.round(d0+predictedtime);
//						document.getElementById('txt').innerHTML=now.getTime()+timeoffset;
									
 //	    	        document.getElementById('txt').innerHTML=fitLine(liquorCurrent[0],liquorCurrent[1],160);
//	    	        document.getElementById('txt').innerHTML=new Date(liquorCurrent['time'][1])
//	    	        document.getElementById('txt').innerHTML=liquorCurrent['time'][1]

	                // and plot all we got
	    	       var plot = $.plot($("#placeholder"), 
	    	 	    	   [{label: "Liquor", color: "#0066CC", data: liquorTempArray, lines: {show: true}, points: {show: true}}, 
	    	 		    	{label: "Wort", color: "#B05F3C", data: wortTempArray, lines: {show: true}, points: {show: false}}],
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
	    		               //$("#clickdata").text("You clicked point " + indexArray[item.dataIndex] + " in " + item.series.label + ".");
	    		               //plot.highlight(item.series, item.datapoint);
	    		           }
	    		       });

	    		       
	            }
	            
	            
	            $.ajax({
	                url: dataurl,
	                method: 'GET',
	                dataType: 'json',
	                success: onDataReceived
	            });
	//        });

			$("input.getTargetTime").click(function() {
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
	   