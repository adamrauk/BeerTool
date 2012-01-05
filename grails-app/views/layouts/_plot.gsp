<div id="plot">
    	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.resize.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.selection.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/date.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/leastsquares.js"></script>

		<div id="placeholder"  style="width:400px;height:200px;"></div>
		<div id="placeholder2"  style="width:50px;height:50px;"></div>
	   <div id="overview" style="margin-left:50px;margin-top:20px;width:400px;height:50px"></div>

		<script id="source2" language="javascript" type="text/javascript">
		$(function () {

		    var placeholder = $("#placeholder");
			  
	        var temparray = ${datavals};
	        var stage = [];
	        var dataVal = [];
			var dataVal2 = [];
	        // go through Objects that are returned and convert them to a 2 dimensional vector [id, temp]
			var d0 =Number(new Date(getDateFromFormat(temparray[0].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
	        for(var i=0;i<temparray.length;i++) {
	   			/*d=Number(formatDate(new Date(getDateFromFormat(temparray[i].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')),'ss'))/(60*60)+
	   				Number(formatDate(new Date(getDateFromFormat(temparray[i].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')),'mm'))/60+
	   				Number(formatDate(new Date(getDateFromFormat(temparray[i].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')),'HH')) -
	   				d0;*/
	   				d=(Number(new Date(getDateFromFormat(temparray[i].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')).getTime())-d0)/(1000*60);

	   				if (temparray[i].liquorTemperature != null) {
							if (temparray[i].stage != null & temparray[i].stage != stage[stage.length-1]) {
				   				stage.push(null);
				   				dataVal.push([null,null])
								}
						stage.push(temparray[i].stage)
		   				dataVal.push([
	  			   			d, 
	  			   			temparray[i].liquorTemperature]);
	   				}
	   				if (temparray[i].wortTemperature != null) {
		   				dataVal2.push([
		  			   			d, 
		  			   			temparray[i].wortTemperature]);
			   		}
	  			   		
	  				//dataVal.push([temparray[i].id, temparray[i].temp]);
	   		}
	   		
	        // and plot all we got
	           // make sure to include dataVal as an array because flot expects you to have 1 or more series to plot

		    var options = {
		    	  //    selection: { mode: "x" },
		    		legend: {container: placeholder2},
		    		grid: {hoverable: true, borderWidth: 0}
	    		};

	        var plot =  $.plot($("#placeholder"), 
	    	   [{label: "Liquor", color: "#0066CC", data: dataVal, lines: {show: true}, points: {show: true}}, 
		    	{label: "Wort", color: "#B05F3C", data: dataVal2, lines: {show: true}, points: {show: false}}],
		    	options
		    	);

/*	       var overview = $.plot($("#overview"), 
	    		   [{color: "#0066CC", data: dataVal, lines: {show: true}, points: {show: false}}, 
			    	{color: "#B05F3C", data: dataVal2, lines: {show: true}, points: {show: false}}],
	    	        {
	           series: {
	               lines: { show: true, lineWidth: 1 },
	               shadowSize: 0
	           },
	           xaxis: { ticks: []},
	           yaxis: { ticks: [], min: 0, autoscaleMargin: 0.1 },
	           selection: { mode: "x" }
	       });

	       $("#placeholder").bind("plotselected", function (event, ranges) {
	           // do the zooming
	           plot = $.plot($("#placeholder"), [dataVal],
	                         $.extend(true, {}, options, {
	                             xaxis: { min: ranges.xaxis.from, max: ranges.xaxis.to }
	                         }));

	           // don't fire event on the overview to prevent eternal loop
	           overview.setSelection(ranges, true);
	       });
	       
	       $("#overview").bind("plotselected", function (event, ranges) {
	           plot.setSelection(ranges);
	       });*/

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
	       	        
			//add text to graph (note: doesn't behave well with window resize)
	       /*var o;
	       o = plot.pointOffset({ x: 0, y: yvals[0]});
	       placeholder.append('<div style="position:absolute;left:' + (o.left + 4) + 'px;top:' + o.top + 'px;color:#666;font-size:smaller">Warming up</div>');*/

	       placeholder.resize(function () {
	           $(".message").text("Placeholder is now "
	                              + $(this).width() + "x" + $(this).height()
	                              + " pixels");
	       });
	    });
	           
		</script>   	
		
</div>
	   