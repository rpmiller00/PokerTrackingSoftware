CREATE VIEW account_username
AS
SELECT u.username, u.user_id, a.account_id
FROM
tenmo_user u
INNER JOIN 
	account a
	ON u.user_id = a.user_id



