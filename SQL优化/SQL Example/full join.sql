SELECT
	*
FROM
	case_info c
LEFT JOIN case_info_copy cc ON c.ID = cc.ID
UNION
	SELECT
		*
	FROM
		case_info c
	RIGHT JOIN case_info_copy cc ON c.ID = cc.ID;

SELECT
	COUNT(ID)
FROM
	case_info;