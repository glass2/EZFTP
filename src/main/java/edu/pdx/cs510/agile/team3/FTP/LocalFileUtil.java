package edu.pdx.cs510.agile.team3.FTP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushamkumar on 7/21/17.
 */

/*
* Local File class has the method getRootList to retrieve the list of root directories of the local machine.
* getFileListByPath method returns the list of files and directories present under the path specified.
*/
public class LocalFileUtil {

    //returns a list of root directories of the local machine along with its path and whether the file is a directory or not.
    public List<LocalFile> getRootList() throws IOException {
        List<LocalFile> root_directorylist= new ArrayList();
            File[] rootDirectories = File.listRoots(); //listRoots method lists all the root directories of the local machine.
            if (rootDirectories.length > 0) {
                for (File root : rootDirectories) {
                    File[] root_directories = root.listFiles();
                    if (root_directories.length > 0) {
                        for (File root_directory : root_directories) {
                            if(root_directory.isDirectory()) {
                                LocalFile localFile = new LocalFile(root_directory.getName(),
                                        root_directory.getPath(),
                                        root_directory.isDirectory());
                                root_directorylist.add(localFile);
                            }
                        }
                    }
                }
            }

        return root_directorylist;
    }

    //returns the list of file info, which are present under the path provided.
    public List<LocalFile> getFileListByPath(String filePath) throws IOException {
        List<LocalFile> fileInfoList = new ArrayList<>();
        if(!filePath.isEmpty() && filePath != null)
        {
            File userfile = new File(filePath);
            if(userfile.exists()) {
                if (userfile.isDirectory()) //check if the path provided exists and whether it's a directory or not
                {
                    //if the path provided is a directory, then get the list of all files under the directory
                    File[] fileList = userfile.listFiles();
                    if (fileList != null && fileList.length > 0) //check if the directory has any files under it.
                    {
                        for (File file : fileList) {
                            LocalFile localFile = new LocalFile(file.getName(),
                                    file.getPath(),
                                    file.isDirectory());
                            fileInfoList.add(localFile);
                        }
                    }
                }
                else
                {
                    throw new IOException("Path Specified is not a directory");
                }
            }
            else
            {
                throw new IOException("Path Specified is not valid");
            }
        }
        return fileInfoList;
    }


    // This method takes
    public boolean renameFileTo(String sourcefilePath, String oldName, String newName) throws IOException
    {
        boolean isFileRenamed=false;
        try {
            if(sourcefilePath != null && newName !=null && oldName!=null ) {
                String pathWithOldFileName=sourcefilePath+oldName;
                String pathWithNewFileName=sourcefilePath+newName;
                File sourceFile = new File(pathWithOldFileName);
                File renameToFile = new File(pathWithNewFileName);
                if(renameToFile.exists())
                {
                    throw new IOException("A File with new Name already exists");
                }
                else if(sourceFile.exists())
                {
                    isFileRenamed= sourceFile.renameTo(renameToFile);
                }
                else
                {
                    throw new IOException("Renaming file from oldName:"+oldName+" To New Name:"+newName+"Failed!!. Please Try Again");
                }

            }
            else
                throw new IOException("Parameters cannot be null");
        }
        catch (Exception e)
        {
           throw new IOException("Path is not valid");

        }
        return isFileRenamed;
    }

    //method searches for a file under a directory. if the fileName parameter is "", then returns all the results under the directory
    public List<LocalFile> SearchFileAtPath(String searchPath, String fileName) throws IOException {

        List<LocalFile> directoryFileList = new ArrayList();
        boolean retrieveAllFiles = false;
        try {
            if (fileName.equals("")) {
                retrieveAllFiles = true;
            }

            if (searchPath != null && fileName != null) {
                File directory = new File(searchPath);
                if (directory.exists()) {
                    File[] directoryFiles = directory.listFiles();
                    if (!retrieveAllFiles) {
                        for (File file : directoryFiles) {
                            if (file.getName().equals(fileName)) {
                                directoryFileList.add(new LocalFile(file.getName(), file.getPath(), false));

                            }
                        }
                    } else {
                        for (File file : directoryFiles) {
                            directoryFileList.add(new LocalFile(file.getName(), file.getPath(), file.isDirectory()));
                        }

                    }
                } else {

                    throw new IOException("Directory does not exist");

                }
            } else {
                throw new IOException("Parameters cannot be null");
            }


        } catch (Exception e) {

            throw new IOException("Search Failed!!");
        }


        return directoryFileList;
    }

}
