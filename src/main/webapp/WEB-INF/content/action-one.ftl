<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
         <title>Palindrome</title>
         <script src="javascript/jquery-1.7.2.js"></script>
         <script type="text/javascript">
             function doAjaxPost() {
                // get the form values
                var name = jQuery('#name').val();
                alert('Error: ' + name);

                jQuery.ajax({
                    type: "POST",
                    url: "/Palindrome/AjaxSubmit.do",
                    data: "name=" + name,
                    success: function(response){
                        // we have the response
                        jQuery('#info').html(response);
                    },
                    error: function(e){
                        alert('Error: ' + e);
                    }
                });
            }
        </script>
    </head>
    <body>
        Enter your name please : <input type="text" id="name"><br/>
        <input type="button" value="Submit" onclick="doAjaxPost()"><br/>
        <div id="info" style="color: green;"></div>
        <a href="index">Main page</a>
    </body>
</html>
