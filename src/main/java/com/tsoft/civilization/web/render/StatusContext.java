package com.tsoft.civilization.web.render;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class StatusContext {

    private static final String HTML_PAGE =
        """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>World Status</title>
                <style>
                    /* Style the table */
                    #tiles-table {
                      font-family: Arial, Helvetica, sans-serif;
                      border-collapse: collapse;
                      width: 100%;
                    }
                    
                    #tiles-table td, #tiles th {
                      border: 1px solid #ddd;
                      padding: 8px;
                    }
                    
                    #tiles-table td:hover { background-color: #ddd; }
                    
                    #tiles-table th {
                      padding-top: 12px;
                      padding-bottom: 12px;
                      text-align: left;
                      background-color: #4CAF50;
                      color: white;
                    }
                    
                    /* Style the tab */
                    .tab {
                      overflow: hidden;
                      border: 1px solid #ccc;
                      background-color: #f1f1f1;
                    }
                    
                    /* Style the buttons that are used to open the tab content */
                    .tab button {
                      background-color: inherit;
                      float: left;
                      border: none;
                      outline: none;
                      cursor: pointer;
                      padding: 14px 16px;
                      transition: 0.3s;
                    }
                    
                    /* Change background color of buttons on hover */
                    .tab button:hover {
                      background-color: #ddd;
                    }
                    
                    /* Create an active/current tablink class */
                    .tab button.active {
                      background-color: #ccc;
                    }
                    
                    /* Style the tab content */
                    .tabcontent {
                      display: none;
                      padding: 6px 12px;
                      border: 1px solid #ccc;
                      border-top: none;
                    }
                </style>
            </head>
            <body>
                <img src="___IMAGE___" />
                
                <div class="tab">
                  <button class="tablinks" onclick="openCity(event, 'Tiles')">Tiles</button>
                  <button class="tablinks" onclick="openCity(event, 'Units')">Units</button>
                  <button class="tablinks" onclick="openCity(event, 'Cities')">Cities</button>
                </div>
                        
                <div id="Tiles" class="tabcontent">
                  <p>___TILES___</p>
                </div>
                        
                <div id="Units" class="tabcontent">
                  <p>___UNITS___</p>
                </div>
                        
                <div id="Cities" class="tabcontent">
                  <p>___CITIES___</p>
                </div>
                
                <script>
                    function openCity(evt, cityName) {
                        // Declare all variables
                        var i, tabcontent, tablinks;
                    
                        // Get all elements with class="tabcontent" and hide them
                        tabcontent = document.getElementsByClassName("tabcontent");
                        for (i = 0; i < tabcontent.length; i++) {
                          tabcontent[i].style.display = "none";
                        }
                    
                        // Get all elements with class="tablinks" and remove the class "active"
                        tablinks = document.getElementsByClassName("tablinks");
                        for (i = 0; i < tablinks.length; i++) {
                          tablinks[i].className = tablinks[i].className.replace(" active", "");
                        }
                    
                        // Show the current tab, and add an "active" class to the button that opened the tab
                        document.getElementById(cityName).style.display = "block";
                        evt.currentTarget.className += " active";
                    }            
                </script>
            </body>
            </html>
            """;

    private String imageFileName = "";
    private String tiles = "";
    private String units = "";
    private String cities = "";

    public void setImageFileName(Path imageFileName) {
        this.imageFileName = imageFileName.getFileName().toString();
    }

    public void setTiles(String tiles) {
        this.tiles = tiles;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public void saveHtmlToFile(Path outputFileName) {
        String page = HTML_PAGE;
        page = page.replace("___IMAGE___", imageFileName);
        page = page.replace("___TILES___", tiles);
        page = page.replace("___UNITS___", units);
        page = page.replace("___CITIES___", cities);

        try {
            Files.writeString(outputFileName, page);
        } catch (IOException e) {
            log.error("Error writing to a file {}: {}", outputFileName.toFile().getAbsolutePath(), e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
