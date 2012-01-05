<div id="plot2">
    	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.resize.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.selection.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/date.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/leastsquares.js"></script>

		 <p>
<!--		      <input class="fetchSeries" type="button" value="Show Data"> -->
<!--  		      <a href="getData?batch.id=1">data</a> --->
		      <span></span>
		    </p>
    		<div id="placeholder"  style="width:400px;height:200px;"></div>
		<div id="placeholder2"  style="width:50px;height:50px;"></div>
	   <div id="overview" style="margin-left:50px;margin-top:20px;width:400px;height:50px"></div>
	   

		<script id="source2" language="javascript" type="text/javascript">
		$(function () {

		    var options = {
			    		selection: {mode: "x"},
			    		legend: {container: placeholder2},
			    		grid: {hoverable: true, borderWidth: 0}
		    		};

	        var stage = [];
	        var dataVal = [];
			var dataVal2 = [];
		    var temparray = [];

            var placeholder = $("#placeholder");
	        
	        $.plot($("#placeholder"), 
 	 	    	   [{label: "Liquor", color: "#0066CC", data: dataVal, lines: {show: true}, points: {show: true}}, 
 	 		    	{label: "Wort", color: "#B05F3C", data: dataVal2, lines: {show: true}, points: {show: false}}],
 	 		    	options
 	 		    	);


	//        $("input.fetchSeries").click(function () {
//	            var button = $(this);
	            
	            // find the URL in the link right next to us 
	//            var dataurl = button.siblings('a').attr('href');
				var dataurl = 'getStuff?batch.id=${batchid}'
	            // then fetch the data with jQuery
	            
	            function onDataReceived(temparray2) {
		            var temparray = temparray2[1];
	    	        var stage = [];
	    	        var dataVal = [];
	    			var dataVal2 = [];
	    	        // go through Objects that are returned and convert them to a 2 dimensional vector [id, temp]
	    			var d0 =Number(new Date(getDateFromFormat(temparray[0].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
	    	        for(var i=0;i<temparray.length;i++) {
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
	    	  			   		
	    	   		}
	    	   		
		                
	                // and plot all we got
	    	        $.plot($("#placeholder"), 
	    	 	    	   [{label: "Liquor", color: "#0066CC", data: dataVal, lines: {show: true}, points: {show: true}}, 
	    	 		    	{label: "Wort", color: "#B05F3C", data: dataVal2, lines: {show: true}, points: {show: false}}],
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
	    		       	        

	    		       
	            }
	            
	            $.ajax({
	                url: dataurl,
	                method: 'GET',
	                dataType: 'json',
	                success: onDataReceived
	            });
	//        });

	        

	        // and plot all we got
	           // make sure to include dataVal as an array because flot expects you to have 1 or more series to plot

	    });
	           
		</script>   	
		
</div>
	   