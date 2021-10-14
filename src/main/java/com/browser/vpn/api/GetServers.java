package com.browser.vpn.api;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GetServers {
    public static void main(String[] args) throws IOException, CsvException {
        new GetServers().csvdownload();
    }
    public String ips;
    public String land;
    public String comp;
    public void csvdownload() throws IOException, CsvException {
        File f = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "download.csv");
        if(!f.exists()) {
            InputStream in = new URL("http://www.vpngate.net/api/iphone/").openStream();
            Files.copy(in, Paths.get("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "download.csv"), StandardCopyOption.REPLACE_EXISTING);
        }
        readCSV();
    }
    String out;
    boolean firsts = false;
    String outs = "";
    private void readCSV() throws IOException, CsvException {
        String fileName = "C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "download.csv";
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> out = out + "\n" + Arrays.toString(x));
        }
        //out = out.replace("wWHRVQVoyd1ZxM09SVHlHbnNNV3JlOXRMWXM1NVgrWk4xMFRjNzV6DQo0aGFjYlUwaHFLTjFIaURtc01SWTMvMk5hWkhveTdNS253SkpCYUc0OGw5Q0NUbFZ3TUhvY0lFQ2dZRUE4amJ5DQpkR2p4VEgrNlhIV05pemI1U1JiWnhBbnlFZUplUndUTWgwZ0d6d0dQcEgvc1pZR3p5dTBTeVNYV0NuWmgzUmdxDQo1dUxsTnh0clhybGpabHlpMm5RZFFnc3EyWXJXVXMwK3pnVSsyMnVRc1pwU0FmdG1oVnJ0dmV0Nk1qVmpiQnlZDQpEQURjaUVWVWRKWUlYaytxbkZVSnllcm9MSWtUajdXWUtaNlJqa3NDZ1lCb0NGSXdSRGVnNDJvSzg5UkZtbk9yDQpMeW1OQXE0KzJvTWhzV2xWYjRlaldJV2VBazluYytHWFVmclhzelJoUzAxbVVuVTVyNXlnVXZSY2FyVi9UM1U3DQpUbk1aK0k3WTREZ1dSSURkNTF6bmh4SUJ0WVY1ai9DL3Q4NUhqcU9rSCs4YjZSVGtiY2hhWDNtYXU3ZnBVZmRzDQpGcTBuaElxNDJmaEVPOHNyZllZd2dRS0JnUUN5aGkxTi84dGFSd3BrKzMvSURFelF3amJmZHpVa1dXU0RrOVhzDQpIL3BrdVJIV2ZUTVAzZmxXcUVZZ1cvTFc0MHBlVzJIRHE1aW1kVjgrQWdaeGUvWE1iYWppOUxnd2YxUlkwMDVuDQpLeGFaUXo3eXFIdXBXbExHRjY4RFBIeGtaVlZTYWdEblYvc3p0V1g2U0ZzQ3FGVm54SVhpZlhHQzRjVzVObTlnDQp2YThxNFFLQmdRQ0VoTFZlVWZkd0t2a1o5NGcvR0Z6NzMxWjJocmRWaGdNWmFVL3U2dDBWOTUrWWV6UE5DUVpCDQp3bUU5TW1sYnExZW1EZVJPaXZqQ2ZvR2hSM2taWFcxcFRLbExoNlpNVVFVT3BwdGRYdmE4WHhmb3FRd2EzZW5BDQpNN211QmJGMFhON1ZPODBpSlB2K1BtSVpkRUlBa3B3S2ZpMjAxWUIrQmFmQ0l1R3hJRjUwVmc9PQ0KLS0tLS1FTkQgUlNBIFBSSVZBVEUgS0VZLS0tLS0NCg0KPC9rZXk+DQoNCg==]", "");
        String[] convert = out.split("]");
        boolean first = false;
        int that = 0;
        String th = "";
        for (String s: convert) {
            if(that==1) {
                th = th + s;
            }else{
                that=that+1;
            }
        }
        String[] conv = th.split("\\[");
        //Ab [2] wird der output korrekt
        String co = conv[2];
        String[] tha = {};
        int eins = 0;
        tha = co.split(",");
        for (String fs: conv) {
            try {
                String[] nach = fs.split(",");
                if (eins > 2) {
                    if (ips == "") {
                        ips = nach[0];
                        land = nach[5];
                        comp = nach[0] + " - " + nach[5] + new Random().nextInt(199) + ",";
                    } else {
                        ips = ips + " " + nach[0];
                        land = land + " " + nach[5];
                        comp = comp + "  " + nach[0] + " - " + nach[5] + "" + new Random().nextInt(199) + ",";
                    }
                }
                eins = eins + 1;
            }catch (ArrayIndexOutOfBoundsException aiiobe) {
                break;
            }
        }
        ips = ips.replace("*", "");
        //System.out.println(ips);
        conv = th.split(",");
        //System.out.println(th);
    }
    public String getAdded() throws IOException {
        Process process = Runtime.getRuntime().exec("powershell Get-VpnConnection");
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        try {
            while ((line = input.readLine()) != null) {
                if(line.contains("Name")) {
                    firsts = true;
                }
                if(firsts) {
                    if(outs=="") {
                        outs = line;
                    }else{
                        outs = outs + "\n" + line;
                    }
                }
                firsts = false;
            }
        }catch (IOException es) {

        }
        return outs.replaceAll("Name                  : ", "[");
    }
}
