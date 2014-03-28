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
<script src="/clever-website/js/serviceControl.js"></script>
</head>
<body>
	<div class="container" id="wholeContainer">
		<div class="row" id="searchTopDiv">
			<div class="col-md-8 col-xs-8 col-xs-offset-2 col-md-offset-2"
				id="searchDiv">
				<div class="input-group">
					<button class="btn btn-primary" type="button" id="searchButton">Restart</button>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8 col-xs-8 col-xs-offset-2 col-md-offset-2">
				<c:forEach var="singleId" items="${archetypeIdList}">
					<div class="panel-group" id="accordion-${singleId}">
   						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion-${singleId}" href="#${singleId}">
										${singleId}
									</a>
								</h4>
								<a href="/clever-website/archetypeDisplay?keyName=${singleId}">浏览</a>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>