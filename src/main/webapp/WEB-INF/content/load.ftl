<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
         <meta name="viewport" content="width=device-width, initial-scale=1.0">
         <title>Palindrome</title>
         <style type="text/css">
            .notes {
                background-color:#DDFFDD;
                border:1px solid #009900;
                width:300px;
            }
            .notes li{ 
                list-style: none; 
            }
            div {
               text-align:center;
            }
        </style>
    </head>
    <body>
        <h4>Total score rank && Highest score rank</h4>
        <div>
            <a href="ranks.action">check!!</a>         
        </div>
        <h4>Your record</h4>
        <div>
            <@s.push value="user">
            <table>
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
        <div stye="">
        <@s.form action="submit.action">
          <@s.textfield name="palindromeString" label="Input a palindrome" size="30"/>
          <@s.submit value="Submit" />
        </@s.form>  
        </div>
    </body>
</html>
