<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/clever-website/css/bootstrap.min.css">
<link rel="stylesheet" href="/clever-website/css/archetypeSearch.css">
<link rel="stylesheet" href="/clever-website/css/bootstrap-theme.min.css">
<script src="/clever-website/js/jquery-2.0.3.min.js"></script>
<script src="/clever-website/js/bootstrap.min.js"></script>
<script src="/clever-website/js/archetypeSearch.js"></script>
</head>
<body>
	<div class="container" id="wholeContainer">
		<div class="row">
			<div class="col-lg-12">
				<ul class="nav nav-tabs">
					<li><a href="/clever-website">Home</a></li>
					<li class="active"><a href="#">Search</a></li>
					<li><a href="#">Messages</a></li>
				</ul>
			</div>
		</div>
		<div class="row" id="searchTopDiv">
			<div class="col-md-8 col-xs-8 col-xs-offset-2 col-md-offset-2"
				id="searchDiv">
				<div class="input-group">
					<input type="text" class="form-control" id="searchConditionInput">
					<span class="input-group-btn">
						<button class="btn btn-primary" type="button" id="searchButton">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8 col-xs-8 col-xs-offset-2 col-md-offset-2">
				<c:forEach var="archetypeBean" items="${archetypeBeanList}">
					<div class="panel-group" id="accordion-${archetypeBean.name}">
   						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion-${archetypeBean.name}" href="#${archetypeBean.name}">
										${archetypeBean.name}
									</a>
								</h4>
								<a href="/clever-website/archetypeDisplay?keyName=${archetypeBean.name}">浏览</a>
							</div>
							<div id="${archetypeBean.name}" class="panel-collapse collapse">
								<div id="descriptionDiv" class="panel-body">${archetypeBean.description}</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>