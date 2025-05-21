-- Customer Table
CREATE TABLE Customer(
    CustomerID INT PRIMARY KEY,
    FirstName NVARCHAR(50),
    LastName NVARCHAR(50),
    Address NVARCHAR(100),
    PostalCode NVARCHAR(10)
);
-- Pizza Table
CREATE TABLE Pizza(
    PizzaCode NVARCHAR(5) PRIMARY KEY,
    PizzaName NVARCHAR(50),
    Price DECIMAL(5,2)
);
-- Orders Table
CREATE TABLE Orders(
    OrderNumber INT PRIMARY KEY,
    OrderDate DATE,
    CustomerID INT,
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
);
-- Orders-Pizza 
CREATE TABLE OrdersPizza(
    OrderNumber INT,
    PizzaCode NVARCHAR(5),
    Quantity INT,
    PRIMARY KEY (OrderNumber, PizzaCode),
    FOREIGN KEY (OrderNumber) REFERENCES Orders(OrderNumber),
    FOREIGN KEY (PizzaCode) REFERENCES Pizza(PizzaCode)
);


ALTER TABLE Orders
DROP COLUMN TotalPrice;