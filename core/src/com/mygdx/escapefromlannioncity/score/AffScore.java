package com.mygdx.escapefromlannioncity.score;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AffScore {
    public static boolean AffScore(){
        try {
            Path path = Paths.get(System.getProperty("user.home")+"/Public/EFLC/Score/");
            Files.createDirectories(path);
            System.out.println("Directory is created!");
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());

        }
        try {
            File f = new File(System.getProperty("user.home")+"/Public/EFLC/Score/index.html");
            if (!f.isFile()) {
                System.out.println("Création de index.html");
                FileWriter file = new FileWriter(System.getProperty("user.home")+"/Public/EFLC/Score/index.html");
                file.write("<!doctype html>\n" +
                        "<html>\n" +
                        "    <head>\n" +
                        "        <title>Escape From Lannion City</title>\n" +
                        "        <style>\n" +
                        "            #but{\n" +
                        "                width:335px;\n" +
                        "                margin: auto;\n" +
                        "            }\n" +
                        "            button{\n" +
                        "                border: 0;\n" +
                        "                background: #4CAFA1;\n" +
                        "                color: white;\n" +
                        "                border-radius: 4px;\n" +
                        "                font-size: 1em;\n" +
                        "                font-weight: 500;\n" +
                        "                height: 2.5em;\n" +
                        "                line-height: 2.5;\n" +
                        "                padding: 0 1em;\n" +
                        "                min-width: 10em;\n" +
                        "                text-align: center;\n" +
                        "                cursor: pointer;\n" +
                        "                outline: none;\n" +
                        "                text-decoration: none;\n" +
                        "                transform: translateY(0);\n" +
                        "                transition: all .15s ease;\n" +
                        "            }\n" +
                        "            h2,h3{text-align: center;}\n" +
                        "\n" +
                        "            table {\n" +
                        "                font-family: Arial, Helvetica, sans-serif;\n" +
                        "                border-collapse: collapse;\n" +
                        "                width: 90%;\n" +
                        "                margin: auto;\n" +
                        "            }\n" +
                        "\n" +
                        "            table th {\n" +
                        "                border: 1px solid #ddd;\n" +
                        "\n" +
                        "            }\n" +
                        "\n" +
                        "            tr:nth-child(even){background-color: #f2f2f2;}\n" +
                        "\n" +
                        "            tr:hover {background-color: #ddd;}\n" +
                        "            tr{text-align: center;}\n" +
                        "            th {\n" +
                        "                padding-top: 12px;\n" +
                        "                padding-bottom: 12px;\n" +
                        "                text-align: center;\n" +
                        "                background-color: #4CAFA1;\n" +
                        "                color: white;\n" +
                        "            }\n" +
                        "            #toTop {\n" +
                        "  padding: .5em 1em;\n" +
                        "  width: auto;\n" +
                        "  display: none;\n" +
                        "  text-align: center;\n" +
                        "  position: fixed;\n" +
                        "  bottom: 20px;\n" +
                        "  right: 30px;\n" +
                        "  z-index: 99;\n" +
                        "  color: white;\n" +
                        "  font-size: 18px;\n" +
                        "  border: 0;\n" +
                        "  border-radius: 4px;\n" +
                        "  outline: none;\n" +
                        "  cursor: pointer;\n" +
                        "  text-decoration: none;\n" +
                        "  background: #4CAFA1;\n" +
                        "  box-shadow: 0 4px 6px rgba(0, 0, 0, .1), 0 1px 3px rgba(0, 0, 0, .08);\n" +
                        "  transform: translateY(0);\n" +
                        "  transition: all .15s ease;\n" +
                        "}\n" +
                        "\n" +
                        "#toTop:hover {\n" +
                        "  background-color: #064508;\n" +
                        "}\n" +
                        "        </style>\n" +
                        "        <script type=\"text/javascript\" src=\"Local.js\"></script>\n" +
                        "        <script type=\"text/javascript\" src=\"Tableau.js\"></script>\n" +
                        "        <script type=\"text/javascript\">\n" +
                        "            function Tab(arr,id){\n" +
                        "              /*  if( data[0]!=null){\n" +
                        "                    var arr= new Array(JSON.parse(data[0]));}\n" +
                        "                for (var i = 1; i < data.length; i++) {\n" +
                        "                    arr.push(JSON.parse(data[i]));\n" +
                        "                }*/\n" +
                        "\n" +
                        "                var table = document.createElement(\"table\"), row, cellA, cellB, cellC, cellD, cellE;\n" +
                        "                document.getElementById(id).appendChild(table);\n" +
                        "\n" +
                        "                row = document.createElement(\"tr\");\n" +
                        "                cellA = document.createElement(\"th\");\n" +
                        "                cellB = document.createElement(\"th\");\n" +
                        "                cellC = document.createElement(\"th\");\n" +
                        "                cellD = document.createElement(\"th\");\n" +
                        "                cellE = document.createElement(\"th\");\n" +
                        "                // (C3) KEY & VALUE\n" +
                        "                cellA.innerHTML = \"rang\";\n" +
                        "                cellB.innerHTML = \"pseudo\";\n" +
                        "                cellC.innerHTML = \"score\";\n" +
                        "                cellD.innerHTML = \"temps\";\n" +
                        "                cellE.innerHTML = \"date\";\n" +
                        "\n" +
                        "                table.appendChild(row);\n" +
                        "                row.appendChild(cellA);\n" +
                        "                row.appendChild(cellB);\n" +
                        "                row.appendChild(cellC);\n" +
                        "                row.appendChild(cellD);\n" +
                        "                row.appendChild(cellE);\n" +
                        "                \n" +
                        "                arr.sort((a, b) => parseFloat(b.score) - parseFloat(a.score));\n" +
                        "\n" +
                        "                for (var i = 0; i < arr.length; i++) {\n" +
                        "                    // (C2) ROWS & CELLS\n" +
                        "                    row = document.createElement(\"tr\");\n" +
                        "                    cellA = document.createElement(\"td\");\n" +
                        "                    cellB = document.createElement(\"td\");\n" +
                        "                    cellC = document.createElement(\"td\");\n" +
                        "                    cellD = document.createElement(\"td\");\n" +
                        "                    cellE = document.createElement(\"td\");\n" +
                        "                    // (C3) KEY & VALUE\n" +
                        "                    cellA.innerHTML = (i+1).toString();\n" +
                        "                    cellC.innerHTML = arr[i].score;\n" +
                        "                    cellB.innerHTML = arr[i].pseudo;\n" +
                        "                    cellD.innerHTML = arr[i].temps;\n" +
                        "                    cellE.innerHTML = arr[i].date;\n" +
                        "                    // (C4) ATTACH ROW & CELLS\n" +
                        "                    table.appendChild(row);\n" +
                        "                    row.appendChild(cellA);\n" +
                        "                    row.appendChild(cellB);\n" +
                        "                    row.appendChild(cellC);\n" +
                        "                    row.appendChild(cellD);\n" +
                        "                    row.appendChild(cellE);\n" +
                        "                    // alert(JSON.parse(data[0]).score);\n" +
                        "                    //alert(mydata[1]);\n" +
                        "                }\n" +
                        "            }\n" +
                        "        function load() {\n" +
                        "            //var mydata = JSON.parse(data);\n" +
                        "            //data = data.replace(/\\\\/g, '');\n" +
                        "           if(typeof Local !== 'undefined'){\n" +
                        "               if( Local[0]!=null){\n" +
                        "                   var arr= new Array(JSON.parse(Local[0]));}\n" +
                        "            for (var i = 1; i < Local.length; i++) {\n" +
                        "                arr.push(JSON.parse(Local[i]));\n" +
                        "            }\n" +
                        "               Tab(arr,\"tableau\");\n" +
                        "           }else{\n" +
                        "               alert(\"Pas encore de score sur cet ordinateur\")\n" +
                        "           }\n" +
                        "           if(typeof Tableau !== 'undefined'){\n" +
                        "               Tab(Tableau,\"tableau2\")\n" +
                        "           }else{\n" +
                        "               alert(\"Le classement général n'a pas été téléchargé\")\n" +
                        "           }\n" +
                        "\n" +
                        "        }\n" +
                        "            function Loc(){\n" +
                        "                var y= document.getElementById(\"tableau\");\n" +
                        "                var x= document.getElementById(\"tableau2\");\n" +
                        "                if(x.style.display==\"none\"){\n" +
                        "                    x.style.display=\"block\";\n" +
                        "                    y.style.display = \"none\"\n" +
                        "                }else{\n" +
                        "                    x.style.display=\"none\";\n" +
                        "                    y.style.display = \"block\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "\n" +
                        "            function Gen(){\n" +
                        "                var x= document.getElementById(\"tableau\");\n" +
                        "                var y= document.getElementById(\"tableau2\");\n" +
                        "                if(x.style.display==\"none\"){\n" +
                        "                    x.style.display=\"block\";\n" +
                        "                    y.style.display = \"none\"\n" +
                        "                }else{\n" +
                        "                    x.style.display=\"none\";\n" +
                        "                    y.style.display = \"block\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "  // When the user scrolls down 20px from the top of the document, show the button\n" +
                        "window.onscroll = function() {\n" +
                        "  scrollFunction();\n" +
                        "};\n" +
                        "\n" +
                        "function scrollFunction() {\n" +
                        "  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {\n" +
                        "    document.getElementById(\"toTop\").style.display = \"block\";\n" +
                        "  } else {\n" +
                        "    document.getElementById(\"toTop\").style.display = \"none\";\n" +
                        "  }\n" +
                        "}\n" +
                        "// When the user clicks on the button, scroll to the top of the document\n" +
                        "function topFunction() {\n" +
                        "  document.body.scrollTop = 0;\n" +
                        "  document.documentElement.scrollTop = 0;\n" +
                        "}\n" +
                        "        </script>\n" +
                        "    </head>\n" +
                        "    <body onload=\"load()\">\n" +
                        "    <h2>Tableau des scores</h2>\n" +
                        "    </br>\n" +
                        "    <div id=\"but\">\n" +
                        "        <button onclick=\"Loc()\">Mes scores</button>\n" +
                        "        <button onclick=\"Gen()\">Classment général</button>\n" +
                        "    </div>\n" +
                        "    </br>\n" +
                        "    <div id=\"tableau\">\n" +
                        "        <h3>Mes Scores</h3>\n" +
                        "    </div>\n" +
                        "    <div id=\"tableau2\">\n" +
                        "        <h3>Classement général</h3>\n" +
                        "    </div>\n" +
                        "    \n" +
                        "    \n" +
                        "      <span onclick=\"topFunction()\" id=\"toTop\" title=\"Go to top\">Top</span>\n" +
                        "    </body>\n" +
                        "</html>");
                file.flush();
                file.close();
            }
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(f.toURI());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Failed to create or open index.html" + e.getMessage());
            return false;
        }
    }
}
