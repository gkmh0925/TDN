<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>ERROR</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/error.css" />
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<body>
<c:choose>
	<c:when test="${errCode eq 100 }">
		<script>
			swal({
				text: "세션이 만료되었습니다. 로그인 후 이용해주세요.",
				icon: "error"
			}).then(function() {
				if(self.opener){
					window.close();
				}else{
					location.replace("/");
				}
			});
		</script>
	</c:when>
	<c:when test="${errCode eq 101 }">
		<script>
			swal({
				text: "이미 접속중인 계정입니다. 로그아웃 후 이용해주세요.",
				icon: "error"
			}).then(function() {
				location.replace("/");
			});
		</script>
	</c:when>
	<c:otherwise>
		<script>
			swal({
				text: "비정상적인 접속이 감지되었습니다. 로그인 화면으로 이동합니다.",
				icon: "error"
			}).then(function() {
				location.replace("/"); 
			});
		</script>
	</c:otherwise>
</c:choose>
</body>
</html>