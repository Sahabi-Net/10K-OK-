-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2026 at 05:38 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_10kok`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id_category` varchar(20) NOT NULL,
  `nama_category` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id_category`, `nama_category`, `created_at`) VALUES
('CAT00001', 'Makanan', '2026-06-27 11:33:11'),
('CAT00002', 'Minuman', '2026-06-27 11:33:11'),
('CAT00003', 'Snack', '2026-06-27 11:33:11');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id_order` varchar(20) NOT NULL,
  `kode_invoice` varchar(20) NOT NULL,
  `id_user` varchar(20) NOT NULL,
  `metode_pengambilan` enum('Delivery','Pickup') NOT NULL,
  `alamat_pengiriman` text DEFAULT NULL,
  `catatan` text DEFAULT NULL,
  `total` int(11) NOT NULL,
  `status` enum('Menunggu','Diproses','Dikirim','Siap Diambil','Selesai','Dibatalkan','Cancel') DEFAULT 'Menunggu',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id_order`, `kode_invoice`, `id_user`, `metode_pengambilan`, `alamat_pengiriman`, `catatan`, `total`, `status`, `created_at`) VALUES
('ORD00001', 'INV-20260628145012', 'USR00001', 'Delivery', 'asdasdasd', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-28 12:50:12'),
('ORD00002', 'INV-20260628152221', 'USR00001', 'Delivery', 'tes', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-28 13:22:21'),
('ORD00003', 'INV-20260629135118', 'USR00001', 'Pickup', 'cimahi', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-29 11:51:18'),
('ORD00004', 'INV-20260629140240', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-29 12:02:40'),
('ORD00005', 'INV-20260629140447', 'USR00001', 'Delivery', 'ssss', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-29 12:04:47'),
('ORD00006', 'INV-20260629140645', 'USR00001', 'Delivery', 'tes', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-29 12:06:45'),
('ORD00007', 'INV-20260629141433', 'USR00001', 'Delivery', 'ss', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-29 12:14:33'),
('ORD00008', 'INV-20260629142404', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-29 12:24:04'),
('ORD00009', 'INV-20260629164217', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-29 14:42:17'),
('ORD00010', 'INV-20260629164720', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-29 14:47:20'),
('ORD00011', 'INV-20260629165017', 'USR00001', 'Delivery', 'JErma', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-06-29 14:50:17'),
('ORD00012', 'INV-20260629181721', 'USR00001', 'Delivery', 'bandung', 'Pesanan dari aplikasi Android', 20000, 'Selesai', '2026-06-29 16:17:21'),
('ORD00013', 'INV-20260701102417', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-01 08:24:17'),
('ORD00014', 'INV-20260701102541', 'USR00001', 'Delivery', 'tes', 'Pesanan dari aplikasi Android', 100000, 'Selesai', '2026-07-01 08:25:41'),
('ORD00015', 'INV-20260701102947', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 100000, 'Selesai', '2026-07-01 08:29:47'),
('ORD00016', 'INV-20260701103321', 'USR00001', 'Delivery', 'tes', 'Pesanan dari aplikasi Android', 70000, 'Selesai', '2026-07-01 08:33:21'),
('ORD00017', 'INV-20260701103607', 'USR00001', 'Delivery', 'sssss', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-01 08:36:07'),
('ORD00018', 'INV-20260701105434', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-01 08:54:34'),
('ORD00019', 'INV-20260701105436', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-01 08:54:36'),
('ORD00020', 'INV-20260701105443', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-01 08:54:43'),
('ORD00021', 'INV-20260701105445', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-01 08:54:45'),
('ORD00022', 'INV-20260701105855', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-01 08:58:55'),
('ORD00023', 'INV-20260702125728', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-02 10:57:28'),
('ORD00024', 'INV-20260702130155', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-02 11:01:55'),
('ORD00025', 'INV-20260703134550', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 11:45:50'),
('ORD00026', 'INV-20260703140528', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:05:28'),
('ORD00027', 'INV-20260703141127', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:11:27'),
('ORD00028', 'INV-20260703141238', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:12:38'),
('ORD00029', 'INV-20260703142015', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:20:15'),
('ORD00030', 'INV-20260703142129', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-03 12:21:29'),
('ORD00031', 'INV-20260703142338', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:23:38'),
('ORD00032', 'INV-20260703142923', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:29:23'),
('ORD00033', 'INV-20260703143022', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:30:22'),
('ORD00034', 'INV-20260703143537', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:35:37'),
('ORD00035', 'INV-20260703143607', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:36:07'),
('ORD00036', 'INV-20260703144259', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 12:42:59'),
('ORD00037', 'INV-20260703151324', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-03 13:13:24'),
('ORD00038', 'INV-20260703153123', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 13:31:23'),
('ORD00039', 'INV-20260703153940', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 13:39:40'),
('ORD00040', 'INV-20260703154351', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, '', '2026-07-03 13:43:51'),
('ORD00041', 'INV-20260703155055', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Cancel', '2026-07-03 13:50:55'),
('ORD00042', 'INV-20260703155805', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Cancel', '2026-07-03 13:58:05'),
('ORD00043', 'INV-20260703160122', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-03 14:01:22'),
('ORD00044', 'INV-20260703160352', 'USR-20260703-B68C', 'Delivery', 'cimahi', 'Pesanan dari aplikasi Android', 20000, 'Selesai', '2026-07-03 14:03:52'),
('ORD00045', 'INV-20260703160841', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Cancel', '2026-07-03 14:08:41'),
('ORD00046', 'INV-20260704043423', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 50000, 'Cancel', '2026-07-04 02:34:23'),
('ORD00047', 'INV-20260704043545', 'USR00001', 'Delivery', 'Cimahi', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-04 02:35:45'),
('ORD00048', 'INV-20260704045652', 'USR00001', 'Pickup', '', 'Pesanan dari aplikasi Android', 10000, 'Selesai', '2026-07-04 02:56:52'),
('ORD00049', 'INV-20260704045848', 'USR00001', 'Delivery', 'cimahi', 'Pesanan dari aplikasi Android', 10000, 'Cancel', '2026-07-04 02:58:48');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id_detail` varchar(20) NOT NULL,
  `id_order` varchar(20) NOT NULL,
  `id_produk` varchar(20) NOT NULL,
  `qty` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `subtotal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id_detail`, `id_order`, `id_produk`, `qty`, `harga`, `subtotal`) VALUES
('DTL00001', 'ORD00001', 'PRD00001', 1, 10000, 10000),
('DTL00002', 'ORD00002', 'PRD00001', 1, 10000, 10000),
('DTL00003', 'ORD00003', 'PRD00002', 1, 10000, 10000),
('DTL00004', 'ORD00004', 'PRD00002', 1, 10000, 10000),
('DTL00005', 'ORD00005', 'PRD00002', 1, 10000, 10000),
('DTL00006', 'ORD00006', 'PRD00001', 1, 10000, 10000),
('DTL00007', 'ORD00007', 'PRD00002', 1, 10000, 10000),
('DTL00008', 'ORD00008', 'PRD00003', 1, 10000, 10000),
('DTL00009', 'ORD00009', 'PRD00001', 1, 10000, 10000),
('DTL00010', 'ORD00010', 'PRD00003', 1, 10000, 10000),
('DTL00011', 'ORD00011', 'PRD00002', 1, 10000, 10000),
('DTL00012', 'ORD00012', 'PRD00001', 2, 10000, 20000),
('DTL00013', 'ORD00013', '1782892770013', 1, 10000, 10000),
('DTL00014', 'ORD00014', '1782892770013', 10, 10000, 100000),
('DTL00015', 'ORD00015', '1782892770013', 10, 10000, 100000),
('DTL00016', 'ORD00016', '1782892770013', 7, 10000, 70000),
('DTL00017', 'ORD00017', '1782892770013', 1, 10000, 10000),
('DTL00018', 'ORD00018', '1782892770013', 1, 10000, 10000),
('DTL00019', 'ORD00019', '1782892770013', 1, 10000, 10000),
('DTL00020', 'ORD00020', '1782892770013', 1, 10000, 10000),
('DTL00021', 'ORD00021', '1782892770013', 1, 10000, 10000),
('DTL00022', 'ORD00022', '1782892770013', 1, 10000, 10000),
('DTL00023', 'ORD00023', 'PRD00003', 1, 10000, 10000),
('DTL00024', 'ORD00024', 'PRD00003', 1, 10000, 10000),
('DTL00025', 'ORD00025', '1782892770013', 1, 10000, 10000),
('DTL00026', 'ORD00026', '1782892770013', 1, 10000, 10000),
('DTL00027', 'ORD00027', 'PRD00001', 1, 10000, 10000),
('DTL00028', 'ORD00028', 'PRD00002', 1, 10000, 10000),
('DTL00029', 'ORD00029', '1782892770013', 1, 10000, 10000),
('DTL00030', 'ORD00030', '1782892770013', 1, 10000, 10000),
('DTL00031', 'ORD00031', '1782892770013', 1, 10000, 10000),
('DTL00032', 'ORD00032', '1782892770013', 1, 10000, 10000),
('DTL00033', 'ORD00033', '1782892770013', 1, 10000, 10000),
('DTL00034', 'ORD00034', '1782892770013', 1, 10000, 10000),
('DTL00035', 'ORD00035', '1782892770013', 1, 10000, 10000),
('DTL00036', 'ORD00036', '1782892770013', 1, 10000, 10000),
('DTL00037', 'ORD00037', '1782892770013', 1, 10000, 10000),
('DTL00038', 'ORD00038', 'PRD00001', 1, 10000, 10000),
('DTL00039', 'ORD00039', '1783085819423', 1, 10000, 10000),
('DTL00040', 'ORD00040', '1783085791260', 1, 10000, 10000),
('DTL00041', 'ORD00041', '1783085819423', 1, 10000, 10000),
('DTL00042', 'ORD00042', '1783085819423', 1, 10000, 10000),
('DTL00043', 'ORD00043', '1783085819423', 1, 10000, 10000),
('DTL00044', 'ORD00044', '1783085791260', 2, 10000, 20000),
('DTL00045', 'ORD00045', '1783085791260', 1, 10000, 10000),
('DTL00046', 'ORD00046', '1783085668513', 5, 10000, 50000),
('DTL00047', 'ORD00047', '1783085791260', 1, 10000, 10000),
('DTL00048', 'ORD00048', 'PRD00003', 1, 10000, 10000),
('DTL00049', 'ORD00049', '1783085819423', 1, 10000, 10000);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id_payment` varchar(20) NOT NULL,
  `id_order` varchar(20) NOT NULL,
  `metode_pembayaran` enum('Cash','QRIS','Transfer Bank','E-Wallet') NOT NULL,
  `status_pembayaran` enum('Belum Bayar','Lunas','Gagal') DEFAULT 'Belum Bayar',
  `tanggal_pembayaran` datetime DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id_payment`, `id_order`, `metode_pembayaran`, `status_pembayaran`, `tanggal_pembayaran`, `created_at`) VALUES
('PAY-20260628145012-0', 'ORD00001', 'Cash', 'Lunas', '2026-06-28 14:50:12', '2026-06-28 12:50:12'),
('PAY-20260628152221-1', 'ORD00002', 'Cash', 'Lunas', '2026-06-28 15:22:21', '2026-06-28 13:22:21'),
('PAY-20260629135119-1', 'ORD00003', 'Cash', 'Lunas', '2026-06-29 13:51:19', '2026-06-29 11:51:19'),
('PAY-20260629140240-5', 'ORD00004', 'Cash', 'Lunas', '2026-06-29 14:02:40', '2026-06-29 12:02:40'),
('PAY-20260629140447-C', 'ORD00005', 'Cash', 'Lunas', '2026-06-29 14:04:47', '2026-06-29 12:04:47'),
('PAY-20260629140645-9', 'ORD00006', 'Cash', 'Lunas', '2026-06-29 14:06:45', '2026-06-29 12:06:45'),
('PAY-20260629141433-3', 'ORD00007', 'Cash', 'Lunas', '2026-06-29 14:14:33', '2026-06-29 12:14:33'),
('PAY-20260629142404-1', 'ORD00008', 'Cash', 'Lunas', '2026-06-29 14:24:04', '2026-06-29 12:24:04'),
('PAY-20260629164217-C', 'ORD00009', 'Cash', 'Lunas', '2026-06-29 16:42:17', '2026-06-29 14:42:17'),
('PAY-20260629164720-9', 'ORD00010', 'Cash', 'Lunas', '2026-06-29 16:47:20', '2026-06-29 14:47:20'),
('PAY-20260629165018-9', 'ORD00011', 'Cash', 'Lunas', '2026-06-29 16:50:18', '2026-06-29 14:50:18'),
('PAY-20260629181721-8', 'ORD00012', 'Cash', 'Lunas', '2026-06-29 18:17:21', '2026-06-29 16:17:21'),
('PAY-20260701102417-7', 'ORD00013', 'Cash', 'Lunas', '2026-07-01 10:24:17', '2026-07-01 08:24:17'),
('PAY-20260701102541-2', 'ORD00014', 'Cash', 'Lunas', '2026-07-01 10:25:41', '2026-07-01 08:25:41'),
('PAY-20260701102947-5', 'ORD00015', 'Cash', 'Lunas', '2026-07-01 10:29:47', '2026-07-01 08:29:47'),
('PAY-20260701103321-0', 'ORD00016', 'Cash', 'Lunas', '2026-07-01 10:33:21', '2026-07-01 08:33:21'),
('PAY-20260701103607-A', 'ORD00017', 'Cash', 'Lunas', '2026-07-01 10:36:07', '2026-07-01 08:36:07'),
('PAY-20260701105855-B', 'ORD00022', 'Cash', 'Lunas', '2026-07-01 10:58:55', '2026-07-01 08:58:55'),
('PAY-20260702125728-7', 'ORD00023', 'QRIS', 'Lunas', '2026-07-02 12:57:28', '2026-07-02 10:57:28'),
('PAY-20260702130155-E', 'ORD00024', 'Cash', 'Lunas', '2026-07-02 13:01:55', '2026-07-02 11:01:55'),
('PAY-20260703134550-9', 'ORD00025', 'Cash', 'Lunas', '2026-07-03 13:45:50', '2026-07-03 11:45:50'),
('PAY-20260703140528-A', 'ORD00026', 'QRIS', 'Lunas', '2026-07-03 14:05:28', '2026-07-03 12:05:28'),
('PAY-20260703141127-A', 'ORD00027', 'Cash', 'Lunas', '2026-07-03 14:11:27', '2026-07-03 12:11:27'),
('PAY-20260703141238-D', 'ORD00028', 'Cash', 'Lunas', '2026-07-03 14:12:38', '2026-07-03 12:12:38'),
('PAY-20260703142015-7', 'ORD00029', 'Cash', 'Lunas', '2026-07-03 14:20:15', '2026-07-03 12:20:15'),
('PAY-20260703142130-A', 'ORD00030', 'Cash', 'Lunas', '2026-07-03 14:21:30', '2026-07-03 12:21:30'),
('PAY-20260703142338-A', 'ORD00031', 'QRIS', 'Lunas', '2026-07-03 14:23:38', '2026-07-03 12:23:38'),
('PAY-20260703142923-5', 'ORD00032', 'Cash', 'Lunas', '2026-07-03 14:29:23', '2026-07-03 12:29:23'),
('PAY-20260703143022-A', 'ORD00033', 'Cash', 'Lunas', '2026-07-03 14:30:22', '2026-07-03 12:30:22'),
('PAY-20260703143537-0', 'ORD00034', 'QRIS', 'Lunas', '2026-07-03 14:35:37', '2026-07-03 12:35:37'),
('PAY-20260703143607-A', 'ORD00035', 'Cash', 'Lunas', '2026-07-03 14:36:07', '2026-07-03 12:36:07'),
('PAY-20260703144259-D', 'ORD00036', 'Cash', 'Lunas', '2026-07-03 14:42:59', '2026-07-03 12:42:59'),
('PAY-20260703151324-0', 'ORD00037', 'QRIS', 'Lunas', '2026-07-03 15:13:24', '2026-07-03 13:13:24'),
('PAY-20260703153123-3', 'ORD00038', 'Transfer Bank', 'Lunas', '2026-07-03 15:31:23', '2026-07-03 13:31:23'),
('PAY-20260703153940-B', 'ORD00039', 'Cash', 'Lunas', '2026-07-03 15:39:40', '2026-07-03 13:39:40'),
('PAY-20260703154351-6', 'ORD00040', 'Cash', 'Lunas', '2026-07-03 15:43:51', '2026-07-03 13:43:51'),
('PAY-20260703155055-6', 'ORD00041', 'Cash', 'Lunas', '2026-07-03 15:50:55', '2026-07-03 13:50:55'),
('PAY-20260703155805-3', 'ORD00042', 'Cash', 'Lunas', '2026-07-03 15:58:05', '2026-07-03 13:58:05'),
('PAY-20260703160122-D', 'ORD00043', 'Transfer Bank', 'Lunas', '2026-07-03 16:01:22', '2026-07-03 14:01:22'),
('PAY-20260703160352-1', 'ORD00044', 'Cash', 'Lunas', '2026-07-03 16:03:52', '2026-07-03 14:03:52'),
('PAY-20260703160841-F', 'ORD00045', 'Cash', 'Lunas', '2026-07-03 16:08:41', '2026-07-03 14:08:41'),
('PAY-20260704043423-D', 'ORD00046', 'Cash', 'Lunas', '2026-07-04 04:34:23', '2026-07-04 02:34:23'),
('PAY-20260704043545-D', 'ORD00047', 'QRIS', 'Lunas', '2026-07-04 04:35:45', '2026-07-04 02:35:45'),
('PAY-20260704045652-4', 'ORD00048', 'Cash', 'Lunas', '2026-07-04 04:56:52', '2026-07-04 02:56:52'),
('PAY-20260704045848-C', 'ORD00049', 'QRIS', 'Lunas', '2026-07-04 04:58:48', '2026-07-04 02:58:48');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id_produk` varchar(20) NOT NULL,
  `id_mitra` varchar(20) NOT NULL,
  `id_category` varchar(20) NOT NULL,
  `nama_produk` varchar(100) NOT NULL,
  `deskripsi` text DEFAULT NULL,
  `harga` int(11) NOT NULL,
  `stok` int(11) DEFAULT 0,
  `gambar` varchar(255) DEFAULT NULL,
  `rating` decimal(2,1) DEFAULT 0.0,
  `jumlah_terjual` int(11) DEFAULT 0,
  `status_produk` enum('Aktif','Nonaktif') DEFAULT 'Aktif',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id_produk`, `id_mitra`, `id_category`, `nama_produk`, `deskripsi`, `harga`, `stok`, `gambar`, `rating`, `jumlah_terjual`, `status_produk`, `created_at`) VALUES
('1782892770013', 'MTR00001', 'CAT00001', 'Chicken Katsu', 'Hidangan ayam fillet yang dibalut tepung roti renyah kemudian digoreng hingga berwarna keemasan. Disajikan bersama nasi putih hangat dan pelengkap, menu ini memiliki tekstur yang krispi di luar namun tetap lembut dan juicy di dalam, sehingga menjadi favorit berbagai kalangan.', 10000, 49, '1783082897_IMG_20260703_194814335.jpg', 4.8, 45, 'Aktif', '2026-07-01 07:59:30'),
('1783085668513', 'MTR00001', 'CAT00002', 'Smoothie Buah', 'Smoothie Buah dibuat dari perpaduan buah naga, stroberi, pisang, dan buah segar pilihan yang dihaluskan hingga menghasilkan tekstur lembut dan creamy. Disajikan dengan topping irisan stroberi, pisang, kelapa parut, dan granola yang renyah, minuman sehat ini kaya akan vitamin, serat, dan antioksidan. Cocok dinikmati sebagai menu sarapan, camilan sehat, maupun pelepas dahaga yang menyegarkan.', 10000, 45, '1783085668_IMG_20260703_203424918.jpg', 5.0, 5, 'Aktif', '2026-07-03 13:34:29'),
('1783085791260', 'MTR00001', 'CAT00002', 'Susu', 'Susu Segar dibuat dari susu berkualitas yang kaya akan protein, kalsium, serta berbagai vitamin dan mineral penting untuk menjaga kesehatan tubuh. Memiliki rasa yang lembut dan menyegarkan, minuman ini cocok dinikmati saat sarapan, sebagai pelengkap hidangan, maupun untuk menemani aktivitas sehari-hari.', 10000, 45, '1783085791_IMG_20260703_203628466.jpg', 5.0, 5, 'Aktif', '2026-07-03 13:36:32'),
('1783085819423', 'MTR00001', 'CAT00002', 'Jus Jeruk', 'Jus Jeruk Segar dibuat dari buah jeruk pilihan yang diperas langsung sehingga menghasilkan cita rasa manis, segar, dan alami. Kaya akan vitamin C serta antioksidan yang baik untuk membantu menjaga daya tahan tubuh, minuman ini cocok dinikmati kapan saja untuk memberikan kesegaran di setiap tegukan.', 10000, 49, '1783085819_IMG_20260703_203656681.jpg', 5.0, 5, 'Aktif', '2026-07-03 13:37:00'),
('PRD00001', 'MTR00001', 'CAT00001', 'Nasi Goreng Spesial', 'Nasi Goreng Spesial dibuat dari nasi yang ditumis dengan bumbu khas pilihan, dipadukan dengan udang, telur, bakso, dan aneka sayuran sehingga menghasilkan cita rasa gurih yang lezat. Disajikan bersama kerupuk dan irisan tomat segar, menu ini cocok dinikmati kapan saja sebagai hidangan yang mengenyangkan dan menggugah selera.', 10000, 49, '1783084252_IMG_20260703_201049099.jpg', 4.8, 164, 'Aktif', '2026-06-27 11:33:49'),
('PRD00002', 'MTR00001', 'CAT00001', 'Salad Sayur', 'Bubur ayam spesial.', 10000, 50, '1783082857_IMG_20260703_194735184.jpg', 4.5, 104, 'Aktif', '2026-06-27 11:33:49'),
('PRD00003', 'MTR00001', 'CAT00001', 'Salad Buah', 'Salad Buah merupakan perpaduan aneka buah segar seperti stroberi, anggur, melon, jeruk, dan buah pilihan lainnya yang dipadukan dengan saus krim manis dan keju parut. Rasanya yang segar, manis, dan sedikit asam menjadikan menu ini cocok sebagai camilan sehat maupun hidangan penutup.', 10000, 49, '1783084308_IMG_20260703_201145322.jpg', 4.8, 206, 'Aktif', '2026-06-27 11:33:49');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id_review` varchar(20) NOT NULL,
  `id_order` varchar(20) DEFAULT NULL,
  `id_produk` varchar(20) DEFAULT NULL,
  `id_user` varchar(20) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `komentar` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`id_review`, `id_order`, `id_produk`, `id_user`, `rating`, `komentar`, `created_at`) VALUES
('RV20260629164729', 'ORD00010', 'PRD00003', 'USR00001', 4, 'tes', '2026-06-29 14:47:29'),
('RV20260629165029', 'ORD00011', 'PRD00002', 'USR00001', 5, 'mantap', '2026-06-29 14:50:29'),
('RV20260629181746', 'ORD00012', 'PRD00001', 'USR00001', 5, 'ddd', '2026-06-29 16:17:46'),
('RV20260701102441', 'ORD00013', '1782892770013', 'USR00001', 5, 'mantap', '2026-07-01 08:24:41'),
('RV20260701102553', 'ORD00014', '1782892770013', 'USR00001', 5, 'mantappppppp', '2026-07-01 08:25:53'),
('RV20260701103329', 'ORD00016', '1782892770013', 'USR00001', 5, 'sssssss', '2026-07-01 08:33:29'),
('RV20260701103617', 'ORD00017', '1782892770013', 'USR00001', 5, '', '2026-07-01 08:36:17'),
('RV20260701105906', 'ORD00022', '1782892770013', 'USR00001', 4, '', '2026-07-01 08:59:06'),
('RV20260702125739', 'ORD00023', 'PRD00003', 'USR00001', 5, 'gokil', '2026-07-02 10:57:39'),
('RV20260702130202', 'ORD00024', 'PRD00003', 'USR00001', 5, 's', '2026-07-02 11:02:02'),
('RV20260703134618', 'ORD00025', '1782892770013', 'USR00001', 4, 'ok', '2026-07-03 11:46:18'),
('RV20260703141335', 'ORD00028', 'PRD00002', 'USR00001', 4, 'ok', '2026-07-03 12:13:35'),
('RV20260703144308', 'ORD00036', '1782892770013', 'USR00001', 5, '', '2026-07-03 12:43:08'),
('RV20260703151344', 'ORD00037', '1782892770013', 'USR00001', 5, 'mantap ', '2026-07-03 13:13:44'),
('RV20260703160131', 'ORD00043', '1783085819423', 'USR00001', 5, 'ok', '2026-07-03 14:01:31'),
('RV20260703160408', 'ORD00044', '1783085791260', 'USR-20260703-B68C', 5, 'ok', '2026-07-03 14:04:08'),
('RV20260704043500', 'ORD00046', '1783085668513', 'USR00001', 5, 'sangat enak', '2026-07-04 02:35:00'),
('RV20260704043554', 'ORD00047', '1783085791260', 'USR00001', 5, 'bagus', '2026-07-04 02:35:54'),
('RV20260704045809', 'ORD00048', 'PRD00003', 'USR00001', 5, 'sangat enak', '2026-07-04 02:58:09');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `foto_profil` varchar(255) DEFAULT 'default.png',
  `role` enum('admin','mitra','user') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` enum('Pending','Aktif','Nonaktif') NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `nama`, `email`, `password`, `no_hp`, `alamat`, `foto_profil`, `role`, `created_at`, `status`) VALUES
('ADM00001', 'Administrator', 'admin@10kok.com', 'admin123', '081111111111', 'Bandung', 'default_admin.png', 'admin', '2026-06-27 11:33:19', 'Aktif'),
('MTR-20260703-5A53', 'tes', 'tes@10kok.com', 'tes123', '0898888', 'fggg', 'default.png', 'mitra', '2026-07-03 11:25:48', 'Nonaktif'),
('MTR-20260704-A5E7', 'KDMP', 'kdmp@10kok.com', '$2y$10$RXZc1Tmd3Mi4KU/iYdrXa.LxA6V5GbOWVyZgpu87WeQfKRWs6q4UO', '0628761648', 'Indonesia', 'default.png', 'mitra', '2026-07-04 02:29:16', 'Nonaktif'),
('MTR-20260704-BF40', 'Cimol AA', 'cimol@10kok.com', '$2y$10$YtPyTqDO504b.SJAdIXwTeKepNfQWTnHEyYWVX.QKN3IMfrnoLI0e', '0849464614846', 'Cimahi', 'default.png', 'mitra', '2026-07-04 02:52:09', 'Aktif'),
('MTR-20260704-EED4', 'Warung Bi Eem', 'bieem@10kok.com', '$2y$10$DMlXYU.tFlL1ZzpcBtIkweohYnWdAS8ni4y4.GLTaH29sTaklIBkW', '123456789078', 'Bandung', 'default.png', 'mitra', '2026-07-04 02:23:55', 'Aktif'),
('MTR00001', 'Warung ChimChim', 'mitra@10kok.com', 'mitra123', '082222222222', 'Cimahi', 'default_mitra.png', 'mitra', '2026-06-27 11:33:36', 'Aktif'),
('USR-20260703-B68C', 'aku', 'aku@10kok.com', '$2y$10$os/NkNo/SMEO3jSLHkZI7eKe4FvBo48rDCxROEOABBS25bO7i2986', NULL, NULL, 'default.png', 'user', '2026-07-03 11:27:43', 'Pending'),
('USR00001', 'Sahabi', 'user@10kok.com', 'user123', '083333333333', 'Bandung', 'default_user.png', 'user', '2026-06-27 11:33:43', 'Aktif');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id_category`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id_order`),
  ADD UNIQUE KEY `kode_invoice` (`kode_invoice`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `id_order` (`id_order`),
  ADD KEY `id_produk` (`id_produk`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id_payment`),
  ADD KEY `id_order` (`id_order`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id_produk`),
  ADD KEY `id_mitra` (`id_mitra`),
  ADD KEY `id_category` (`id_category`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id_review`),
  ADD KEY `id_order` (`id_order`),
  ADD KEY `id_produk` (`id_produk`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id_order`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`id_produk`) REFERENCES `products` (`id_produk`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id_order`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`id_mitra`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `products_ibfk_2` FOREIGN KEY (`id_category`) REFERENCES `categories` (`id_category`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id_order`),
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`id_produk`) REFERENCES `products` (`id_produk`),
  ADD CONSTRAINT `reviews_ibfk_3` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
