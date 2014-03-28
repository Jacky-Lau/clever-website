<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/clever-website/css/bootstrap.min.css">
<link rel="stylesheet" href="/clever-website/css/archetypeSearch.css">
<link rel="stylesheet" href="/clever-website/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/clever-website/css/prettify.css">
<script src="/clever-website/js/jquery-2.0.3.min.js"></script>
<script src="/clever-website/js/jquery.hotkeys.min.js"></script>
<script src="/clever-website/js/bootstrap.min.js"></script>
<script src="/clever-website/js/bootstrap-wysiwyg.min.js"></script>
</head>
<body>
	<div class="container" id="wholeContainer">
	    <div class="row">
			<div class="col-lg-12">
				<ul class="nav nav-tabs">
					<li><a href="/clever-website">Home</a></li>
					<li><a href="/clever-website/archetypeSearch?condition=">Search</a></li>
					<li class="active"><a href="#">Messages</a></li>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				ARCHETYPE <br />
				<a href="/clever-website/fileDownload.action?keyName=${archetypeBean.name}&fileType=adl">
					${archetypeBean.name}
				</a>
				<br />				
				<textarea id="archetypeDiv" cols="160" rows="50">
					${archetypeBean.content}
				</textarea>
				<br />
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				ARM <br />
				<a href="/clever-website/fileDownload.action?keyName=${armBean.name}&fileType=arm">
					${armBean.name}
				</a>
				<br />
				<textarea id="armDiv" cols="160" rows="50">
					${armBean.content}
				</textarea>
			</div>
		</div>
	</div>
</body>
</html>