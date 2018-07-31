<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Tree Structure Web Application</title>

	<style>
	html { margin:0; padding:0; font-size:62.5%; }
	body { font-size:14px; font-size:1.4em; }
	h1 { font-size:1.8em; }
	.demo { overflow:auto; }
	</style>

	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />
</head>
<body>
	<h1>Tree</h1>
	<div id="lazy" class="demo"></div>


	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>


	<script>

	// lazy demo
	$('#lazy').jstree({
		'core' : {
			'data' : {
				"url" : "getnodechildren",
				'data': function (node) {
				return { "id" : node.id === '#' ? 'root' : node.id };
                }
			}
		}
	});
	</script>
</body>