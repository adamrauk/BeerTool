    	<script type="text/javascript" src="/BeerTool/scripts/jquery.js"></script>
   	 	<script type="text/javascript" src="/BeerTool/scripts/jquery.flot.js"></script>
   	 	<script type="text/javascript" src="/BeerTool/scripts/jquery.flot.resize.js"></script>
   	 	<script type="text/javascript" src="/BeerTool/scripts/jquery.flot.selection.js"></script>
   	 	<script type="text/javascript" src="/BeerTool/scripts/date.js"></script>
   	 	<script type="text/javascript" src="/BeerTool/scripts/leastsquares.js"></script>

			<script type="text/javascript">
	        var lsParams = {liquor: [], wort: []};
			var t = {};
	        var timerref;
			var timerindx;
			function timedCount(cinput,timerref,timerindx) /*cinput=targettime, timerref=id of timer, timerindx=index number of timeout*/
			{
			
				var now = new Date();
				var c=(Number(cinput)*1000-Number(now.getTime()+now.getTimezoneOffset()*60*1000))/1000;
				var pad = "00";
				var seconds=""+Math.round(c%60);
				var minutes=Math.floor(c/60);
				document.getElementById(timerref).innerHTML=minutes+":"+pad.substring(0,pad.length-seconds.length)+seconds;
				if(c>=0) {
					t[timerindx]=setTimeout("timedCount("+cinput+",\'"+timerref+"\',"+timerindx+")",1000);
				}
				else {
					document.getElementById(timerref).innerHTML="";
				}
			}

			/*Obtain target time for liquor*/
		   function getTargetTime() {
			    var targetTemp = document.getElementById('targettemp').value;
				var now = new Date();
			    var timeoffset =  now.getTimezoneOffset()*60*1000;
			    lsParams['liquor'] = fitLine(liquorCurrent['time'],liquorCurrent['temp']);
			    var predictedtime = (targetTemp - lsParams['liquor'][0]) / lsParams['liquor'][1];
			    if ((d0+predictedtime) > (now.getTime()+timeoffset)) {
					var c=Math.round(predictedtime/1000);
					timedCount(Math.round(((d0+predictedtime))/1000),"liquortimer",0);
				}
		   }

			/*Get target time for wort (includes obtaining ls fit of current data)*/
		   function getTargetTimeWort() {
			    var targetTemp = document.getElementById('targettempwort').value;
				var now = new Date();
			    var timeoffset =  now.getTimezoneOffset()*60*1000;
			    lsParams['wort'] = fitLine(wortCurrent['time'],wortCurrent['temp']);
			    var predictedtime = (targetTemp - lsParams['wort'][0]) / lsParams['wort'][1];
			    if ((d0+predictedtime) > (now.getTime()+timeoffset)) {
					var c=Math.round(predictedtime/1000);
					timedCount(Math.round(((d0+predictedtime))/1000),"worttimer",1);
				}
		   }

		    var options = {
		    		selection: {mode: "x"},
		    		legend: {container: placeholder2, noColumns: 5},
		    		grid: {hoverable: true, clickable: true, borderWidth: 0},
		    		xaxis: {mode: "time"},
		    		yaxis: {max: 220, min: 40}
		    };

	        var placeholder = $("#placeholder"); /*variable reference to plot placeholder div object*/
			var dataurl = "getBatch?batch.id=${batchid}";
			
           function onDataReceived(temparray2) {
	            var recipeinput = temparray2[0];
	            var measurementinput = temparray2[1];
	            var hopsinput = temparray2[2];
	            var grainsinput = temparray2[3];
	  	        var stage = [];
	  	        var stagewort = [];
	  	        var liquorTempArray = [];
	  	        var liquorIndex=[];
	  	        var liquorCurrent={time: [], temp: []};
	  	        var wortCurrent={time: [], temp: []};
	  			var wortTempArray = [];
	  	        var wortIndex=[];
	  	        var startboil = null;
	  	        var startmash = null;
	  	        var hopsArray = [];
	  	        var tempPredictions = {liquor: [], wort: []};
	  	        var boillength = recipeinput[0].boilTime;
	  	        var mashlength = recipeinput[0].mashTime;
		   	    	 
				/*var liquortimes=[];
				for (var i=1;i<measurementinput.length;i++) {
					liquortimes.push(Number(new Date(getDateFromFormat(measurementinput[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime()));
				}
				document.getElementById("txt").innerHTML=Math.min(liquortimes);*/
				var d0_liquor=Number(new Date(getDateFromFormat(measurementinput[0].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
				var d0_wort;
				
   			// go through Objects that are returned and convert them to a 2 dimensional vector [id, temp]
   			var d0 =Number(new Date(getDateFromFormat(measurementinput[0].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
   	        for(var i=0;i<measurementinput.length;i++) {
   	   				d=new Date(getDateFromFormat(measurementinput[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ'));
   	   				dMilliseconds=Number(d.getTime());
   	   				if (measurementinput[i].liquorTemperature != null) {
						if (measurementinput[i].length==1) {
							var d0_liquor=Number(new Date(getDateFromFormat(measurementinput[0].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
						};
	    	   			if (measurementinput[i].stage != null & measurementinput[i].stage != stage[stage.length-1]) {
	   				   		stage.push(null);
	   				   		liquorTempArray.push([null,null]);
	   				   		liquorIndex.push(null);
					        liquorCurrent={time: [], temp: []};
	   				    	var d0_liquor =Number(new Date(getDateFromFormat(measurementinput[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
   						};
   						stage.push(measurementinput[i].stage)
   		   				liquorTempArray.push([d, measurementinput[i].liquorTemperature]);
   						liquorIndex.push(measurementinput[i].id)
   						liquorCurrent['time'].push(dMilliseconds-d0_liquor)
   						liquorCurrent['temp'].push(measurementinput[i].liquorTemperature)
   	   				};
   	   				if (measurementinput[i].wortTemperature != null) {
						if (measurementinput[i].stage != null & measurementinput[i].stage != stagewort[stagewort.length-1]) {
				   			stagewort.push(null);
				   			wortCurrent={time: [], temp: []}
				   			dCurrentWort=[]
				    		var d0_wort=Number(new Date(getDateFromFormat(measurementinput[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
						}
						if (measurementinput[i].stage != null) {
							stagewort.push(measurementinput[i].stage)
						}
						else {
							stagewort.push(stagewort[stagewort.length-1])
						}
   	   					wortTempArray.push([d, measurementinput[i].wortTemperature]);
	  			   		wortIndex.push(measurementinput[i].id)
   						wortCurrent['time'].push(dMilliseconds-d0_wort)
   						wortCurrent['temp'].push(measurementinput[i].wortTemperature)
   			   		}
					if (measurementinput[i].stage == "Boil" & startboil == null) {
						var startboil = new Date(getDateFromFormat(measurementinput[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ'))
					}
					if (measurementinput[i].stage == "Mash" & startmash == null) {
						var startmash = new Date(getDateFromFormat(measurementinput[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ'))
					}
   	   				if (measurementinput[i].wortTemperature == null & measurementinput[i].stage == "Boil") {
						if (measurementinput[i].stage != null & measurementinput[i].stage != stagewort[stagewort.length-1]) {
				   			wortCurrent={time: [], temp: []}
				   			dCurrentWort=[]
				    		var d0_wort =Number(new Date(getDateFromFormat(measurementinput[i].dateCreated,'yyyy-MM-ddTHH:mm:ssZ')).getTime());
						}
						stagewort.push(measurementinput[i].stage)
   	   					wortTempArray.push([d, 212]);
	  			   		wortIndex.push(measurementinput[i].id)
   						wortCurrent['time'].push(dMilliseconds-d0_wort)
   						wortCurrent['temp'].push(212)
					}
   	        }

   	        if (startboil != null) {
	    	    for(var i=0;i<hopsinput.length;i++) {
		   			var dHops=new Date(Number(startboil.getTime())+Number((boillength-hopsinput[i].boilTime)*60*1000));
		   			hopsArray.push(
		   				[dHops, 
	  	    			212]);
	    	    }
   	        }
				
			var now = new Date();
			var timeoffset =  now.getTimezoneOffset()*60*1000;
			
			var targetTemp = document.getElementById('targettemp').value;
		    lsParams['liquor'] = fitLine(liquorCurrent['time'],liquorCurrent['temp']);
		    var predictedtime_liquor = (targetTemp - lsParams['liquor'][0]) / lsParams['liquor'][1];

			var targetTemp = document.getElementById('targettempwort').value;
		    lsParams['wort'] = fitLine(wortCurrent['time'],wortCurrent['temp']);
		    var predictedtime_wort = (targetTemp - lsParams['wort'][0]) / lsParams['wort'][1];

			tempPredictions['wort'].push([null,null]);
			tempPredictions['wort'].push([null,null]);
			tempPredictions['liquor'].push([null,null]);
			tempPredictions['liquor'].push([null,null]);
			
				if (Math.round((now.getTime()+timeoffset-d0_liquor-Math.max.apply(Math,liquorCurrent['time']))/1000) < 60*60) {
					tempPredictions['liquor'][0]=([
	    	            new Date(d0_liquor), lsParams['liquor'][0]+liquorCurrent['time'][0]*lsParams['liquor'][1]]);
	    	        tempPredictions['liquor'][1]=([
	    	            new Date(now.getTime()+timeoffset),  lsParams['liquor'][0]+(now.getTime()+timeoffset-d0_liquor)*lsParams['liquor'][1]]);
				}
				if (Math.round((now.getTime()+timeoffset-d0_wort-Math.max.apply(Math,wortCurrent['time']))/1000) < 60*60) {
					tempPredictions['wort'][0]=([
	    	            new Date(d0_wort+wortCurrent['time'][0]), lsParams['wort'][0]+wortCurrent['time'][0]*lsParams['wort'][1]]);
	    	        tempPredictions['wort'][1]=([
	    	            new Date(now.getTime()+timeoffset),  lsParams['wort'][0]+(now.getTime()+timeoffset-d0_wort)*lsParams['wort'][1]]);
				}
				function updatePredictions() {
					var now = new Date();
					var timeoffset =  now.getTimezoneOffset()*60*1000;
					
					if (Math.round((now.getTime()+timeoffset-d0_liquor-Math.max.apply(Math,liquorCurrent['time']))/1000) < 60*60) {
		    	        tempPredictions['liquor'][1]=([
		    	        	new Date(now.getTime()+timeoffset),  lsParams['liquor'][0]+(now.getTime()+timeoffset-d0_liquor)*lsParams['liquor'][1]]);
					}
					if (Math.round((now.getTime()+timeoffset-d0_wort-Math.max.apply(Math,wortCurrent['time']))/1000) < 60*60) {
		    	        tempPredictions['wort'][1]=([
	 	    	            new Date(now.getTime()+timeoffset),  lsParams['wort'][0]+(now.getTime()+timeoffset-d0_wort)*lsParams['wort'][1]]);
					} 	
					if (startboil != null & Math.round((now.getTime()+timeoffset-d0_wort-Math.max.apply(Math,wortCurrent['time']))/1000) <= boillength*60) {
						if (Math.round((Number(startboil.getTime())+Number(boillength*60*1000))/100) == Math.round((now.getTime()+timeoffset)/100)) {
							$.ajax({
				                url: "/BeerTool/measurement/customsave?batch.id=${batchid}&stage=Boil",
				                method: 'GET'
				            });
				            window.location.reload();
						}
						tempPredictions['wort'][0]=([
						    new Date(startboil+timeoffset), 212]);
						tempPredictions['wort'][1]=([
						    new Date(now.getTime()+timeoffset),  212]);
					};
				}
				updatePredictions();
   	        
   	        var plot = $.plot($("#placeholder"), 
   	 	    	   [{color:"#66A3E0", data: tempPredictions['liquor'], lines: {show: true}, points: {show: false}},
   	 	    	   {color:"#D8AF9E", data: tempPredictions['wort'], lines: {show: true}, points: {show: false}},
	    	 	    	{label: "Liquor", color: "#0066CC", data: liquorTempArray, lines: {show: true}, points: {show: true}}, 
   	 		    	{label: "Wort", color: "#B05F3C", data: wortTempArray, lines: {show: true}, points: {show: false}},
   	 		    	{label: "Hops", color:"#009900", data: hopsArray, lines: {show: false}, points: {show: true}}
   	 		    	],
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
		   		        	   window.location = '/BeerTool/measurement/editValue/'+indexArray[item.dataIndex]
		    		           }
	    		           if (item.series.label == "Liquor") {
								var indexArray = liquorIndex;
		   		        	   window.location = '/BeerTool/measurement/editValue/'+indexArray[item.dataIndex]
		    		           }
   		           }
   		       });
   		       $("#placeholder").append('<div id="graphboiltimer" style="position:absolute;left: 0px;top: 0px;color:#666;font-size:medium"></div>');
   		       $("#placeholder").append('<div id="graphmashtimer" style="position:absolute;left: 0px;top: 0px;color:#666;font-size:medium"></div>');
   		       $("#placeholder").append('<div id="graphliquortimer" style="position:absolute;left: 0px;top: 0px;color:#666;font-size:medium"></div>');
  		       $("#placeholder").append('<div id="graphworttimer" style="position:absolute;left: 0px;top: 0px;color:#666;font-size:medium"></div>');


				if ((d0_liquor+predictedtime_liquor) > (now.getTime()+timeoffset)) {
					clearTimeout(t[0]);
					var c=Math.round(predictedtime_liquor/1000);
					timedCount(Math.round(((d0_liquor+predictedtime_liquor))/1000),
						"graphliquortimer",0);
				}
				else {clearTimeout(t[0]); 
					document.getElementById('graphliquortimer').innerHTML=""
				}

				if ((d0_wort+predictedtime_wort) > (now.getTime()+timeoffset)) {
					clearTimeout(t[1]);
					var c=Math.round(predictedtime_wort/1000);
					timedCount(Math.round(((d0_wort+predictedtime_wort))/1000),"graphworttimer",1);
				}
				else {clearTimeout(t[1]); 
					document.getElementById('graphworttimer').innerHTML=""
				}

	  	        if (startmash != null) {
	  	        	if (Number(startmash.getTime())+Number(mashlength*60*1000) > (now.getTime()+timeoffset)) {
	   	        		timedCount(Math.round(((Number(startmash.getTime())+Number(mashlength*60*1000)))/1000),"graphmashtimer",2);
	    	        }
	  	        }

	  	        if (startboil != null) {
		  	        clearTimeout(t[2])
	    	        for(var i=0;i<hopsinput.length;i++) {
	    	        	if (Number(startboil.getTime())+Number((boillength-hopsinput[i].boilTime)*60*1000) > (now.getTime()+timeoffset)) {
	    	        		timedCount(Math.round(((Number(startboil.getTime())+Number((boillength-hopsinput[i].boilTime)*60*1000)))/1000),"hoptimer["+hopsinput[i].id+"]",i+2);
		    	        }
	    	        }
	   	        	if (Number(startboil.getTime())+Number(boillength*60*1000) > (now.getTime()+timeoffset)) {
	   	        		timedCount(Math.round(((Number(startboil.getTime())+Number(boillength*60*1000)))/1000),"graphboiltimer",hopsinput.length+3);
	    	        }
	    	        document.getElementById("messages").innerHTML="Boiling";
	   	        };

   		       function update() {
		   		     updatePredictions();
   			       boilval=document.getElementById("graphboiltimer").innerHTML;
   			        var o;
   			        var o2;
		           if (boilval != '') {
		   				$("#graphboiltimer").css({"position":"absolute","margin-left":"30%", "top": "10%", "font-family": "Verana", "font-size": "100px", "opacity":"0.3"});
	   			        document.getElementById("graphboiltimer").innerHTML=boilval;
	   			    }
	   				$("#graphmashtimer").css({"position":"absolute","margin-left":"30%", "top": "10%", "font-family": "Verana", "font-size": "100px", "opacity":"0.3"});
				    o = plot.pointOffset({ x: tempPredictions['liquor'][1][0], y: tempPredictions['liquor'][1][1]});
	   				$("#graphliquortimer").css({"position":"absolute","left":(o.left) +"px", "top": o.top + "px"});
				    o2 = plot.pointOffset({ x: tempPredictions['wort'][1][0], y: tempPredictions['wort'][1][1]});
	   				$("#graphworttimer").css({"position":"absolute","left":(o2.left) +"px", "top": o2.top + "px"});
					

   		           plot.setData(
   	    	 	    	   [{color:"#66A3E0", data: tempPredictions['liquor'], lines: {show: true}, points: {show: false}},
   	    	 	    	   {color:"#D8AF9E", data: tempPredictions['wort'], lines: {show: true}, points: {show: false}},
	    	    	 	    	{label: "Liquor", color: "#0066CC", data: liquorTempArray, lines: {show: true}, points: {show: true}}, 
   	    	 		    	{label: "Wort", color: "#B05F3C", data: wortTempArray, lines: {show: true}, points: {show: false}},
   	    	 		    	{label: "Hops", color:"#009900", data: hopsArray, lines: {show: false}, points: {show: true}}
   	    	 		    	]);
					   plot.setupGrid();
   		           plot.draw();
	   			    
		           setTimeout(update, 10);
   		       }
   		       update();

	    	        var pgArray = [];
	    	        TGU=0;
	    	        for(var i=0;i<grainsinput.length;i++) {
						pgArray.push(grainsinput[i].potentialGravity * grainsinput[i].amount);
						TGU+=grainsinput[i].potentialGravity * grainsinput[i].amount;
			    	}

	    	        var sgVal = [];
	    	        var tempVal = [];
	    	        var volume = [];
	    			var adjsgVal = [];
	    			var targetSG = [];
	    			targetSG.push([recipeinput[0].targetSG]);
	    			var targetVol = [];
	    			targetVol.push([recipeinput[0].targetVolume]);

	    			for(var i=0;i<measurementinput.length;i++) {
		   				if (measurementinput[i].specificGravity != null) {
			   				sgVal.push([measurementinput[i].specificGravity]);
			   				tempVal.push([measurementinput[i].specificGravityTemperature]);
		  				         
		   					}
	   					if (measurementinput[i].wortVolume != null) {
	   	   					volume.push([measurementinput[i].wortVolume]);
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
			
					var currentSG = Math.round(adjsgVal[adjsgVal.length-1]*1000)/1000;
				    			
					document.getElementById("sgreadings").innerHTML = currentSG;
					document.getElementById("wortvolume").innerHTML=volume[volume.length-1];
					document.getElementById("targetsg").innerHTML=targetSG[targetSG.length-1];
					document.getElementById("targetvol").innerHTML=targetVol[targetVol.length-1];
					document.getElementById("sugarunits").innerHTML=Math.round(targetSU - currentSU);
					document.getElementById("newvol").innerHTML=Math.round(newVol*10)/10;
		
					if (isNaN(currentSG)) {document.getElementById("trcurrentsg").style.display="none"}
					if (isNaN(Math.round(targetSU - currentSU))) {document.getElementById("trsugarunits").style.display="none"}
					if (isNaN(Math.round(newVol*10)/10)) {document.getElementById("trnewvol").style.display="none"}

					var efficiencyurl="/BeerTool/batch/saveEfficiency/${batchid}?efficiency=" + Math.round(10000 * currentSU / TGU)/100
		            $.ajax({
		                url: efficiencyurl,
		                method: 'GET'
		            });
					
   		       
           }
           
			
		$(function () {

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
	        
	    });
	           
		</script>   	
	   