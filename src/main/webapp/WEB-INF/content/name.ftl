<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
         <meta name="viewport" content="width=device-width, initial-scale=1.0">
         <title>Register</title>
    </head>
    <body>
        <@s.form action="register">
          <@s.textfield name="name" label="Enter your name please :" />
          <@s.submit value="Submit" />
        </@s.form>
        <a href="index">Main page</a>
    </body>
</html>
