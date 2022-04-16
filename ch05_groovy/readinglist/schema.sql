create TABLE `Book` (
    `id` IDENTITY,
    `reader` VARCHAR(20) NOT NULL,
    `isbn` VARCHAR(10) NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `author` VARCHAR(50) NOT NULL,
    `description` VARCHAR(2000) NOT NULL
);