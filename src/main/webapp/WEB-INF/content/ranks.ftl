<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
         <meta name="viewport" content="width=device-width, initial-scale=1.0">   
         <meta name="format-detection" content="telephone=no">      
         <title>Register</title>
         <style type="text/css">
         .rank1 {
           background-color:#ffd700; 
           text-align:center;
         }
         
         .rank2 {
           background-color:#c9c9c9; 
           text-align:center;
         }
         
         .rank3 {
           background-color:#cc9966; 
           text-align:center;
         }
         
         .rank4 {
           text-align:center;
         }         

         .rank5 {
           text-align:center;
         }

         img {
             max-width: 60%;
             height: auto;
         }
         
         div {
           text-align:center;
         }
         </style>
    </head>
    <body>
        <div>
            <h3>Highest Score Rank</h3>
            <table>
               <tr>
                 <th width="10%">Rank</th>
                 <th width="30%">Score</th>
                 <th width="60%" style="text-align:left;">Name</th>
               </tr>
               <@s.iterator value="highestRanks" status="stat">
               <tr>
                 <td class='rank<@s.property value="#stat.count"/>'><@s.property value="#stat.count"/></td>
                 <td style="text-align:center;"><@s.property value="score"/></td>
                 <td style="text-align:left;"><@s.property value="name" /></td>
               </tr>
               </@s.iterator>
            </table>
        </div>
        <br/>
        <div>
            <h3>Total Score Rank</h3>
            <table>
               <tr>
                 <th width="10%">Rank</th>
                 <th width="30%">Score</th>
                 <th width="60%" style="text-align:left;">Name</th>
               </tr>
               <@s.iterator value="totalRanks" status="stat">
               <tr>
                 <td class='rank<@s.property value="#stat.count"/>'><@s.property value="#stat.count"/></td>
                 <td style="text-align:center;"><@s.property value="score"/></td>
                 <td style="text-align:left;"><@s.property value="name" /></td>
               </tr>
               </@s.iterator>
            </table>
        </div>  
        <br/>
        <div align="center">
          <a href="load"><img src="images/challenge.jpeg"></a>
        </div>      
    </body>
</html>
