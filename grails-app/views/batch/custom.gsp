<html>
	<head>
 	</head>


		<script type="text/javascript">
		function displaySG(temparray,recipearray)
		{
	        var sgVal = [];
	        var tempVal = [];
	        var volume = [];
			var adjsgVal = [];
			var targetSG = [];
			targetSG.push([recipearray[0].targetSG]);
			var targetVol = [];
			targetVol.push([recipearray[0].targetVolume]);

			for(var i=0;i<temparray.length;i++) {
	   				if (temparray[i].specificGravity != null) {
		   				sgVal.push([temparray[i].specificGravity]);
		   				tempVal.push([temparray[i].specificGravityTemperature]);
	  				         
	   					}
   					if (temparray[i].wortVolume != null) {
   	   					volume.push([temparray[i].wortVolume]);
   	   				}
	   		}
	   		for(var i=0;i<sgVal.length;i++) {
		   		adjsgVal.push(
		   				parseFloat(sgVal[i])+(1.313454-0.132674*tempVal[i]+0.002057793*Math.pow(tempVal[i],2)-0.000002627634*Math.pow(tempVal[i],3))/1000
				)

	   		}
	   		var currentSU = 1000*(adjsgVal[adjsgVal.length-1]-1)*volume[volume.length-1];
			var targetSU = 1000*(targetSG[targetSG.length-1]-1)*targetVol[targetVol.length-1];
			var newVol = adjsgVal[adjsgVal.length-1]*volume[volume.length-1]/targetSG[targetSG.length-1];
	   		
			document.getElementById("sgreadings").innerHTML=adjsgVal.join();
	//		document.write("<br>");
	//		document.write("Temperature adjusted SG: "+adjsgVal[adjsgVal.length-1]);
	//		document.write("<br>");
			document.getElementById("wortvolume").innerHTML=volume.join();
	//		document.write("<br>");
			document.getElementById("targetsg").innerHTML=targetSG[targetSG.length-1];
	//		document.write("<br>");
			document.getElementById("targetvol").innerHTML=targetVol[targetVol.length-1];
	//		document.write("<br>");
			document.getElementById("sugarunits").innerHTML=(targetSU - currentSU);
	//		document.write("<br>");
			document.getElementById("newvol").innerHTML=newVol;
			
		}
		</script>

	<body onload="displaySG(${datavals},${recipevals})">
	The temperature adjusted specific gravity readings are: 
	<p id="sgreadings"></p>
	<table border=1>
	<tr><td>Wort Volume</td>
		<td><p id="wortvolume"></p></td></tr>
	<tr><td>Target SG</td>
		<td><p id="targetsg"></p></td></tr>
	<tr><td>Target Volume</td>
		<td><p id="targetvol"></p></td></tr>
	<tr><td>Sugar Units Off Target</td>
		<td><p id="sugarunits"></p></td></tr>
	<tr><td>New Volume</td>
		<td><p id="newvol"></p></td></tr>

	</table>

	    <!--  this div will be where the graph will be drawn, notice the #placeholder in the jquery code above -->

		<!-- It seems that the function below overrides the 'onload' call to the script in the header?-->
	</body>
</html>
