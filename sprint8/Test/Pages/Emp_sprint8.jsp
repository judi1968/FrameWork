<%
    String a = (String)request.getAttribute("Reps");
    String b = (String)request.getAttribute("Parametre1");  
    
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>sprint 8</title>
    <link rel="stylesheet" href="./../../../../assets/style.css">

</head>
<body>
    <h1>Sprint 8</h1>
    <p><%= a %></p>
    <p><%= b %></p>    
</body>
</html>