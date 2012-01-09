<div id="sgcalculations">
    	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/jquery.flot.js"></script>
   	 	<script language="javascript" type="text/javascript" src="/BeerTool/scripts/date.js"></script>

	<table border=0 width="100">
	<tr class="prop"><td>Current SG</td>
		<td id="sgreadings"></td></tr>
	<tr class="prop"><td>Wort Volume</td>
		<td id="wortvolume"></td></tr>
	<tr class="prop"><td>Target SG</td>
		<td id="targetsg"></td></tr>
	<tr class="prop"><td>Target Volume</td>
		<td id="targetvol"></td></tr>
	<tr class="prop"><td>Sugar Units Off Target</td>
		<td id="sugarunits"></td></tr>
	<tr class="prop"><td>New Volume</td>
		<td id="newvol"></td></tr>

	</table>

		<script id="source3" type="text/javascript">

		$(function () {
		function displaySG(inputdata) {
			var recipearray = inputdata[0];
			var temparray = inputdata[1];

			var dataurl2 = 'getStuff?batch.id=${batchid}';
			
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
			var newVol = (adjsgVal[adjsgVal.length-1]-1)*volume[volume.length-1]/(targetSG[targetSG.length-1]-1);
	   		
	//		document.getElementById("sgreadings").innerHTML=adjsgVal.join();
	//		document.write("Temperature adjusted SG: "+adjsgVal[adjsgVal.length-1]);
			document.getElementById("sgreadings").innerHTML=Math.round(adjsgVal[adjsgVal.length-1]*1000)/1000;
			document.getElementById("wortvolume").innerHTML=volume[volume.length-1];
			document.getElementById("targetsg").innerHTML=targetSG[targetSG.length-1];
			document.getElementById("targetvol").innerHTML=targetVol[targetVol.length-1];
			document.getElementById("sugarunits").innerHTML=Math.round(targetSU - currentSU);
			document.getElementById("newvol").innerHTML=Math.round(newVol*10)/10;
			
		}

        $.ajax({
            url: dataurl2,
            method: 'GET',
            dataType: 'json',
            success: displaySG
        });
		
		})
		</script>

</div>