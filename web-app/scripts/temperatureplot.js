		$(function () {
		    var options = {
		        lines: { show: true },
		        points: { show: true },
		        xaxis: {
		           //mode: "time"
		           //timeformat: "%H/%M/%S"
//		           minTickSize: [1, "hour"] 
		        }
	      	};
		    var data = [];
		    var placeholder = $("#placeholder");
		  
		    // fetch one series, adding to what we got
		    var alreadyFetched = {};
		    
		    $("input.fetchSeries").click(function () {
		        var button = $(this);
		        
		        // find the URL in the link right next to us 
		        var dataurl = button.siblings('a').attr('href');
		
		        // then fetch the data with jQuery
		        function onDataReceived(temparray) {

			        var dataVal = [];
					var dataVal2 = [];
			        // go through Objects that are returned and convert them to a 2 dimensional vector [id, temp]
					var d0 =Number(new Date(getDateFromFormat(temparray[0].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
			        for(var i=0;i<temparray.length;i++) {
			   			/*d=Number(formatDate(new Date(getDateFromFormat(temparray[i].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')),'ss'))/(60*60)+
			   				Number(formatDate(new Date(getDateFromFormat(temparray[i].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')),'mm'))/60+
			   				Number(formatDate(new Date(getDateFromFormat(temparray[i].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')),'HH')) -
			   				d0;*/
			   				d=(Number(new Date(getDateFromFormat(temparray[i].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')).getTime())-d0)/(1000*60*60);
			   				dataVal.push([
		   			   			d, 
		   			   			temparray[i].liquorTemperature]);
			   				dataVal2.push([
		   			   			d, 
		   			   			temparray[i].wortTemperature]);
		   			   		
		   				//dataVal.push([temparray[i].id, temparray[i].temp]);
			   		}
			   		
		            // and plot all we got
		            // make sure to include dataVal as an array because flot expects you to have 1 or more series to plot
		            $.plot(placeholder, [dataVal, dataVal2]);
		         }

		        // make an async ajax call to the grails server and get the list of temps
		        $.ajax({
		            url: dataurl,
		            method: 'GET',
		            dataType: 'json',
		            success: onDataReceived 
		        });
		    });
	
		});
