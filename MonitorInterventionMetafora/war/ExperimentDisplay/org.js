//Constructor for the NodeColor object
function NodeColor(r,g,b){
    this.red = r;
    this.green = g;
    this.blue = b;
}

//returns maximum score value of the org chart
//takes a double array of the org chart data
function findMax(listIn){
    var max = 0;
    for(var i = 0; i < listIn.length; i++){
        if(listIn[i][2] > max){
            max = listIn[i][2];
        }
    }
    return max;
}

//returns minimum score value of the org chart
//takes a double array of the org chart data
function findMin(listIn){
    //var min = 10000000;
    var min = listIn[1][2];
    for(var i = 0; i < listIn.length; i++){
        if(listIn[i][2] < min){
            min = listIn[i][2];
        }
    }
    return min;
}


//returns a list of NodeColor objects that correspond by order to the nodes in the org chart
//takes a double array of the org chart data
function makeColorsList(dataList){
    
    var colorsList = [];
    
    var max = findMax(dataList);
    var min = findMin(dataList);
    
    //this is where you assign the color for the highest score
    var maxColor = new NodeColor(0,0,255);
    //this is where you assign the color for the lowest score
    var minColor = new NodeColor(255,0,0);
    
    for(var i = 0; i < dataList.length; i++){
        if(dataList[i][2] >= 0){   
            min = 0;
            max = 1
            maxColor = new NodeColor(0,200,0);
            minColor = new NodeColor(0,0,180);
            var scorePer = calcScorePerc(max, min, dataList[i][2]);

            //assigns new values to each color channel based on how the score is compared to the max and min.
            var r = getNewColorValue(maxColor.red, minColor.red, scorePer);
            var g = getNewColorValue(maxColor.green, minColor.green, scorePer);
            var b = getNewColorValue(maxColor.blue, minColor.blue, scorePer);

            var currNodeColor = new NodeColor(r,g,b);
            colorsList.push("#"+RGBToHex(currNodeColor));
        }else{
            min = -1;
            max = 0
            maxColor = new NodeColor(0,0,180);
            minColor = new NodeColor(255,0,0);
            var scorePer = calcScorePerc(max, min, dataList[i][2]);

            //assigns new values to each color channel based on how the score is compared to the max and min.
            var r = getNewColorValue(maxColor.red, minColor.red, scorePer);
            var g = getNewColorValue(maxColor.green, minColor.green, scorePer);
            var b = getNewColorValue(maxColor.blue, minColor.blue, scorePer);

            var currNodeColor = new NodeColor(r,g,b);
            colorsList.push("#"+RGBToHex(currNodeColor));
        }
    
    }
    
    return colorsList;
}

function makeColorsList2(dataList){
    
    var colorsList = [];
    var max = findMax(dataList);
    var min = findMin(dataList);
    
    //this is where you assign the color for the highest score
    var maxColor = new NodeColor(0,0,255);
    //this is where you assign the color for the lowest score
    var minColor = new NodeColor(255,0,0);
    
    for(var i = 0; i < dataList.length; i++){
        var scorePer = calcScorePerc(max, min, dataList[i][2]);
        
        //assigns new values to each color channel based on how the score is compared to the max and min.
        var r = getNewColorValue(maxColor.red, minColor.red, scorePer);
        var g = getNewColorValue(maxColor.green, minColor.green, scorePer);
        var b = getNewColorValue(maxColor.blue, minColor.blue, scorePer);
        
        var currNodeColor = new NodeColor(r,g,b);
        colorsList.push("#"+RGBToHex(currNodeColor));
    }
    
    return colorsList;
}


//returns numbber between 0-255 
//takes the maximum value of the color channel, the minimum value of the color channel, and the score percentage
//(where the score falls proportionally between the max and the min)
function getNewColorValue(maxC, minC, scorePer){
    
    var cRange = maxC - minC;
    var newC = 0;
    //for negative difference
    if (cRange < 0){
        cRange = cRange * -1;
        scorePer = 100 - scorePer;
        newC = (scorePer * cRange) / 100;
        newC = maxC + newC;
        
    }else{ //for positive differences
        newC = (scorePer * cRange) / 100;
        newC = minC + newC;

    }
    
    return newC;
}

//returns a string that is the hexadecimal code for the color passed in
//takes a NodeColor object
function RGBToHex(colorIn){
    var bin = colorIn.red << 16 | colorIn.green << 8 | colorIn.blue;
    return (function(h){
        return new Array(7-h.length).join("0")+h
    })(bin.toString(16).toUpperCase())
}

//returns the "score percentage" or where it falls proportionally to the max and min of the score set
//takes max of score set, min of score set, and score
function calcScorePerc(max, min, score){
    var range = max - min;
    return ((score - min) * 100) / range;
}

//functions draws the org chart and handles of the selection 
//takes a double array (rows and columns) of org chart data.
//expecting the form (Topic, Parent, Score)
function drawOrgChart(dataInput){
    
    //loading google charts libraries.
      google.charts.load('current', {packages:["orgchart"]});
    //call drawChart when the page has loaded all of the files.
      google.charts.setOnLoadCallback(drawChart);
        
    
        //define drawChart()
      function drawChart() {
          
        //create the list of colors that corresponde to each node
        var colorList = makeColorsList(dataInput);
          
        //create a data table
        var data = new google.visualization.DataTable();
        //define your data table columns
        data.addColumn('string', 'Topic');
        data.addColumn('string', 'Parent');
        data.addColumn('number', 'Score');
          
        // Add all of the rows that were primed in readJson file.
        data.addRows(dataInput);
        // Create the chart in the html div called "chart" and assign it to variable chart
        var chart = new google.visualization.OrgChart(document.getElementById('section'));
          
          
          //for every node in the chart, set the color property to the corresponding color.
          for(var i = 0; i < dataInput.length; i++){
            data.setRowProperty(i, 'style', 'border: 10px solid '+colorList[i]+'');  
            
          }
        // Draw the chart, setting the allowHtml option to true for the selection.
         chart.draw(data, {allowHtml:true,ready:true,selectionColor:'#999999',allowCollapse:true,size:'small'});
          //enable the chart to listen for selection of nodes
          google.visualization.events.addListener(chart, 'select', selectHandler);
          //iterate through each row of the data and collapse it except the first two rows.
          for(var i = dataInput.length-1; i > 0; i--){
            //collapse the row at index i in the chart data
              if(dataInput[i][2] >= 1){
                chart.collapse(i,true);  
              }
          }
        
        //define the select handler
        //what should happen when a given node is selected
        function selectHandler(e) {
            //assigns the node that is selected to variable selection
              var selection = chart.getSelection();
              for (var i = 0; i < selection.length; i++) {
                var item = selection[i];
                  //checking that either the row or column is not null
                  //sets topic equal to the node name
                  //sets score equal to the node score
                if (item.row != null && item.column != null) {
                  var topic = data.getFormattedValue(item.row, item.column);
                  var score = data.getFormattedValue(item.row, 2);
                } else if (item.row != null) {
                  var topic = data.getFormattedValue(item.row, 0);
                  var score = data.getFormattedValue(item.row, 2);
                } else if (item.column != null) {
                  var topic = data.getFormattedValue(0, item.column);
                  var score = data.getFormattedValue(item.row, 2);
                }
              }
            //if the user has selected a node, set the div with the id "text" to the following string
            if(topic != null && score != null){
                document.getElementById("text").innerHTML = "Topic: " + topic;
            }else{
                document.getElementById("text").innerHTML = "Select a topic to see the overall score";
            }
          }
      }
}