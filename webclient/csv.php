<?php
        $von = htmlspecialchars($_GET['von']);
            $bis = htmlspecialchars($_GET['bis']);
            $sensorgroup = htmlspecialchars($_GET['sengr']);
        if($_GET['all'] == 'on' && $_GET['type'] == 'on') {
            if($_GET['all'] == 'on'){
            $db = mysqli_connect('localhost', 'root', '' , 'verkehr');
            $sql = "SELECT SENSOR, STIME, DATEN, GRUPPE from daten where GRUPPE = '$sensorgroup' ORDER BY STIME DESC";
            mysqli_error($db);
            $result = mysqli_query($db, $sql);
            while($row = mysqli_fetch_assoc($result)) {
                echo $row['SENSOR'];
                echo ',';
                echo $row['STIME'];
                echo ',';
                echo $row['DATEN'];
                echo "\n";
            }
            mysqli_close($db);
            }
        } elseif($_GET['all'] == 'on'){
            $db = mysqli_connect('localhost', 'root', '' , 'verkehr');
            $sql = "SELECT SENSOR, STIME, DATEN, GRUPPE from daten ORDER BY STIME DESC";
            mysqli_error($db);
            $result = mysqli_query($db, $sql);
            while($row = mysqli_fetch_assoc($result)) {
                echo $row['SENSOR'];
                echo ',';
                echo $row['GRUPPE'];
                echo ',';
                echo $row['STIME'];
                echo ',';
                echo $row['DATEN'];
                echo "\n";
            }
            mysqli_close($db);
        } else
            {
            
            $db = mysqli_connect('localhost', 'root', '' , 'verkehr');
            $sql = "SELECT SENSOR, STIME, DATEN from daten where STIME >= '$von' AND STIME <= '$bis' AND GRUPPE = '$sensorgroup' ORDER BY STIME DESC";
            mysqli_error($db);
            $result = mysqli_query($db, $sql);
            while($row = mysqli_fetch_assoc($result)) {
                echo $row['SENSOR'];
                echo ',';
                echo $row['STIME'];
                echo ',';
                echo $row['DATEN'];
                echo "\n";
            }
            mysqli_close($db);
        }
        
?>