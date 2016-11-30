//Define some global variables
var fileName = "experimentInput/fullExperimentFile.json"; //fileName of JSON file to read in
var dataObject; //the object that one concept graph gets saved to when a student button is clicked
var objectsArray; //the object that the json file gets parsed to, an array of "student objects"
var names = []; //list of names of the students
var visualizationList = []; //the formatted list of data

//node object made to hold the root node information.
//used in findRoots
function RootNode(idIn, scoreIn, distIn){
    this.id = idIn;
    this.score = scoreIn;
    this.dist = distIn;
}

//reads in JSON file and parses it to objectsArray
function readJson(fileName){
 var request = new XMLHttpRequest();
   request.open("GET", fileName, false);
   request.send(null)

    objectsArray = JSON.parse(request.responseText);
}

//creates the student buttons
function writeMenu(){
    //creates the list of student names from the master objectsArray
    for(var i = 0; i < objectsArray.length; i++){
        names.push(objectsArray[i].name);
    }
    //defines a line of HTML to inject into the DOM
    //creates a list of button objects. The button click calls makeChart and passes the argument of the student's name
    //and a term "reg" or "avg" to tell whether the graph should display actualcomp or distfromavg
    var newCode = "<button class='accordion'>Section 1</button><div class='panel'><ul style='list-style: none;'>";
    newCode += "<li><button type='button' onclick='makeChart(&quot;" + names[0] + "&quot;,&quot;reg&quot;)'>" + names[0] + "</button>";
    for(var i = 1; i < names.length; i++){
        newCode += "<li><button type='button' onclick='makeChart(&quot;" + names[i] + "&quot;,&quot;reg&quot;)'>" + names[i] + "</button>";
        newCode +="<button type='button' onclick='makeChart(&quot;" + names[i] + "&quot;,&quot;avg&quot;)'>" + "DistAvg" + "</button></li>";
    }
    newCode += "</ul></div>";
    //insert the HTML code into the div with the ID "menu"
    document.getElementById("menu").innerHTML = newCode;
}

//takes the name of the student whose org chart should be drawn
function makeChart(currName,typeGraph){
    var currTitle = "";
    if(typeGraph == "reg"){
        currTitle += "<h3>"+currName+"</h3>"; 
        currTitle+="<figure><img src='scaleImg.png'><figcaption>Poor  -  Moderate  -  Good</figcaption></figure>"
    }else{
        currTitle += "<h3>"+currName + " Distance from Average Graph</h3>";
        currTitle+="<figure><img src='scaleImg.png'><figcaption>Below  -  Average  -  Above</figcaption></figure>"
    }  
    document.getElementById("title").innerHTML = currTitle;
    
    //initializes visualizationList
    visualizationList = [];
    //clears the "seciton" HTML
    document.getElementById("section").innerHTML = "";
    //iterates through the master array of objects and assigns the matching student object to dataObject
    for(var i = 0; i < objectsArray.length; i++){
        if(String(objectsArray[i].name) == currName){
            console.log("FOUND: "+objectsArray[i].name);
            dataObject = objectsArray[i].cg;
        }
    }
    
    //assigns roots of the tree to an array of RootNode objects
    var roots = findRoot(dataObject);
    //iterate through all of the roots and add them to the visualizationList
    for(var i = 0; i < roots.length; i++){
        var row1 = [];//make an empty row
        var c = roots[i].id;
        if(typeGraph == "reg"){
            var s = roots[i].score;
            row1.push({v:c, f:stripTitle(c)+'<div style="color:blue; font-style:italic">Score: '+s+'</div>'});//add the topic
        }else{
            var s = roots[i].dist;
            console.log(s);
            row1.push({v:c, f:stripTitle(c)+'<div style="color:blue; font-style:italic">Distance: '+s+'</div>'});//add the topic
        }
        row1.push(null);//add the parent, null for roots
        row1.push(s);//add the score
        visualizationList.push(row1);//push row to the list
    }
    
    //iterate through all of the links and add a row for each one
    for(var i = 0; i < dataObject.links.length; i++){
        var row = [];                              //make empty row
        var c = dataObject.links[i].child;         //def Topic
        
        var p = dataObject.links[i].parent;        //def parents
        
        //search through the list of nodes to find the node that correspondes with the child in the link
        for(var j = 0; j < dataObject.nodes.length; j++){
            //if the node ID matches the name in the link
            if(dataObject.nodes[j].id == dataObject.links[i].child){
                var q = stripQ(c);
                
                //def Score
                if(typeGraph == "reg"){
                    var s = dataObject.nodes[j].actualComp;
                    //add Topic (this is formatted to show the node ID without the iterative tag at the end and the score)
                    
        row.push({v:c, f:stripTitle(q)+'<div style="color:blue; font-style:italic">Score: '+s+'</div>'});
                }else{
                    var s = dataObject.nodes[j].distanceFromAvg;
                    //add Topic (this is formatted to show the node ID without the iterative tag at the end and the score)
                    if(q.indexOf("_Q") > 0){
        row.push({v:c, f:stripTitle(q)+'<div style="color:blue; font-style:italic"></div>'});
                    }else{
                        row.push({v:c, f:stripTitle(q)+'<div style="color:blue; font-style:italic">Distance: '+s+'</div>'});
                    }
                }
            }
        }
        row.push(p); //add parent
        row.push(s); //add score
        visualizationList.push(row); //push row to the list
    }
    
    //calls drawOrgChart from org.js, passing the formatted double array of data
    drawOrgChart(visualizationList);
}

//takes out any rows that are questions. (this was made so that we could look at visualizations with more ease but is not used now
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

//returns an array of the IDs of the roots of the tree
//takes in the dataObject informatin of one org chart
//NOTE: The reason that we iterate through the dataList.nodes list instead of just iterating through roots is because we cannot iterate through it and remove things from it at the same time.
function findRoot(dataList){
    //initialize roots
    var roots = []; 
    //add all of the node IDs to the roots array
    for(var i = 0; i < dataList.nodes.length; i++){
        roots.push(new RootNode(dataList.nodes[i].id, dataList.nodes[i].actualComp, dataList.nodes[i].distanceFromAvg));
    }
    //loop through all of the links
    for(var i = 0; i < dataList.links.length; i++){
        //loop through all of the nodes
        for(var j = 0; j < roots.length; j++){
            //current link's child is equal to the current node
            if(dataObject.links[i].child == roots[j].id){
                roots.splice(j, 1);
                j = roots.length+1;
            }
        }
    }
    return roots;
}

//returns a string that is everything before the "-" in a string
//takes a string, expecting format "Boolean-3" or "Boolean_Expressions-12"

function stripTitle(title) {
  for (var i = 0; i < title.length; i++) {
    if (title[i] == "-") {
      title = title.slice(0, i);
      return title;
    }
  }
    return "invalid node title";
}

function stripQ(c){
    var qIdx = c.indexOf(": description:");
    var endIdx = c.indexOf("-");
    var q;
    if(qIdx >= 0){
        var title = c.slice(0,qIdx);
        var end = c.slice(endIdx,c.length);
        q = title.concat("_Q");
        q = q.concat(end);
    }else{
        q = c;   
    }   
    return q;
}

//calls functions
readJson(fileName);
writeMenu();