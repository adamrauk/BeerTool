<html>
	<head>
		<script language="javascript" type="text/javascript">

		var temparray = ${datavals};
		var d0 = Number(new Date(getDateFromFormat(temparray[0].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
        var yvals = [];
        var x = [];
		for(var i=0;i<temparray.length;i++) {
				if (temparray[i].liquorTemperature != null) {
					d=(Number(new Date(getDateFromFormat(temparray[i].lastUpdated,'yyyy-MM-ddTHH:mm:ssZ')).getTime())-d0)/(1000*60);
					x.push(d)
					yvals.push(temparray[i].liquorTemperature);
				}
		}
		function leastsquaresfit() {
	        // go through Objects that are returned and convert them to a 2 dimensional vector [id, temp]
			document.getElementById('predicted').innerHTML =  findLineByLeastSquares(x, yvals, 160) 
		}

		//var now = Number(new Date().getTime()) - d0;
		var c = findLineByLeastSquares(x, yvals, 160);
		var t;
		function timedCount() {
			document.getElementById('timer').value=c;
			c=c-1/60;
			t=setTimeout("timedCount()",1000);
		}

				
		
		</script>

 	</head>
	<body onload="leastsquaresfit();timedCount()">
		<g:render template="/layouts/header" />		
		<g:render template="/layouts/plot" /> 	
		

	<p>
	Predicted: 
	<SPAN ID="predicted"></SPAN>
	</p>	 
	<p>
	Timer: 
	<input type="text" id="timer">	
	</p>	 
	   
	</body>
</html>
