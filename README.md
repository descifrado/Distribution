# Distribution
A Distributed System for Sharing files similar to Bittorent with some added functionalities.
## Basic Features:
1. A user account to which each user can log into and create his profile and can share content with others.
2. A dashboard for the user showing the files he has downloaded or shared, the number of people who have downloaded the files he/she has shared.
3. While sharing a file the user should be able to define what kind of fileit is. 
4. A file shared by any user should be stored on multiple nodes either as a complete copy or in the form partial chunks. (i.e. in a Distributed Manner)
5. The chunks of any single file must be stored in at least 3 different nodes.
6. A user should be able to download a file using a unique identifier (i.e. a file (or any such entity or object) which exclusively defines that particular file which the user wants to download).
7. If identical files are shared by different users, they should have the same identifier.
8. Create an interface where the user can search for different kinds of files 
9. Create a user interface where users can search a file using the file title/name.

## Advanced Features:
1. File shared by the user can have multiple tags like a movie shared by a user can have tags like action, fantasy etc., a book shared by a user can have tags like ML, coursebook etc.
2. Create a search utility where the user can view files and search files using various other parameters like file type Document, Video, Song,Movie etc.), tags etc.
3. For a file (or a part of a file) which is stored on multiple nodes, downloads for that file should occur parallelly from all those nodes which are online and which have a part of that file.
4. In case of multimedia files like videos or movies, play the video/movie without downloading the whole file. 
5. Recommend multimedia files for users based on their download/view history. 
6. If for a search query there are multiple results, rank the results based on the availability of that file on different nodes.
7. A user who has still not downloaded the whole file (but has some part of it ) can have other users downloading that part of the file from his node.



