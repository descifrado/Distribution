-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 08, 2019 at 11:11 AM
-- Server version: 5.7.27-0ubuntu0.18.04.1
-- PHP Version: 7.2.19-0ubuntu0.18.04.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Distribution`
--

-- --------------------------------------------------------

--
-- Table structure for table `File`
--

CREATE TABLE `File` (
  `fileUID` varchar(256) NOT NULL,
  `fileName` varchar(256) NOT NULL,
  `location` varchar(256) NOT NULL,
  `type` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `File`
--

INSERT INTO `File` (`fileUID`, `fileName`, `location`, `type`) VALUES
('0a6ee498-a450-3b6d-92e5-6d7d5042797d', 'memory_lane.jpg', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'IMAGE'),
('0f972b6f-0911-3f75-a278-96915be2dd8e', 'Screenshot from 2018-12-13 17-52-46.png', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'OTHER'),
('1058aaad-b5c7-35d4-b84d-ed21f6fe7292', '20174005.sql', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'TEXT'),
('1e5c5408-65c8-3063-8c85-4e572de5dbd2', 'index.mp4', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'MEDIA'),
('250797fd-fc70-376b-820a-cef8db0f4844', 'Screenshot from 2019-01-26 12-42-39.png', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'OTHER'),
('261eba44-1cae-3997-8e5d-36a54ce192cc', 'Test.java', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'OTHER'),
('3a0443a8-8e7a-34b7-a49e-db939aeb872c', 'avengers-endgame-space.png', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'IMAGE'),
('5ac8f782-dc80-3ab5-94c6-ba51ac19387b', 'Screenshot from 2019-01-27 07-48-15.png', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'OTHER'),
('678a3285-a6e0-3bfb-afa7-2f153fc85ab9', 'try.mp4', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'MEDIA'),
('7b9f1b8a-2623-3aca-b239-9009660173ab', 'jquery-min.js', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'TEXT'),
('81af7c58-bf67-3c77-9736-dd9b63969162', 'Screenshot from 2018-12-26 18-41-03.png', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'OTHER'),
('94ebe92a-929a-3101-b79b-50e3f53f2c9d', 'project2frg.pptx', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'ARCHIVE'),
('aadcc50b-5a23-3974-bb78-0d4325a14374', 'Arc-Reactor-Iron-Man-Desktop-Wallpaper.jpg', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'IMAGE'),
('aed0d698-23fb-3824-922c-2dae50c2ce61', 'pp.jpg', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'IMAGE'),
('d81333c7-2cf4-309c-9dfc-63d209e92ce8', 'Screenshot from 2019-08-21 13-05-50.png', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'OTHER'),
('dc1b927a-f6e2-30da-97ac-637a10db65f8', '20174047_Assignment#2.pdf', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'DOCUMENT'),
('eb8c0e12-0753-38aa-9bbc-0af66c74bda4', 'hackpro_ctf_2.png', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'IMAGE'),
('ebc0f32e-54f7-37eb-8468-4f21d56b1ab1', 'Screenshot from 2018-09-29 16-13-21.png', '/home/moonknight/Avishkar/This3Bhushan/DistributionServer/jsonFiles', 'OTHER');

-- --------------------------------------------------------

--
-- Table structure for table `FileTags`
--

CREATE TABLE `FileTags` (
  `fileUID` varchar(256) NOT NULL,
  `tags` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `FileTags`
--

INSERT INTO `FileTags` (`fileUID`, `tags`) VALUES
('1058aaad-b5c7-35d4-b84d-ed21f6fe7292', 'database'),
('3a0443a8-8e7a-34b7-a49e-db939aeb872c', 'avengers'),
('eb8c0e12-0753-38aa-9bbc-0af66c74bda4', 'ctf'),
('eb8c0e12-0753-38aa-9bbc-0af66c74bda4', 'revengg'),
('0a6ee498-a450-3b6d-92e5-6d7d5042797d', 'ctf'),
('0a6ee498-a450-3b6d-92e5-6d7d5042797d', 'memory'),
('aadcc50b-5a23-3974-bb78-0d4325a14374', 'avengers'),
('aadcc50b-5a23-3974-bb78-0d4325a14374', 'arc reactor'),
('94ebe92a-929a-3101-b79b-50e3f53f2c9d', 'ppt'),
('94ebe92a-929a-3101-b79b-50e3f53f2c9d', 'iitb'),
('81af7c58-bf67-3c77-9736-dd9b63969162', 'picture'),
('81af7c58-bf67-3c77-9736-dd9b63969162', 'screenshot'),
('ebc0f32e-54f7-37eb-8468-4f21d56b1ab1', 'sacred'),
('0f972b6f-0911-3f75-a278-96915be2dd8e', 'ss'),
('5ac8f782-dc80-3ab5-94c6-ba51ac19387b', 'hack'),
('aed0d698-23fb-3824-922c-2dae50c2ce61', 'Photo'),
('aed0d698-23fb-3824-922c-2dae50c2ce61', 'Profile'),
('d81333c7-2cf4-309c-9dfc-63d209e92ce8', 'ss'),
('7b9f1b8a-2623-3aca-b239-9009660173ab', 'js'),
('dc1b927a-f6e2-30da-97ac-637a10db65f8', 'pdf'),
('1e5c5408-65c8-3063-8c85-4e572de5dbd2', 'index');

-- --------------------------------------------------------

--
-- Table structure for table `Password`
--

CREATE TABLE `Password` (
  `userUID` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Password`
--

INSERT INTO `Password` (`userUID`, `password`) VALUES
('123', 'suraj'),
('8d0b63e8-5b25-3b59-b471-3cdb9b2fd0c4', '131132133'),
('6f403a84-c578-32c4-9b66-04c4382fe289', '171177165172174179'),
('3ba3018b-4d6a-3d50-a60d-c0116b938e2b', '3da541559918a808c2402bba5012f6c60b27661c'),
('c39fca14-1fc9-3150-bf7e-4c832e12b9ab', '8cb2237d0679ca88db6464eac60da96345513964'),
('439133d3-beaf-3fe8-b5b7-f4b6320d64bd', 'ba710cfe4d49197860b4c3a526eaab22d8463251'),
('ec50c884-23c0-3417-a169-83e9e089b1b8', '0abd0df55eb02d47bf2d3e301d3310c4d084e10b'),
('3c6f2e70-11c5-3603-aa9a-bff258040d10', 'e9d71f5ee7c92d6dc9e92ffdad17b8bd49418f98'),
('3030130b-59da-34f4-8dd4-0ef213ac605f', '0abd0df55eb02d47bf2d3e301d3310c4d084e10b');

-- --------------------------------------------------------

--
-- Table structure for table `Peers`
--

CREATE TABLE `Peers` (
  `fileUID` varchar(256) NOT NULL,
  `userUID` varchar(256) NOT NULL,
  `IP` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Peers`
--

INSERT INTO `Peers` (`fileUID`, `userUID`, `IP`) VALUES
('asdas', 'asdsdggadfg', 'dfsgdfgd'),
('asdas', 'asdsdggasdfddfg', 'dfssdfgdfgd'),
('asdas', 'afddfg', 'dsdfgdfgd'),
('dsfgsd', 'afddfg', 'dsdfgdfgd'),
('dsfgsd', 'dvz', 'zd'),
('dsfgsd', 'ddfvz', 'zdfzd'),
('dsfgsd', 'ddfvcxvz', 'zdfxcvzd'),
('sdfsdfsdgdsfd', 'sdfgd', 'zxvcxc'),
('sdfsdfsdgdsfd', 'sdfgdasdf', 'zxdfvcxc'),
('678a3285-a6e0-3bfb-afa7-2f153fc85ab9', '439133d3-beaf-3fe8-b5b7-f4b6320d64bd', '127.0.1.1'),
('3a0443a8-8e7a-34b7-a49e-db939aeb872c', '439133d3-beaf-3fe8-b5b7-f4b6320d64bd', '192.168.0.106'),
('eb8c0e12-0753-38aa-9bbc-0af66c74bda4', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.106'),
('0a6ee498-a450-3b6d-92e5-6d7d5042797d', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.106'),
('aadcc50b-5a23-3974-bb78-0d4325a14374', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.106'),
('94ebe92a-929a-3101-b79b-50e3f53f2c9d', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.106'),
('81af7c58-bf67-3c77-9736-dd9b63969162', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.106'),
('ebc0f32e-54f7-37eb-8468-4f21d56b1ab1', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.106'),
('0f972b6f-0911-3f75-a278-96915be2dd8e', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.106'),
('250797fd-fc70-376b-820a-cef8db0f4844', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.106'),
('5ac8f782-dc80-3ab5-94c6-ba51ac19387b', '3030130b-59da-34f4-8dd4-0ef213ac605f', '192.168.0.106'),
('aed0d698-23fb-3824-922c-2dae50c2ce61', '439133d3-beaf-3fe8-b5b7-f4b6320d64bd', '192.168.0.103'),
('aed0d698-23fb-3824-922c-2dae50c2ce61', '439133d3-beaf-3fe8-b5b7-f4b6320d64bd', '192.168.0.103'),
('aed0d698-23fb-3824-922c-2dae50c2ce61', '439133d3-beaf-3fe8-b5b7-f4b6320d64bd', '192.168.0.103'),
('d81333c7-2cf4-309c-9dfc-63d209e92ce8', '439133d3-beaf-3fe8-b5b7-f4b6320d64bd', '192.168.0.103'),
('7b9f1b8a-2623-3aca-b239-9009660173ab', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.103'),
('dc1b927a-f6e2-30da-97ac-637a10db65f8', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.103'),
('1e5c5408-65c8-3063-8c85-4e572de5dbd2', '3c6f2e70-11c5-3603-aa9a-bff258040d10', '192.168.0.103');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `userUID` varchar(256) NOT NULL,
  `firstName` varchar(256) NOT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `emailID` varchar(256) NOT NULL,
  `contact` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`userUID`, `firstName`, `lastName`, `emailID`, `contact`) VALUES
('0b392605-08fe-3aa4-9136-4ccd24358503', 'suraj', 'goel', 'suraj@gmail.com', '8901435825'),
('123', 'suraj', 'goel', 'surajgoel.1225@gmail.com', '8901435825'),
('3030130b-59da-34f4-8dd4-0ef213ac605f', 'suraj', 'goel', 'surajgoel@mnnit.ac.in', '8901435285'),
('3ba3018b-4d6a-3d50-a60d-c0116b938e2b', 'Shrey', 'T', 'shrey@gmail.com', '6549873210'),
('3c6f2e70-11c5-3603-aa9a-bff258040d10', 'a', 'b', 'a', '1'),
('439133d3-beaf-3fe8-b5b7-f4b6320d64bd', 'Aman', 'Sharma', 'amansharma@gmail.com', '9876543210'),
('6f403a84-c578-32c4-9b66-04c4382fe289', 'XYZ', 'ABC', 'xyz@gmail.com', '7894561230'),
('8d0b63e8-5b25-3b59-b471-3cdb9b2fd0c4', 'abc', 'xyz', 'a@b.com', '123'),
('c39fca14-1fc9-3150-bf7e-4c832e12b9ab', 'FN', 'LN', 'fnln@gmail.com', '187176837'),
('ec50c884-23c0-3417-a169-83e9e089b1b8', 'nobody', 'does', 'nbid@gmail.com', '8901435825');

-- --------------------------------------------------------

--
-- Table structure for table `UserHistory`
--

CREATE TABLE `UserHistory` (
  `userUID` varchar(256) NOT NULL,
  `fileUID` varchar(256) NOT NULL,
  `downloaded` int(1) NOT NULL,
  `shared` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `File`
--
ALTER TABLE `File`
  ADD PRIMARY KEY (`fileUID`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`userUID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
