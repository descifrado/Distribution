package tools;

import constants.FileType;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetFileType
{
    private FileType ft;
    public FileType getFileType(String path)
    {
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
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
            output=output.split(",")[0];
            output=output.split(":")[1];
            String out[]=output.split(" ");
            for(int i=0;i<out.length;i++) {
                if (out[i].equalsIgnoreCase("media"))
                    return FileType.MEDIA;
                else if (out[i].equalsIgnoreCase("image"))
                    return FileType.IMAGE;
                else if (out[i].equalsIgnoreCase("audio"))
                    return FileType.AUDIO;
                else if (out[i].equalsIgnoreCase("text"))
                    return FileType.TEXT;
                else if (out[i].equalsIgnoreCase("archive"))
                    return FileType.ARCHIVE;
                else
                    return FileType.OTHER;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return FileType.UNCATEGORIZED;
        }
        System.out.println(output);
        return FileType.UNCATEGORIZED;
    }
}