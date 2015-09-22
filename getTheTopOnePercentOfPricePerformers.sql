SELECT ticker_symbol FROM prospect WHERE perf5 > (
SELECT AVG(t1.perf5) AS median_val FROM (
SELECT @rownum:=@rownum+1 AS `row_number`, d.perf5
  FROM prospect d,  (SELECT @rownum:=0) r
  WHERE 1
  -- put some where clause here
  ORDER BY d.perf5
) AS t1, 
(
  SELECT COUNT(*) AS total_rows
  FROM prospect d
  WHERE 1
  -- put same where clause here
) AS t2
WHERE 1 AND t1.row_number IN ( FLOOR(total_rows-((total_rows+1)/100)), FLOOR(total_rows-(total_rows+2)/100) )
);