<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Kennung</title>
    </head>
    <body>
        <h1>Datenverarbeitung</h1>
        <form method="POST" enctype="multipart/form-data">
            <h2>Dateninput</h2>
            <p>Kennung: <input type="text" name="id"></p>
            <p>Passwort: <input type="text" name="pass"></p>
            <p>Datei: <input type="file" name="data"</p>
            <p><button type="submit">Senden</button></p>
        </form>
        <form method="GET" action="csv.php">
            <h2>Datenoutput</h2>
            <p>Zeitraum: <input type="text" name="von" placeholder="YYYYMMDDhhmmss"> - <input type="text" name="bis" placeholder="YYYYMMDDhhmmss"></p>
            <p>Sensorengruppen: <select name="sengr"><?php
            $db = mysqli_connect('localhost', 'root', '' , 'verkehr');
            $sql = "SELECT SENSORGRUPPE from sensoren";
            mysqli_error($db);
            $result = mysqli_query($db, $sql);
            while($row = mysqli_fetch_assoc($result)) {
                echo '<option>';
                echo $row['SENSORGRUPPE'];
                echo '</option>';
            }
            mysqli_close($db);
            ?></select></p>
            <p>Gesamte DB Ausgeben: <input type="checkbox" name="all"></p>
            <p>Bei Gesamtausgabe Gruppe beachten: <input type="checkbox" name="type"></p>
            <p><button type="submit">Senden</button></p>
        </form>
        <pre>
        <?php
        if(!empty($_POST['id'])) {
            $db = mysqli_connect('localhost', 'root', '' , 'verkehr');
            mysqli_error($db);
            
            if (($handle = fopen($_FILES['data']['tmp_name'], "r")) !== FALSE) {
                while(($csv = fgetcsv($handle)) !== FALSE) {
                    $id = htmlspecialchars($_POST['id']);
                    $time = $csv['0'];
                    $data = $csv['1'];
                    $i = 1;
                    $sql = "INSERT INTO daten(SENSOR, GRUPPE, STIME, DATEN) SELECT '$id', SENSORGRUPPE, '$time', '$data' FROM sensoren WHERE NAME = '$id'";
                    $result = mysqli_query($db, $sql);
                    $i++;
                }
                mysql_close($db);
            
                }
            }
        ?>
        </pre>
    </body>
</html>
