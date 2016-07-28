function NodeColor(r,g,b){
    this.red = r;
    this.green = g;
    this.blue = b;
}

function findMax(listIn){
    var max = 0;
    for(var i = 0; i < listIn.length; i++){
        if(listIn[i][2] > max){
            max = listIn[i][2];
        }
    }
    return max;
}

function findMin(listIn){
    var min = 100000;
    for(var i = 0; i < listIn.length; i++){
        if(listIn[i][2] < min){
            min = listIn[i][2];
        }
    }
    return min;
}

function makeColorsList(dataList){
    
    var colorsList = [];
    var max = findMax(dataList);
    var min = findMin(dataList);
    
    var maxColor = new NodeColor(0,0,255);
    var minColor = new NodeColor(255,0,0);
    
    for(var i = 0; i < dataList.length; i++){
        var scorePer = calcScorePerc(max, min, dataList[i][2]);
        var r = getNewColorValue(maxColor.red, minColor.red, scorePer);
        var g = getNewColorValue(maxColor.green, minColor.green, scorePer);
        var b = getNewColorValue(maxColor.blue, minColor.blue, scorePer);
        
        var currNodeColor = new NodeColor(r,g,b);
        colorsList.push("#"+RGBToHex(currNodeColor));
    }
    
    return colorsList;
}



function getNewColorValue(maxC, minC, scorePer){
    
    var cRange = maxC - minC;
    var newC = 0;
    if (cRange < 0){
        cRange = cRange * -1;
        scorePer = 100 - scorePer;
        newC = (scorePer * cRange) / 100;
        newC = maxC + newC;

    }else{
        newC = (scorePer * cRange) / 100;
        newC = minC + newC;

    }
    
    return newC;
}

function RGBToHex(colorIn){
    var bin = colorIn.red << 16 | colorIn.green << 8 | colorIn.blue;
    return (function(h){
        return new Array(7-h.length).join("0")+h
    })(bin.toString(16).toUpperCase())
}

function calcScorePerc(max, min, score){
    var range = max - min;
    return ((score - min) * 100) / range;
}

function drawOrgChart(dataInput){
google.charts.load('current', {packages:["orgchart"]});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
          
        var colorList = makeColorsList(dataInput);
          
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Topic');
        data.addColumn('string', 'Parent');
        data.addColumn('number', 'Score');
          
        // For each orgchart box, provide the name, manager, and tooltip to show.
        data.addRows(dataInput);
          
        // Create the chart.
        var chart = new google.visualization.OrgChart(document.getElementById('section'));
          
          for(var i = 0; i < dataInput.length; i++){
            data.setRowProperty(i, 'style', 'border: 10px solid '+colorList[i]+'');  
            
          }
        // Draw the chart, setting the allowHtml option to true for the tooltips.
         chart.draw(data, {allowHtml:true,ready:true,selectionColor:'#999999'});
          
          google.visualization.events.addListener(chart, 'select', selectHandler);
        
        function selectHandler(e) {
              var selection = chart.getSelection();
              for (var i = 0; i < selection.length; i++) {
                var item = selection[i];
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
            if(topic != null && score != null){
                document.getElementById("text").innerHTML = "Topic: " + topic + " Score: " + score;
            }else{
                document.getElementById("text").innerHTML = "Select a topic to see the overall score";
            }
          }
      }
}


drawOrgChart(visualizationList);