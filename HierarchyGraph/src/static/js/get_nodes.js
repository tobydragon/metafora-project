/**
 * Created by David on 10/5/15.
 */

function get_nodes(nodes, edges, current) {

    // Gather up students into list for current node
    var students = [],
        j = 0;
    for(; j< current.students.length; j++) {
        students.push(current.students[j]);
    }

    // make current node
    nodes.push({
        "title": current.title,
        "numAsses": current.numAsses,
        "false": current.false,
        "time": current.time,
        "objectIds": current.objectIds,
        "students": students
    });
    // check if we have children
    if (current.children.length == 0) {
        return;
    }

    // make edges between current (parent) and all of its children... based off ids
    var i = 0;
    for (; i < current.children.length; i++) {
        get_nodes(nodes, edges, current.children[i]);
        edges.push([current.title, current.children[i].title])
    }

}
