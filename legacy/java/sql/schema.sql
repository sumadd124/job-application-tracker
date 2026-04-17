CREATE DATABASE IF NOT EXISTS job_tracker;
USE job_tracker;

CREATE TABLE IF NOT EXISTS applications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    date_applied DATE,
    status ENUM('Applied', 'Screening', 'Interview', 'Offer', 'Rejected') DEFAULT 'Applied',
    deadline DATE,
    notes TEXT
);
