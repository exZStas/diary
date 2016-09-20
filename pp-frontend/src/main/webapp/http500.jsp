<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Internal server error</title>
</head>
<style>
    .header{
        height: 90px;
        width:100%;
        position:absolute;
        background-color: #EE911F;
        color:#fff;
        font-family:Helvetica;
        position:fixed;
        z-index:999;
    }

    .content{
        position:relative;
        padding-left:30px;
        display:flex;
        top:100px;
        word-wrap:normal;
        overflow:auto;
        display:block;
    }

    .footer{
        height: 40px;
        width:100%;
        position:fixed;
        background-color: #999;
        color:#000;
        font-family:Helvetica;
        bottom:0px;
    }

    .footer p {
        margin-left:40px;
    }

    .header h1 {
        padding-top:20px;
        margin-left:40px;
    }
</style>
<body>
<div class="header">
    <h1>Internal server error</h1>
</div>
<div class="content">
    <h1>${param.header}</h1>
    <p> ${param.error} </p>
</div>
<div class="footer">
    <p>(C) Pazio 2016</p>
</div>

</body>
</html>
