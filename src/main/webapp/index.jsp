<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Tree Structure Web Application</title>

    <style>
        html {
            margin: 0;
            padding: 0;
            font-size: 62.5%;
        }

        body {
            font-size: 14px;
            font-size: 1.4em;
        }

        h1 {
            font-size: 1.8em;
        }

        .demo {
            overflow: auto;
        }
    </style>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />
</head>

<body>
    <h1>Tree</h1>
    <div id="lazy" class="demo"></div>


    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.1/jquery.min.js">
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js">
    </script>
<p id="demolog"></p>

    <script>
        var idToCreate;
        $('#lazy').jstree({
                'core': {
                    'check_callback': true,
                    'data': {
                        'url': 'getnodechildren',
                        'data': function(node) {
                            return {
                                "id": node.id === '#' ? 'root' : node.id
                            };
                        }
                    }
                },
                'plugins': ['dnd', 'contextmenu', 'sort'],
                "contextmenu": {
                    "items": function($node) {
                        return {
                            "create": {
                                "separator_before": false,
                                "separator_after": false,
                                "label": "Create",
                                "action": function(data) {
                                    var ref = $.jstree.reference(data.reference);
                                    sel = ref.get_selected();
                                    if (!sel.length) {
                                        return false;
                                    }
                                    sel = sel[0];
                                    sel = ref.create_node(sel, {
                                        "type": "file"
                                    });
                                    if (sel) {
                                        ref.edit(sel);
                                    }
                                }
                            },
                            "delete": {
                                "separator_before": false,
                                "separator_after": false,
                                "label": "Delete",
                                "action": function(data) {
                                    var ref = $.jstree.reference(data.reference),
                                        sel = ref.get_selected();
                                    if (!sel.length) {
                                        return false;
                                    }
                                    ref.delete_node(sel);
                                }
                            },
                            "rename": {
                                "separator_before": false,
                                "separator_after": false,
                                "label": "Rename",
                                "action": function(data) {
                                    var inst = $.jstree.reference(data.reference),
                                        obj = inst.get_node(data.reference);
                                    inst.edit(obj);

                                }
                            }
                        };
                    }
                }
            }).bind("move_node.jstree", function(e, data) {
document.getElementById("demolog").innerHTML +=  data.node.id.substring(0,1)==="j" ? idToCreate : data.node.id + ", " + data.parent + " (idToCreate : " + idToCreate + ", data.node.id : " + data.node.id + ")<br>";
                $.post(
                    "movenode", {
                        "id": (data.node.id.substring(0,1)==="j" ? idToCreate : data.node.id),
                        "new_parent_id": data.parent
                    }
                ).fail(function(error) {
                    document.getElementById("demolog").innerHTML += "<b>ERROR WHILE MOVING NODE</b>"
                });
            })
            .bind("rename_node.jstree", function(e, data) {
                $.post(
                    "renamenode", {
                        "id": (data.node.id.substring(0,1)==="j" ? idToCreate : data.node.id) ,
                        "name": data.text
                    }
                ).fail(function(error) {
                    document.getElementById("demolog").innerHTML += "<b>ERROR WHILE RENAMING NODE</b>"
                });
		document.getElementById("demolog").innerHTML += "Try to rename : node #" + (data.node.id.substring(0,1)==="j" ? idToCreate : data.node.id)  + " to " + data.text + "<br>";
		document.getElementById("demolog").innerHTML +="LOG: data.node.id=" + data.node.id + ", idToCreate=" + idToCreate + "<br>";
            })
            .bind("delete_node.jstree", function(e, data) {
                $.post(
                    "deletenode", {
                        "id": (data.node.id.substring(0,1)==="j" ? idToCreate : data.node.id)
                    }
                ).fail(function(error) {
                    document.getElementById("demolog").innerHTML += "<b>ERROR WHILE DELETENG NODE</b>"
                });
            })
            .bind("create_node.jstree", function(e, data) {
                $.post(
                    "createnode", {
                        "name": data.node.text,
                        "parent" : data.parent
                    }
                ).fail(function(error) {
                    document.getElementById("demolog").innerHTML += "<b>ERROR WHILE CREATING NODE</b><br>"
                });
		document.getElementById("demolog").innerHTML += "Created node. Id: " + data.node.id + ", name: " + data.node.text + "<br>";
                $.getJSON("getlastcreatednode", function(obj) {
                    idToCreate = obj.id;
		    document.getElementById("demolog").innerHTML += "idToCreate (local): " + idToCreate + "<br>";
		    data.instance.set_id(node, idToCreate);
		    data.instance.edit(idToCreate);
                });
            });
    </script>
</body>