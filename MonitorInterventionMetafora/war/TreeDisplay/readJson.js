var dataObject;
var visualizationList = [];

 var request = new XMLHttpRequest();
   request.open("GET", "input.json", false);
   request.send(null)
   
var dataObject = JSON.parse(request.responseText);

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
    row.push(dataObject.links[i].child);
    row.push(dataObject.links[i].parent);
    for(var j = 0; j < dataObject.nodes.length; j++){
        if(dataObject.nodes[j].id == dataObject.links[i].child){
            row.push(dataObject.nodes[i].actualComp);
        }
    }
    visualizationList.push(row);
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