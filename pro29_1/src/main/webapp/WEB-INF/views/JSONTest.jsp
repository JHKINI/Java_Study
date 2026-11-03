<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(function() {
            $("#checkJson").click(function() {
                var member = {
                    id: "park",
                    name: "박지성",
                    pwd: "1234",
                    email: "park@test.com"
                };

                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/test/info",
                    contentType: "application/json",
                    data: JSON.stringify(member),
                    success: function(data, textStatus) {
                    },
                    error: function(data, textStatus) {
                        alert("에러가 발생했습니다.");
                    },
                    complete: function(data, textStatus) {
                    }
                });
            });
        });
    </script>
</head>
<body>
    <input type="button" id="checkJson" value="회원 정보 보내기"/><br><br>
    <div id="output"></div>
</body>
</html>
