-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 20, 2021 lúc 02:14 PM
-- Phiên bản máy phục vụ: 10.4.19-MariaDB
-- Phiên bản PHP: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `app_pos`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `history`
--

CREATE TABLE `history` (
  `list_name` varchar(255) NOT NULL,
  `code_table` varchar(255) NOT NULL,
  `total` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `history`
--

INSERT INTO `history` (`list_name`, `code_table`, `total`, `date`, `email`) VALUES
('Bánh mì: 3, Bò bít tết: 3, Gà đông tảo: 2', '5', '1440000', '20/12/2021', 'nopecode684@gmail.com'),
('Bánh mì: 2, Bò bít tết: 3, Gà đông tảo: 2', '11', '1410000', '20/12/2021', 'nopecode684@gmail.com'),
('Bánh mì: 1, Bò bít tết: 1, Gà đông tảo: 2', '2', '1080000', '20/12/2021', 'nopecode684@gmail.com'),
('Bánh mì: 3, Bò bít tết: 3', '5', '540000', '20/12/2021', 'haha@gmail.com'),
('Bánh mì: 2', '1', '60000', '20/12/2021', 'nopecode684@gmail.com'),
('Bò bít tết: 2', '2', '300000', '20/12/2021', 'nopecode684@gmail.com'),
('Bò bít tết: 2', '2', '300000', '20/12/2021', 'nopecode684@gmail.com'),
('Bò bít tết: 2', '2', '300000', '20/12/2021', 'nopecode684@gmail.com'),
('Bò bít tết: 2', '3', '300000', '20/12/2021', 'nopecode684@gmail.com'),
('Bánh mì: 2', '2', '60000', '20/12/2021', 'tuan310801anh@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `menu`
--

CREATE TABLE `menu` (
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `thumbnail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `menu`
--

INSERT INTO `menu` (`name`, `price`, `thumbnail`) VALUES
('Bánh mì', 30000, 'http://10.0.2.2/app/thumbnail_food/banh_mi.jpg'),
('Bò bít tết', 150000, 'http://10.0.2.2/app/thumbnail_food/bo_bit_tet.jpg'),
('Gà đông tảo', 450000, 'http://10.0.2.2/app/thumbnail_food/ga_dong_tao.jpg'),
('Humburger', 50000, 'http://10.0.2.2/app/thumbnail_food/humburger.jpg'),
('Phở bò Huế', 40000, 'http://10.0.2.2/app/thumbnail_food/pho.jpg'),
('Tôm nướng', 80000, 'http://10.0.2.2/app/thumbnail_food/tom_nuong.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rating`
--

CREATE TABLE `rating` (
  `Email` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Rate` varchar(255) NOT NULL,
  `Comment` text NOT NULL,
  `Time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `rating`
--

INSERT INTO `rating` (`Email`, `Name`, `Rate`, `Comment`, `Time`) VALUES
('', 'Tuan Anh', 'Rất Tốt', 'tot', '2021-12-20 13:01:04'),
('', 'Tuan Anh', 'Rất Tốt', 'Rat tot', '2021-12-20 13:05:13'),
('nopecode684@gmail.com', 'Tuan Anh', 'Rất Tốt', 'Rat Tot', '2021-12-20 13:06:56'),
('tuan310801anh@gmail.com', 'Tuan Anh', 'Rất Tốt', 'Nha hang qua tuyet voi', '2021-12-20 13:13:55');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `code_otp` varchar(20) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`name`, `email`, `password`, `code_otp`, `status`) VALUES
('Nope ', 'nopecode684@gmail.com', '1234567890', '449280', 1),
('noppee', 'haha@gmail.com', '12345678', '', 1),
('Mlem Mlem', 'tuan310801anh@gmail.com', '1234567890', '', 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
