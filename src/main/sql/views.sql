-- View
CREATE VIEW OrdersWithTotal AS
SELECT 
    o.OrderNumber,
    o.OrderDate,
    o.CustomerID,
    SUM(p.Price * op.Quantity) AS TotalPrice
FROM Orders o
JOIN OrdersPizza op ON o.OrderNumber = op.OrderNumber
JOIN Pizza p ON op.PizzaCode = p.PizzaCode
GROUP BY o.OrderNumber, o.OrderDate, o.CustomerID;
