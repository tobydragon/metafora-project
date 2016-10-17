var dataObject;

var names = [];
var visualizationList = [];

 var request = new XMLHttpRequest();
   request.open("GET", "input.json", false);
   request.send(null)
   
var objectsArray = JSON.parse(request.responseText);

for(var i = 0; i < objectsArray.length; i++){
    names.push(objectsArray[i].name);
}

writeMenu(names);

function writeMenu(names){
    var newCode = "<ul style='list-style: none;'>";
    for(var i = 0; i < names.length; i++){
        newCode += "<li><button type='button' onclick='makeChart(&quot;" + names[i] + "&quot;)'>" + names[i] + "</button></li>";
    }
    newCode += "</ul>";
    document.getElementById("menu").innerHTML = newCode;
}

function makeChart(currName){
    visualizationList = [];
    console.log(currName);
    document.getElementById("section").innerHTML = "";
    for(var i = 0; i < objectsArray.length; i++){
    names.push(objectsArray[i].name);
        if(String(objectsArray[i].name) == currName){
            dataObject = objectsArray[i].cg;
        }
    }
    console.log("dataobject" + dataObject);
    var roots = findRoot(dataObject);
    for(var i = 0; i < roots.length; i++){
        var row1 = [];
        row1.push(roots[i].id);
        row1.push(null);
        row1.push(roots[i].actualComp);
        visualizationList.push(row1);
    }

    for(var i = 0; i < dataObject.links.length; i++){
        var row = [];
        var c = dataObject.links[i].child;
        var p = dataObject.links[i].parent;
        for(var j = 0; j < dataObject.nodes.length; j++){
            if(dataObject.nodes[j].id == dataObject.links[i].child){
                var s = dataObject.nodes[j].actualComp;
            }
        }
        row.push({v:c, f:stripTitle(c)+'<div style="color:blue; font-style:italic">Score: '+s+'</div>'});
        //row.push(c);
        row.push(p);
        row.push(s);
        visualizationList.push(row);
    }
    
    drawOrgChart(visualizationList);
}
//visualizationList = removeQ(visualizationList);
//console.log(visualizationList);

//functions

function removeQ(visList){
    var outputList = [];
    var listLen = visList.length;
    
    for(var i = 0; i < listLen; i++){
        if(visList[i][0] != undefined){
            if(visList[i][0].indexOf("test") == -1){
                outputList.push(visList[i]);
            }
        }
    }
    return outputList;
}

function findRoot(dataList){
    var roots = [];
    for(var i = 0; i < dataList.nodes.length; i++){
        roots.push(dataList.nodes[i].id);
    }
    
    for(var i = 0; i < dataList.links.length; i++){
        for(var j = 0; j < dataList.nodes.length; j++){
            if(dataObject.links[i].child == dataList.nodes[j].id){
                var index = roots.indexOf(dataList.nodes[j].id);
                console.log(index);
                roots.splice(index, 1);
            }
        }
    }
    console.log(roots);
    return roots;
}

function stripTitle(title) {
  for (var i = 0; i < title.length; i++) {
    if (title[i] == "-") {
      title = title.slice(0, i);
      i = title.length+1;
    }
  }
  return title;
}