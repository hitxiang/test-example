<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
         <title>Palindrome</title>
         <script src="javascript/jquery-1.7.2.js"></script>
         <script src="jquery.mobile/jquery.mobile-1.1.1.js"></script>
         <style type="text/css">
            .notes {
                background-color:#DDFFDD;
                border:1px solid #009900;
                width:300px;
            }
            .notes li{ 
                list-style: none; 
            }
        </style>
    </head>
    <body>
        <div>
            <h3>Total score rank && Highest score rank</h3>
            <div align="center">
            <a href="ranks">check!!</a>
            </div>          
        </div>
        <div>
            <h3>Your record</h3>
            <@s.push value="user">
            <table>
              <tr>
                <td>id: </td> 
                <td><@s.property value="id"/></td>
              </tr>
              <tr>
                <td>Your name    : </td> 
                <td>
                    <@s.if test="%{name == null}">
                        <div>
                        <a href="name">Register your name</a>
                        </div>
                    </@s.if>
                    <@s.else>
                        <div><@s.property value="name"/></div>
                    </@s.else>
                </td>
              </tr>
              <tr>
                <td>Last socre  : </td> 
                <td><@s.property value="lastScore"/></td>
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
        <@s.if test="hasActionMessages()">
           <div class="notes">
              <@s.actionmessage/>
           </div>
        </@s.if>
        <div>
        <@s.form action="submit">
          <@s.textfield name="palindromeString" label="Please input a palindrome" />
          <@s.submit value="Submit" />
        </@s.form>  
        </div>
    </body>
</html>
