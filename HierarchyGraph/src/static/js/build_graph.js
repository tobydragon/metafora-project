/**
 * Created by David on 10/5/15.
 */

function build_graph() {

    var graph = Viva.Graph.graph(),
        graphics = Viva.Graph.View.svgGraphics(),
        nodeSize = 30,
        edges = bookCustomOne.links,
        nodes = bookCustomOne.nodes;
    for (j = 0; j < nodes.length; j++) {
        console.log(Math.random());
        graph.addNode(nodes[j]["concept"]["conceptTitle"],
            {
                "title": nodes[j]["concept"]["conceptTitle"],
                "time": nodes[j]["summaryInfo"]["time"],
                "objectIds": nodes[j]["summaryInfo"]["objectId"],
                "false" : nodes[j]["summaryInfo"]['totalFalseEntries'],
                "color" : nodes[j]["level"],
                "students": nodes[j]["summaryInfo"]["users"],
                "aptitude": nodes[j]["actualComp"],
                "pred": nodes[j]["predictedComp"]
            }
        );
    }

    for (k = 0; k < edges.length; k++) {
        graph.addLink(edges[k]["parent"]["concept"]["conceptTitle"], edges[k]["child"]["concept"]["conceptTitle"]);
    }

    graphics.node(function (node) {
        var ui = Viva.Graph.svg('g'),
            img = Viva.Graph.svg('image')
                .attr('width', nodeSize)
                .attr('height', nodeSize)
                .link(getColor(node));

        $(ui).click(function () {
            $('#myModal').modal('show');
            document.getElementById("myModalLabel").innerHTML = node.data.title;
            //document.getElementById("numAsses").innerHTML = "Number of Assessments = " + node.data.numAsses;
            document.getElementById("false").innerHTML = "Number of False Attempts = " + node.data.false;
            document.getElementById("time").innerHTML = "Total time taken = " + node.data.time;
            document.getElementById("actual").innerHTML = "Actual score = " + node.data.aptitude;
            document.getElementById("pred").innerHTML = "Predicted score = " + node.data.pred;
            document.getElementById("students").innerHTML = "Students = " + setStudents(node);
        });

        ui.append(img);
        return ui;
    }).placeNode(function (nodeUI, pos) {
        nodeUI.attr('transform',
            'translate(' +
            (pos.x - nodeSize / 2) + ',' + (pos.y - nodeSize / 2) +
            ')');
    });

    function getColor(node) {
        color = String(node.data.color);
        aptitude = (node.data.aptitude);
        if (aptitude >= 0 && aptitude < .25) {
            return 'static/images/red' +  color+'.jpg';
        }
        if (aptitude >= .25 && aptitude < .5) {
            return 'static/images/orange' +  color+'.jpg';
        }
        if (aptitude >= .5 && aptitude < .75) {
            return 'static/images/yellow' +  color+'.jpg';
        }
        if (aptitude >= .75 && aptitude <= 1) {
            return 'static/images/green' +  color+'.jpg';
        }


    }

    function setStudents(node) {
        var students = parseInt(node.data.students.length);
        var studentsString = "";
        for (k =0;  k <students; k++) {
            studentsString += (node.data.students[k] + "\n");
            console.log(node.data.students[k]);
        }
        return studentsString;
    }
    // Render the graph
    var renderer = Viva.Graph.View.renderer(graph, {
        graphics: graphics
    });
    renderer.run();

}
