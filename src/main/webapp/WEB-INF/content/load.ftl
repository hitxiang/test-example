<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
         <title>Palindrome</title>
         <script src="javascript/jquery-1.7.2.js"></script>
         <script src="jquery.mobile/jquery.mobile-1.1.1.js"></script>
    </head>
    <body>
        <div>
            <h3>Your record</h3>
            <@s.push value="#session[@com.playfish.palindrome.model.User@IDENTIFY_TAG]">
            <table>
              <tr>
                <td>id: </td> 
                <td><@s.property value="id"/></td>
              </tr>
              <tr>
                <td>Your name    : </td> 
                <td><@s.property value="name"/></td>
              </tr>
              <tr>
                <td>Total socre  : </td> 
                <td><@s.property value="totalScore"/></td>
              </tr>
              <tr>
                <td>Highest score: </td> 
                <td><@s.property value="highestScore"/></td>
              </tr>             
            </table>
            </@s.push>
        </div>
        <br/>
        <br/>
        <div>
        <@s.form action="submit">
          <@s.textfield name="palindromeString" label="Please input a palindrome" />
          <@s.submit value="Submit" />
        </@s.form>  
        </div>
    </body>
</html>
