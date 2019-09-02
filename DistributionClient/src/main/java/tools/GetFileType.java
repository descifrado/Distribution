package tools;

import constants.FileType;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetFileType
{
    private FileType ft=FileType.OTHER;
    public FileType getFileType(String path)
    {
        String extra[]=path.split("/");
        String fileName=extra[extra.length-1];
        System.out.println(fileName);
        String s;
        Process p;
        String output="";
        try
        {
            p = Runtime.getRuntime().exec("file "+path);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                output+=s;
            p.waitFor();
            p.destroy();
            output=output.split("fileName")[0];
            output=output.split(",")[0];
            output=output.split(":")[1];
            System.out.println(output);
            String out[]=output.split(" ");
            for(int i=0;i<out.length;i++) {
                if (out[i].equalsIgnoreCase("media") || out[i].equalsIgnoreCase("matroska"))
                    ft= FileType.MEDIA;
                else if (out[i].equalsIgnoreCase("image"))
                    ft= FileType.IMAGE;
                else if (out[i].equalsIgnoreCase("audio"))
                    ft= FileType.AUDIO;
                else if (out[i].equalsIgnoreCase("text"))
                    ft= FileType.TEXT;
                else if (out[i].equalsIgnoreCase("archive"))
                    ft= FileType.ARCHIVE;
                else if(out[i].equalsIgnoreCase("document"))
                    ft= FileType.DOCUMENT;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            ft= FileType.OTHER;
        }
        return ft;
    }
}