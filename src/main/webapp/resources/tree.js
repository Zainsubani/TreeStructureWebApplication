$('#lazy').jstree({
    'core': {
        "themes": {
            "name": "proton",
            "dots": true,
            "icons": true
        },
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
    $.post(
        "movenode", {
            "id": data.node.id,
            "new_parent_id": data.parent
        }
    );
})
.bind("rename_node.jstree", function(e, data) {
    $.post(
        "renamenode", {
            "id": (data.node.id),
            "name": data.text
        }
    );
})
.bind("delete_node.jstree", function(e, data) {
    $.post(
        "deletenode", {
            "id": (data.node.id)
        }
    );
})
.bind("create_node.jstree", function(e, data) {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "createnode",
        data: {
            name: data.node.text,
            parent: data.parent
        },
        success: function(resultData) {
            var id = resultData.id;
            data.instance.set_id(data.node, id);
            data.instance.edit(id);
        }
    });
});